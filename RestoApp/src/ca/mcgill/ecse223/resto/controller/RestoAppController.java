package ca.mcgill.ecse223.resto.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.model.Bill;
import ca.mcgill.ecse223.resto.model.Menu;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.PricedMenuItem;
import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Table.Status;

public class RestoAppController {

	public RestoAppController() {
	}
	
	public static HashMap<MenuItem, Integer> getTopSellingItems(Date startDate, Time startTime, Date endDate,
			Time endTime) throws InvalidInputException {

		if (!((startDate == null && startTime == null && endDate == null && endTime == null)
				|| (startDate != null && startTime != null && endDate != null && endTime != null))) {
			throw new InvalidInputException("Invalid Dates");
		}

		HashMap<MenuItem, Integer> popularity = new HashMap<>();
		RestoApp r = RestoAppApplication.getr();
		List<Order> orders = r.getOrders();
		Date currentDate;
		Time currentTime;
		List<OrderItem> orderItems;
		PricedMenuItem pricedMenuItem;
		MenuItem menuItem;
		Integer value;
		for (Order order : orders) {
			currentDate = order.getDate();
			currentTime = order.getTime();
			if (isInTime(startDate, startTime, endDate, endTime, currentDate, currentTime)) {
				orderItems = order.getOrderItems();
				for (OrderItem orderItem : orderItems) {
					pricedMenuItem = orderItem.getPricedMenuItem();
					menuItem = pricedMenuItem.getMenuItem();
					if (popularity.containsKey(menuItem)) {
						value = popularity.get(menuItem);
						value++;
						popularity.put(menuItem, value);
					} else {
						popularity.put(menuItem, 0);
					}
				}
			}
		}
		return popularity;
	}

	private static boolean isInTime(Date startDate, Time startTime, Date endDate, Time endTime, Date currentDate,
			Time currentTime) {
		if (startDate == null && startTime == null && endDate == null && endTime == null) {
			return true;
		}

		if (startDate != null && startTime != null && endDate != null && endTime != null) {
			return endDate.getTime() - currentDate.getTime() >= startDate.getTime()
					&& endTime.getTime() - currentTime.getTime() >= startTime.getTime();
		}

		return false;
	}

	public static List<ItemCategory> getItemCategories() {
		List <ItemCategory> itemCategories = new ArrayList<ItemCategory>();
		for (ItemCategory itemCategory : MenuItem.ItemCategory.values()) {
			itemCategories.add(itemCategory);
		}
		return itemCategories;
	}

	public static void createTable(int number, int x, int y, int width, int length, int numberOfSeats)
			throws InvalidInputException {
		String error = "";
		if (number <= 0) {
			error = "The number of the table must be greater than zero. ";
		}
		if (x < 0) {
			error = error + "The x coordinate of the table must be greater than zero. ";
		}
		if (y < 0) {
			error = error + "The y coordinate of the table must be greater than zero. ";
		}
		if (width <= 0) {
			error = error + "The width of the table must be greater than zero. ";
		}
		if (length <= 0) {
			error = error + "The length of the table must be greater than zero. ";
		}
		if (numberOfSeats <= 0) {
			error = error + "The numberOfSeats of the table must be greater than zero. ";
		}
		// System.out.prinln(error);
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}

		RestoApp r = RestoAppApplication.getr();

		// RestoApp currentTable = r.getCurrentTables();

		for (Table currentTable : r.getCurrentTables()) {
			if (currentTable.doesOverlap(x, y,width, length)) {

				throw new InvalidInputException("A table already exists at this location");
			}

		}
		try {
			Table table = new Table(number, x, y, width, length, r);
			r.addCurrentTable(table);
			
			for (int i = 1; i <= numberOfSeats; i++) {

				Seat seat = table.addSeat();
				table.addCurrentSeat(seat);
				// RestoAppApplication.save();

			}

		} catch (RuntimeException e) {
			error = e.getMessage();
			if (error.equals("Cannot create due to duplicate number")) {
				error = "A table with this number already exists. Please use a different number.";
			}
			throw new InvalidInputException(error);
			
		}
		RestoAppApplication.save();
		
	}
	
	public static void reserveTable(Date date, Time time, int numberIntParty, String contactName, String contactEmailAddress, String contactPhoneNumber, int[] listTables) throws
	InvalidInputException{
		RestoApp r = RestoAppApplication.getr();
		ArrayList<Table> tables = new ArrayList<Table>();
		
		for(int i = 0; i < listTables.length; i++) {
			tables.add(Table.getWithNumber(listTables[i]));
		}
		
		if(date == null || time == null || contactName == null || contactEmailAddress == null || contactPhoneNumber == null) {
			throw new InvalidInputException("All fields must be specified to add a reservation. ");
		}
		if( numberIntParty < 0 ) {
			throw new InvalidInputException("The number in party must be bigger than 0. ");
		}
		
		if ( contactName ==""  || contactEmailAddress == "" || contactPhoneNumber == "") {
			throw new InvalidInputException ("The contact name, email address and phone number should not be empty. ");
		}
		
		java.util.Date today = RestoAppApplication.getr().getCurrentDate();
		
		if(date.before(today)) {
			throw new InvalidInputException("Date has to be in the future");
		}	
		
		List<Table> currentTables = r.getCurrentTables();
		boolean current = true; 
		int seatCapacity = 0;
		for(Table table: tables) {
			current = currentTables.contains(table);
			if(current == false) {
				throw new InvalidInputException("one of the tables doesn't exist");
			}
			
			seatCapacity += table.numberOfCurrentSeats();
			
			List<Reservation> reservations = table.getReservations();
			for(Reservation res: reservations) {
				if(res.doesOverlap(date, time)) {
					throw new InvalidInputException("Reservation time is not available");
				}
			}
			
		}
		
		if( seatCapacity < numberIntParty ) {
			throw new InvalidInputException("Not enough seats");
		}
		
		Table[] resTables = new Table[tables.size()];
		for(int i = 0; i<resTables.length; i++) {
			resTables[i] = tables.get(i);
		}
			
		Reservation reservation = new Reservation(date, time, numberIntParty, contactName, contactEmailAddress, contactPhoneNumber, r, resTables);
		
		for(Table t: tables) {
			t.addReservation(reservation);
		}
		
		RestoAppApplication.save();
		
	}

	// added this coz of java swing.
	public static void changeTableNumtoTable(int number) throws InvalidInputException {
		RestoApp r = RestoAppApplication.getr();
		String error = "";
		if(number < 1) {
			throw new InvalidInputException("Table ID must be greater than 0");
		}
		
		Table t = Table.getWithNumber(number);
		if(t==null) {
			throw new InvalidInputException("Table not found");
		}
		
		if(t.hasReservations()) {
			throw new InvalidInputException("Table has reservations");
		}
		
		if(t.getStatus() != Status.Available) {
			throw new InvalidInputException("Table is in-use");
		}
		
		
		try {
			r.removeCurrentTable(t);
			RestoAppApplication.save();
		}
		catch(RuntimeException e){
			error = e.getMessage();
			throw new InvalidInputException(e.getMessage());
		}
	}

	// method to update table, has various checks to insure everything runs smoothly
	//Fixed using feedback from iteration 3
	//Update Table fixed 
	public static void updateTable(int number, int newNumber, int numberOfSeats) throws InvalidInputException {
		String error = "";
		if (number <= 0) {
			error = "The number of the table must be greater than zero. ";
		}
		if (newNumber <= 0) {
			error = "The new number of the table must be greater than zero. ";
		}
		
		if (newNumber == number) {
			error = "New table number entered is the same as the current one, please choose another one. ";
		}
		
		if (numberOfSeats <= 0) {
			error = error + "The numberOfSeats of the table must be greater than zero. ";
		}
		
		// System.out.prinln(error);
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}

		RestoApp r = RestoAppApplication.getr();

		try {
			Table table = Table.getWithNumber(number);
		} catch (RuntimeException e) {
			error = e.getMessage();

		}
		
		Table table = Table.getWithNumber(number);
		if(table==null) {
			throw new InvalidInputException("Table not found");
		}
		if (table.getStatus() != Status.Available) {
			throw new InvalidInputException("The table desired is already occupied, please choose another one.");
			
		}
		// added to verify that is not reserved already
		if ((table.hasReservations())) {
			throw new InvalidInputException("This table is already reserved, please choose another table");
		}
		
	/*	for ( Order order : r.getCurrentOrders()) {			//might have to be removed
			List<Table> numOfTables = order.getTables();	//since condition to check if table is used verified earlier
															//line 163
			if(numOfTables.contains(numOfTables)) {
				
				throw new InvalidInputException("Table is in use");
			}

		}  */

		try {
			table.setNumber(newNumber);
			int n = table.numberOfCurrentSeats();

			r.addCurrentTable(table);
			if ((numberOfSeats-n) > 0) {
				for (int i = 1; i <= numberOfSeats-n; i++) {

					Seat seat = table.addSeat();
					table.addCurrentSeat(seat);
					// RestoAppApplication.save();
				}
			} else {

				for (int i = 1; i <= n-numberOfSeats; i++) {

					Seat seat = table.getCurrentSeat(i);
					table.removeCurrentSeat(seat);
					// RestoAppApplication.save();
				}

			}

		} catch (RuntimeException e) {
			error = e.getMessage();
			if (error.equals("Cannot create due to duplicate number")) {
				error = "A table with this number already exists. Please use a different number.";
			}
			throw new InvalidInputException(error);
		}

		RestoAppApplication.save();

	}



	public static void changeTableLocation(int tableNum, int x, int y) throws InvalidInputException {
		String error = "";
		// the RestoAppApplication should return a restoApp object.
		if (tableNum < 1)  {
			error = "Table Number less than zero. Enter a valid table number";			
		}
		if (x < 0) {
			error = error + "The x coordinate of the table must be greater than zero. ";
		}
		if (y < 0) {
			error = error + "The y coordinate of the table must be greater than zero. ";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}

		RestoApp r = RestoAppApplication.getr();		
		List<Table> currentTables = r.getCurrentTables();

		try {
			Table table = Table.getWithNumber(tableNum);
		} catch (RuntimeException e) {
			error = e.getMessage();		

		}
		
		Table table = Table.getWithNumber(tableNum);
		if(table==null) {
			throw new InvalidInputException("Table not found");
		}
		
		int corX = table.getX();
		int corY = table.getY();
		int width = table.getWidth();
		int length = table.getLength();

		for (Table currentTable : currentTables) {
			if (currentTable.doesOverlap(x, y,width, length) && currentTable.getX() != corX && currentTable.getY() != corY ) {
				throw new InvalidInputException("A table already exists at this location ashish");
			}
		
		}
			
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		try {
			table.setX(x);
			table.setY(y);
			RestoAppApplication.save();
		}

		catch (RuntimeException e) {
			error = e.getMessage();
			throw new InvalidInputException("Could not set or save the table location...please call 1800-GET-HELP");
		}
		


	}

	public static List<MenuItem> getMenuItems(ItemCategory itemCategory) throws InvalidInputException {
		if (itemCategory == null) {
			throw new InvalidInputException("ItemCategory can't be null");
		}

		List<MenuItem> l = new ArrayList<MenuItem>();
		RestoApp restoApp = RestoAppApplication.getr();
		Menu menu = restoApp.getMenu();
		List<MenuItem> menuItems = menu.getMenuItems();
		ItemCategory currentMenuItemItemCategory = null;
		Boolean hasPricedMenuItems = false;
		for (MenuItem menuItem : menuItems) {
			hasPricedMenuItems = menuItem.hasCurrentPricedMenuItem();
			currentMenuItemItemCategory = menuItem.getItemCategory();
//			System.out.println(menuItem.getName());
//			System.out.println(menuItem.hasCurrentPricedMenuItem());
//			System.out.println(menuItem.getItemCategory().toString());
//			System.out.println(itemCategory.toString());
			if (hasPricedMenuItems && currentMenuItemItemCategory.equals(itemCategory)) {
				l.add(menuItem);
//				System.out.println("*******");
			}
		}

		return l;
	}
	//method's error fixed, orderOne changed to o in line 402
	
	public static void startOrder(List<Integer> tableNums) throws InvalidInputException{
		RestoApp r = RestoAppApplication.getr();
		
		ArrayList<Table> boustan = new ArrayList<Table>();
		
		for(Integer i : tableNums) {
			Table t = Table.getWithNumber(i);
			boustan.add(t);
		}
		
		if (tableNums.size() == 0) {
			throw new InvalidInputException("tables is null");
		}
		
		List<Table> currentTables = r.getCurrentTables();
		for (Table b : boustan) {
			if(!currentTables.contains(b)) {
				throw new InvalidInputException("table not in currentTables");
			}
		}
		boolean orderCreated = false;
		Order lastOrder = null;
		Order newOrder = null;
		for (Table c : boustan) {
			if (c.hasOrders()) {
				List<Order> cOrders = c.getOrders();
				for (Order o : cOrders) {
					c.addToOrder(newOrder);
					orderCreated = true;
					newOrder = o;
				}			
			}else {
				c.startOrder();
			}
			if(c.numberOfOrders() > 0) {
				lastOrder = c.getOrder(c.numberOfOrders() -1);
			}
			if(c.numberOfOrders() > 0 && !c.getOrder(c.numberOfOrders() - 1).equals(lastOrder)) {
				orderCreated = true;
				newOrder = c.getOrder(c.numberOfOrders() -1);
			}
					
		}
		
		r.addCurrentOrder(newOrder);
		RestoAppApplication.save();
		
	}
	
	
	
	public static void endOrder(int orderNum) throws
	InvalidInputException{
		
		if(orderNum < 1) {
			throw new InvalidInputException("order number cannot be < 1");
		}
		
		RestoApp r = RestoAppApplication.getr();
		Order order = null;
		for(Order ord: r.getCurrentOrders()) {
			
			if(ord != null && ord.getNumber() == orderNum) {
				order = ord; 
			}
		}
		if(order == null) {
			throw new InvalidInputException("We couldn't find your order");
		}
		
		
	
		List<Order> currentOrders = r.getCurrentOrders();
		boolean current = true; 
		
		current = currentOrders.contains(order);
		if(current == false) {
			throw new InvalidInputException(" Current order is false");
		}
		
		//Therefore, a new list of tables needs to be created before the loop, 
		//each table needs to be added one by one to that new list, 
		// and the new list needs to be used in the loop instead of tables.
		
		
		List<Table> tables = r.getCurrentTables();
		for(Table table : tables) {
			
			if(table.numberOfOrders()>0 && table.getOrder(table.numberOfOrders()-1).equals(order)) {
				table.endOrder(order);
			}
		}
		// if method returns true, then the order will be removed	
		
		if(allTablesAvailableOrDifferentCurrentOrder(tables, order)) {
			
				r.removeCurrentOrder(order);
			}
			
		
		// this line uncommented
		RestoAppApplication.save();  
				
	} 
	
	// this method was recently added
	public static Boolean allTablesAvailableOrDifferentCurrentOrder(List<Table> tables, Order order) throws
	InvalidInputException{
		Boolean goz = true;
		for(Table table: tables) {
			if(table.getStatus() == (Status.Available)) {
				goz =true;
			}
			if (table.getOrder(table.numberOfOrders()-1) == order) {
				goz = true;
			} else {
				goz = false;
			}
		}
		return goz;
	}
	
	public static void addMenuItem(String name, Double price, String itemCategory) throws InvalidInputException{
		//check for valid input
		RestoApp r = RestoAppApplication.getr();
		
		if(price < 0) {
			throw new InvalidInputException("Price has to be greater than 0");
		}
		
		for(MenuItem x : r.getMenu().getMenuItems()) {
			if(x.getName().equals(name)) {
				throw new InvalidInputException("Name is already taken");
			}
		}
		
		ItemCategory aItemCat = null;
		
		switch(itemCategory) {
		case "Appetizer":
			aItemCat = ItemCategory.valueOf("Appetizer");
			break;
		case "Main":
			aItemCat = ItemCategory.valueOf("Main");
			break;
		case "Dessert":
			aItemCat = ItemCategory.valueOf("Dessert");
			break;
		case "AlcoholicBeverage":
			aItemCat = ItemCategory.valueOf("AlcoholicBeverage");
			break;
		case "NonAlcolholicBeverage":
			aItemCat = ItemCategory.valueOf("NonAlcolholicBeverage");
			break;
		default:
				throw new InvalidInputException("Must slect a menu item");
		}
		
		
		
		MenuItem item = new MenuItem(name, r.getMenu());
		
		item.setItemCategory(aItemCat);
		
		PricedMenuItem pItem = new PricedMenuItem(price, r, item);
		
		item.setCurrentPricedMenuItem(pItem);
		
		RestoAppApplication.save();
	}
	
	/* user will input all attributes, and they will be chanted only if they are different from current values */
	public static void updateMenuItem(String currentName, String newName, Double price, String itemCatagory) throws InvalidInputException{
		//check for valid input and if currentName exists
		//menu item can only be updated if its in current orders for some reason i.e. has current priced item
		RestoApp r = RestoAppApplication.getr(); 

		ItemCategory aItemCat = null;

		switch(itemCatagory) {
		case "Appetizer":
			aItemCat = ItemCategory.valueOf("Appetizer");
			break;
		case "Main":
			aItemCat = ItemCategory.valueOf("Main");
			break;
		case "Dessert":
			aItemCat = ItemCategory.valueOf("Dessert");
			break;
		case "AlcoholicBeverage":
			aItemCat = ItemCategory.valueOf("AlcoholicBeverage");
			break;
		case "NonAlcolholicBeverage":
			aItemCat = ItemCategory.valueOf("NonAlcolholicBeverage");
			break;
		default:
			throw new InvalidInputException("Must slect a menu item");
		}
		
		MenuItem item = null;
		
		if(currentName == null || newName == null || price == null || itemCatagory == null) {
			throw new InvalidInputException("One of the parametrs is null");
		}
		
		for(MenuItem m : r.getMenu().getMenuItems()) {
			if(m.getName().equals(currentName)) {
				item = m; 
			}
		}
		
		if(item == null) {
			throw new InvalidInputException("menu item doesn't exist");
		}
		
		for(MenuItem x : r.getMenu().getMenuItems()) {
			if(x.getName().equals(newName)) {
				throw new InvalidInputException("name already exists");
			}
		}
		
		if(price < 0) {
			throw new InvalidInputException("Price has to be greater than 0");
		}
		
		if(!item.hasCurrentPricedMenuItem()) {
			throw new InvalidInputException("Menu item doesn't have current priced menu item");
		}
		
		if(!newName.equals(item.getName())) {
			item.setName(newName);
		}
		
		if(price != item.getCurrentPricedMenuItem().getPrice()) {
			PricedMenuItem pItem = item.addPricedMenuItem(price, r);
			item.setCurrentPricedMenuItem(pItem);
		}
		
		if(!item.getItemCategory().toString().equals(itemCatagory)) {
			item.setItemCategory(aItemCat);
		}
		
		RestoAppApplication.save();  
		
	}
	
	
	//finds and remove menu item by its name
	public static void removeMenuItem(String menuItem) throws InvalidInputException{
		
		RestoApp r = RestoAppApplication.getr();
		MenuItem mItem = null;
		for(MenuItem m : r.getMenu().getMenuItems()) {
			if(m.getName().equals(menuItem)) {
				mItem = m; 
			}
		}
		
		if(mItem == null) {
			throw new InvalidInputException("menu item doesn't exist");
		}
		
		mItem.setCurrentPricedMenuItem(null);
	
		RestoAppApplication.save();

	}

	
	
	public static List<OrderItem> getOrderItems(Table table) throws InvalidInputException{
		String error = "";
		if(table  == null) {
			error =" PLease select a table";	
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		RestoApp r = RestoAppApplication.getr();

		List<Table> currentTables = r.getCurrentTables();
		boolean current = true; 

		current = currentTables.contains(table);
		if(current == false) {
			throw new InvalidInputException("This table doesn't exist");
		}

		if(table.getStatus() == (Status.Available)) {
			throw new InvalidInputException("This table has no orders");	
		}

		Order lastOrder = null;
		if(table.numberOfOrders()>0) {
			lastOrder = table.getOrder(table.numberOfOrders()-1);

		}
		else {
			throw new InvalidInputException("this table has no orders");
		}
		List<Seat> currentSeats = table.getCurrentSeats();
		List<OrderItem> result = new ArrayList<OrderItem>();
		List<OrderItem> orderItems = new ArrayList<OrderItem>();

		Order order ;

		for (Seat currentSeat : currentSeats) {
			orderItems = currentSeat.getOrderItems();

			for (OrderItem orderitem : orderItems) {
				order =	orderitem.getOrder();

				if (lastOrder.equals(order) && !result.contains(orderitem)) {

					result.add(orderitem);

				}
			}


		}
        return result;



	}
	
	public static void orderMenuItem(MenuItem menuItem,int quantity, List<Seat> seats) throws InvalidInputException{
		RestoApp r = RestoAppApplication.getr();

//		try {	
//			MenuItem menuItem = MenuItem.getWithName(menuItemString);
//		}catch(Exception e) {
//			String error1 = e.getMessage();
//			throw new InvalidInputException(error1);
//			
//		}
//		MenuItem menuItem = MenuItem.getWithName(menuItemString);
		Menu m = r.getMenu();
		
		if ( quantity < 1) {
			throw new InvalidInputException(" quantity < 0");
		}
		if (menuItem == null ) {
			throw new InvalidInputException("menuitems = 0");
		}
		if (seats.size() == 0) {
			throw new InvalidInputException(" seats = 0");
		}
		
		boolean current = menuItem.hasCurrentPricedMenuItem();
		if(current == false) {
			throw new InvalidInputException("does not have currentPricedmenuitem");
		}
		
		List<Table> currentTables = r.getCurrentTables();
		Order lastOrder = null;
		for(Seat s : seats) {
			Table table = s.getTable();
			current = currentTables.contains(table);
			if(!current) {
				throw new InvalidInputException("table is not a current Table");
			}
			List<Seat> currentSeats = table.getCurrentSeats();
			current = currentSeats.contains(s);
			if (!current) {
				throw new InvalidInputException("seat is not cointained in seats");
			}
			
			if(lastOrder == null) {

				if (table.numberOfOrders() > 0) {
					lastOrder = table.getOrder(table.numberOfOrders()-1);
				}else {
					throw new InvalidInputException("table has no orders");
				}
			}else {
				Order comparedOrder = null;
				if(table.numberOfOrders() > 0) {
					comparedOrder = table.getOrder(table.numberOfOrders() - 1);
				}else {
					throw new InvalidInputException("table has no orders");
				}
				if(!comparedOrder.equals(lastOrder)) {
					throw new InvalidInputException("theres something wrong");
				}
			}	
		}
		if (lastOrder == null) {
			throw new InvalidInputException("last order is null");
		}
		
		PricedMenuItem pmi = menuItem.getCurrentPricedMenuItem();
		boolean itemCreated = false;
		OrderItem newItem = null;
		
		for (Seat s1 : seats) {
			Table table = s1.getTable();
			
			if (itemCreated) {
				table.addToOrderItem(newItem, s1);
			}else {
				OrderItem lastItem = null;
				if(lastOrder.numberOfOrderItems()>0) {
					lastItem = lastOrder.getOrderItem(lastOrder.numberOfOrderItems()-1);
				}
				table.orderItem(quantity, lastOrder, s1, pmi);
				
				if(lastOrder.numberOfOrderItems() > 0 && !lastOrder.getOrderItem(lastOrder.numberOfOrderItems()-1).equals(lastItem)) {
					itemCreated = true;
					newItem = lastOrder.getOrderItem(lastOrder.numberOfOrderItems() -1);
				}
			}
		}
		if (itemCreated == false) {
			throw new InvalidInputException("item not created. check whats wrong");
		}
		RestoAppApplication.save();
		
	}
	
	public static void cancelOrderItem(OrderItem aOrderItem) throws
	InvalidInputException{
		
		if(aOrderItem == null) {
			throw new InvalidInputException("The orderItem is null");
		}
		
		RestoApp r = RestoAppApplication.getr();//here symbolically
		List<Seat> seats = aOrderItem.getSeats();
		Order order =aOrderItem.getOrder();
		
		List<Table> tables = new ArrayList();
		List<Seat> dummySeats = new ArrayList<Seat>(seats);
		for(Seat seat: dummySeats) {
			Table table = seat.getTable();
			Order lastOrder = null;
			if(table.numberOfOrders()>0) {
				lastOrder = table.getOrder(table.numberOfOrders()-1);
			}else  {
				throw new InvalidInputException("Table does not have an order");
			}
			
			if(lastOrder.equals(order) && !(tables.contains(table))) {
				tables.add(table);
			}
			List<Table> copyOfTable = new ArrayList<Table>(tables);
			for(Table eachTable: copyOfTable) {
				eachTable.cancelOrderItem(aOrderItem);
			}	
		}
		RestoAppApplication.save();  
				
	}	
	public static void cancelOrder(Table table) throws
	InvalidInputException{
		if(table == null) {
			throw new InvalidInputException("table entered is null");
		}

		RestoApp r = RestoAppApplication.getr();
		List<Table> currentTables = r.getCurrentTables();
		
		if(!currentTables.contains(table)){
			throw new InvalidInputException("table entered isn't a current table");
		}else {
			table.cancelOrder();
		}		
		RestoAppApplication.save();  				
	}
	public static void issueBill(List<Seat> seats) throws InvalidInputException {
		RestoApp r = RestoAppApplication.getr();
		
		if(seats == null || seats.size() == 0) {
			throw new InvalidInputException("List of seats is empty. Or the list contains seats which do not exist.");
		}
		List<Table> currentTables = r.getCurrentTables();
		Order lastOrder = null;

		for (Seat s1: seats) {
			
			Table table = s1.getTable();
			boolean current = currentTables.contains(table);
			if (!current) {
				throw new InvalidInputException("This seat's table does not exist.");
			}
			
			List<Seat> currentSeats = table.getCurrentSeats();
			current = currentSeats.contains(s1);
			if (!current) {
				throw new InvalidInputException("Seat not found for this particular table.");
			}
			
			if (lastOrder == null) {
				if(table.numberOfOrders()>0) {
					lastOrder = table.getOrder(table.numberOfOrders()-1);
				}
				else {
					throw new InvalidInputException("No Last Order");
				}			
			}
			
			else {
				Order comparedOrder = null;
				if(table.numberOfOrders()>0) {
					comparedOrder = table.getOrder(table.numberOfOrders()-1);
				}
				else {
					throw new InvalidInputException("No Last Order for this seat.");
				}
				
				if(!comparedOrder.equals(lastOrder)) {
					throw new InvalidInputException("Last Order should be same for every seat");
				}
			}
		}
		
		if (lastOrder == null) {
			throw new InvalidInputException("No Last Order found");
		}
			
		//The first for loop ends here
	
		boolean billCreated = false;
		Bill newBill = null; 
		
		for(Seat s1: seats) {
			Table table = s1.getTable();
			
			if(billCreated) {
				table.addToBill(newBill, s1);
			}
			else {
				Bill lastBill = null;
				if (lastOrder.numberOfBills()>0) {
					lastBill = lastOrder.getBill(lastOrder.numberOfBills()-1);
				}
				table.billForSeat(lastOrder, s1);
				if(lastOrder.numberOfBills()>0 && !lastOrder.getBill(lastOrder.numberOfBills()-1).equals(lastBill)) {
					billCreated = true;
					newBill = lastOrder.getBill(lastOrder.numberOfBills()-1);
				}
				
				}
			}
		
		if (billCreated == false) {
			throw new InvalidInputException("There is no billCreated");
		}
		
		RestoAppApplication.save();
		}

}
