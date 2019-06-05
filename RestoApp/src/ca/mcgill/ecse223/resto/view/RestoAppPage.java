package ca.mcgill.ecse223.resto.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;

//import org.jdatepicker.impl.JDatePanelImpl;
//import org.jdatepicker.impl.JDatePickerImpl;
//import org.jdatepicker.impl.SqlDateModel;

//import org.jdatepicker.impl.JDatePanelImpl;
//import org.jdatepicker.impl.JDatePickerImpl;
//import org.jdatepicker.impl.SqlDateModel;

//import ca.mcgill.ecse223.resto.view.DateLabelFormatter;
import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Bill;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.model.OrderItem;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;


import java.util.HashMap;


import java.util.HashMap;

import java.util.List;
import java.util.Properties;

import javax.swing.JSpinner;
import javax.swing.JTable;

public class RestoAppPage {

	private static final Table Table = null;
	private static JPanel contentPane = new JPanel();
	private MainMenu mainmenu;

	private static AddTab addtab;
	private static RemoveTab removetab;
	private static UpdateTab updatetab;
	private static MoveTab movetab;
	private static AddReservation addres;
	private static IndicateTable intable;
	private static GetOrderItems getorderitems;
	private static IssueBill issuebill;
	private static UpdateMenuItem updatemenuitem;
	private static DateSelection dateSelection;
	private static Statistics statistics;
	private static Cancel cancel;
	private static CancelOrderItem cancelorderitem;

	private DisplayMenu displaymenu;
	private static DisplayMenuItems displayMenuItemsAppetizer;
	private static DisplayMenuItems displayMenuItemsMain;
	private static DisplayMenuItems displayMenuItemsDessert;
	private static DisplayMenuItems displayMenuItemsAlcoholicBeverage;
	private static DisplayMenuItems displayMenuItemsNonAlcoholicBeverage;
	public static RestoAppVisualizer visualizer;

	public void displayGui() {
		JFrame frame = new JFrame("OG RestoApp");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		visualizer = new RestoAppVisualizer(frame);

		//JPanel contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new CardLayout());
		updatemenuitem = new UpdateMenuItem(contentPane);
		mainmenu = new MainMenu(contentPane);
		addtab = new AddTab(contentPane);
		removetab = new RemoveTab(contentPane);
		updatetab = new UpdateTab(contentPane);
		movetab = new MoveTab(contentPane);

		// addres = new AddReservation(contentPane);
		addres = AddReservation.createAddReservation(contentPane);

		cancel = new Cancel(contentPane);
		// cancelorderitem = new CancelOrderItem(contentPane);

		cancelorderitem = CancelOrderItem.createCancelViewOrder(contentPane);

		intable = IndicateTable.createIndicateTable(contentPane);
		displaymenu = DisplayMenu.createDisplayMenu(contentPane);
		// getorderitems= new GetOrderItems(contentPane);

		getorderitems = GetOrderItems.createViewOrder(contentPane);
		issuebill = IssueBill.createIssueBill(contentPane);
		dateSelection = new DateSelection(contentPane);
		statistics = new Statistics(contentPane);
		displayMenuItemsAppetizer = DisplayMenuItems.createDisplayMenuItems(contentPane, ItemCategory.Appetizer);
		displayMenuItemsMain = DisplayMenuItems.createDisplayMenuItems(contentPane, ItemCategory.Main);
		displayMenuItemsDessert = DisplayMenuItems.createDisplayMenuItems(contentPane, ItemCategory.Dessert);
		displayMenuItemsAlcoholicBeverage = DisplayMenuItems.createDisplayMenuItems(contentPane,
				ItemCategory.AlcoholicBeverage);
		displayMenuItemsNonAlcoholicBeverage = DisplayMenuItems.createDisplayMenuItems(contentPane,
				ItemCategory.NonAlcoholicBeverage);

		contentPane.add(mainmenu, "MainMenu");
		contentPane.add(addtab, "AddTab");
		contentPane.add(removetab, "RemoveTab");
		contentPane.add(updatetab, "UpdateTab");
		contentPane.add(movetab, "MoveTab");
		contentPane.add(cancel, "Cancel");
		contentPane.add(cancelorderitem, "CancelOrderItem");
		contentPane.add(addres, "AddReservation");
		contentPane.add(displaymenu, "DisplayMenu");
		contentPane.add(intable, "IndicateTable");
		contentPane.add(getorderitems, "GetOrderItems");
		contentPane.add(issuebill, "IssueBill");
		contentPane.add(updatemenuitem, "UpdateMenuItem");
		contentPane.add(dateSelection, "DateSelection");
		contentPane.add(statistics, "Statistics");
		contentPane.add(displayMenuItemsAppetizer, "DisplayMenuItemsAppetizer");
		contentPane.add(displayMenuItemsMain, "DisplayMenuItemsMain");
		contentPane.add(displayMenuItemsDessert, "DisplayMenuItemsDessert");
		contentPane.add(displayMenuItemsAlcoholicBeverage, "DisplayMenuItemsAlcoholicBeverage");
		contentPane.add(displayMenuItemsNonAlcoholicBeverage, "DisplayMenuItemsNonAlcoholicBeverage");
		// setLayout(null);
		// contentPane.add(reservetab, "ReserveTab");
		frame.setContentPane(contentPane);
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);

	}

	public static void refresh(JScrollPane t) {
		// GetOrderItems.createViewOrder(contentPane, t );
		contentPane.repaint();

		addres.repaint();
		addtab.repaint();
		removetab.repaint();
		updatetab.repaint();
		movetab.repaint();
		intable.repaint();
		System.out.println("12312313");
		// getorderitems.add(t);
		getorderitems = GetOrderItems.createViewOrder(contentPane);
		getorderitems.repaint();
		issuebill = IssueBill.createIssueBill(contentPane);
		issuebill.repaint();

		cancelorderitem = CancelOrderItem.createCancelViewOrder(contentPane);
		cancelorderitem.repaint();

	}

	public static void updateStats(int year, int month, int day, int hour, int min, int year2, int month2, int day2,
			int hour2, int min2) {
		contentPane.remove(statistics);
		statistics = new Statistics(contentPane, year, month, day, hour, min, year2, month2, day2, hour2, min2);
		contentPane.add(statistics, "Statistics");
		contentPane.repaint();
		statistics.repaint();
	}

	public static void updateDisplay2(JPanel contentPane, String name) {
		switch (name) {
		case "GetOrderItems":
			contentPane.remove(getorderitems);
			getorderitems = GetOrderItems.createViewOrder(contentPane);
			contentPane.add(getorderitems, "GetOrderItems");
		case "CancelOrderItem":
			contentPane.remove(cancelorderitem);
			cancelorderitem = CancelOrderItem.createCancelViewOrder(contentPane);
			contentPane.add(cancelorderitem, "CancelOrderItem");

		case "AddReservation":
			contentPane.remove(addres);
			addres = AddReservation.createAddReservation(contentPane);
			contentPane.add(addres, "AddReservation");

		case "IndicateTable":
			contentPane.remove(intable);
			intable = IndicateTable.createIndicateTable(contentPane);
			contentPane.add(intable, "IndicateTable");

		case "IssueBill":
			contentPane.remove(issuebill);
			issuebill = IssueBill.createIssueBill(contentPane);
			contentPane.add(issuebill, "IssueBill");

		}

	}

	public static void updateDisplay(JPanel contentPane, ItemCategory category) {
		switch (category) {
		case Appetizer:
			contentPane.remove(displayMenuItemsAppetizer);
			displayMenuItemsAppetizer = DisplayMenuItems.createDisplayMenuItems(contentPane, ItemCategory.Appetizer);
			contentPane.add(displayMenuItemsAppetizer, "DisplayMenuItemsAppetizer");
			break;
		case Main:
			contentPane.remove(displayMenuItemsMain);
			displayMenuItemsMain = DisplayMenuItems.createDisplayMenuItems(contentPane, ItemCategory.Main);
			contentPane.add(displayMenuItemsMain, "DisplayMenuItemsMain");
			break;
		case Dessert:
			contentPane.remove(displayMenuItemsDessert);
			displayMenuItemsDessert = DisplayMenuItems.createDisplayMenuItems(contentPane, ItemCategory.Dessert);
			contentPane.add(displayMenuItemsDessert, "DisplayMenuItemsDessert");
			break;
		case AlcoholicBeverage:
			contentPane.remove(displayMenuItemsAlcoholicBeverage);
			displayMenuItemsAlcoholicBeverage = DisplayMenuItems.createDisplayMenuItems(contentPane,
					ItemCategory.AlcoholicBeverage);
			contentPane.add(displayMenuItemsAlcoholicBeverage, "DisplayMenuItemsAlcoholicBeverage");
			break;
		case NonAlcoholicBeverage:
			contentPane.remove(displayMenuItemsNonAlcoholicBeverage);
			displayMenuItemsNonAlcoholicBeverage = DisplayMenuItems.createDisplayMenuItems(contentPane,
					ItemCategory.NonAlcoholicBeverage);
			contentPane.add(displayMenuItemsNonAlcoholicBeverage, "DisplayMenuItemsNonAlcoholicBeverage");
			break;
		}
	}

}

class MainMenu extends JPanel {

	private JPanel contentPane;
	private JButton addTable;
	private JButton removeTable;
	private JButton updateTable;
	private JButton changeLocation;
	private JButton displayMenu;
	private JButton addReservation;
	private JButton cancel;
	private JButton cancelOrderItem;
	private JButton issueBill;
	private JButton updateMenuItem;
	private JButton dateSelection;
	private static Boolean singletonController = false;

	@Override
	public Dimension getPreferredSize() {
		return (new Dimension(925, 450));
	}

	public MainMenu(JPanel panel) {
		if (!singletonController) {
			MainMenu(panel, true);
			singletonController = true;
		}
	}

	public void MainMenu(JPanel panel, boolean trivial) {
		contentPane = panel;
		setLayout(null);
		setOpaque(true);

		TitledBorder border = new TitledBorder("RestoApp");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		setBorder(border);

		// construct components
		// Create table button
		addTable = new JButton("Create Table");
		addTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();

				cardLayout.show(contentPane, "AddTab");
			}
		});
		addTable.setBounds(90, 26, 120, 60);
		add(addTable);

		// removetable button
		removeTable = new JButton("Remove table");
		removeTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();

				cardLayout.show(contentPane, "RemoveTab");
			}
		});
		removeTable.setBounds(90, 124, 120, 60);
		add(removeTable);

		// update table number
		updateTable = new JButton("Update table");
		updateTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();

				cardLayout.show(contentPane, "UpdateTab");
			}
		});
		updateTable.setBounds(300, 26, 120, 60);
		add(updateTable);

		dateSelection = new JButton("Statistics");
		dateSelection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();

				cardLayout.show(contentPane, "DateSelection");
			}
		});
		dateSelection.setBounds(90, 320, 120, 60);
		add(dateSelection);
		
		// cancel order
		cancel = new JButton("Cancel Order");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();

				cardLayout.show(contentPane, "Cancel");
			}
		});

		cancel.setBounds(500, 26, 120, 60); // need to be updated with right locations
		add(cancel);

		// cancel orderItem
		cancelOrderItem = new JButton("Cancel Order-Item");
		cancelOrderItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				RestoAppPage.updateDisplay2(contentPane, "CancelOrderItem");
				cardLayout.show(contentPane, "CancelOrderItem");
			}
		});
		cancelOrderItem.setBounds(700, 26, 150, 60); // need to be updated with right locations
		add(cancelOrderItem);

		// Move table button
		changeLocation = new JButton("Move Table");
		changeLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();

				cardLayout.show(contentPane, "MoveTab");
			}
		});
		changeLocation.setBounds(300, 124, 120, 60);
		add(changeLocation);

		// display menu button
		displayMenu = new JButton("Order");
		displayMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();

				cardLayout.show(contentPane, "DisplayMenu");
			}
		});
		displayMenu.setBounds(300, 220, 120, 60);
		add(displayMenu);

		// display add reservation button

		addReservation = new JButton("Add Reservation");
		addReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				RestoAppPage.updateDisplay2(contentPane, "AddReservation");
				cardLayout.show(contentPane, "AddReservation");
			}
		});
		addReservation.setBounds(90, 220, 120, 60);
		add(addReservation);

		// display indicate table button

		addReservation = new JButton("Indicate Table");
		addReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				RestoAppPage.updateDisplay2(contentPane, "IndicateTable");
				cardLayout.show(contentPane, "IndicateTable");
			}
		});
		addReservation.setBounds(500, 124, 150, 60);
		add(addReservation);

		// display get order button

		addReservation = new JButton("View Order Items");
		addReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();

				// cardLayout.show(contentPane, "GetOrderItems");
				RestoAppPage.updateDisplay2(contentPane, "GetOrderItems");
				// GetOrderItems c = (GetOrderItems) contentPane.getComponent(8);
				// c.refresh(contentPane);
				cardLayout.show(contentPane, "GetOrderItems");
			}
		});
		addReservation.setBounds(500, 220, 120, 60);
		add(addReservation);

		// IssueBill button
		issueBill = new JButton("Issue Bill");
		issueBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				RestoAppPage.updateDisplay2(contentPane, "IssueBill");
				cardLayout.show(contentPane, "IssueBill");
			}
		});
		issueBill.setBounds(700, 220, 120, 60);
		add(issueBill);

		// UpdateMenuItem
		updateMenuItem = new JButton("Update Menu Item");
		updateMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "UpdateMenuItem");
			}
		});
		updateMenuItem.setBounds(700, 124, 150, 60);
		add(updateMenuItem);
	}

}

class UpdateMenuItem extends JPanel {
	private JButton back;
	private String error = null;
	private static Boolean singletonController = false;
	// private JPanel frmReservation;
	private JTextField Date;
	private JTextField Time;
	private JTextField nameOfCustomer;
	private JTextField phoneNumber;
	private JTextField emailAddress;
	private JPanel contentPane;
	private JTextField numberOfPersons;
	private JButton btnStartOrder;

	public UpdateMenuItem(JPanel panel) {
		if (!singletonController) {
			UpdateMenuItem(panel, true);
			singletonController = true;
		}
	}

	private void UpdateMenuItem(JPanel panel, boolean trivial) {

		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "MainMenu");
			}
		});
		back.setBounds(700, 400, 120, 26);
		add(back);
		// errorMessage.setText(error);

		// elements for error message

		JLabel errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(300, 250, 500, 26);

		add(errorMessage);

		// setting setLayout to null is very important
		contentPane = panel;
		setLayout(null);
		setPreferredSize(new Dimension(300, 250));

		// creates the title
		TitledBorder border = new TitledBorder("Update Menu Item");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		setBorder(border);

		// frmReservation = new JPanel();

		Date = new JTextField();
		Date.setBounds(74, 15, 130, 26);
		add(Date);
		Date.setColumns(10);

		JLabel lblDate = new JLabel("Name :");
		lblDate.setBounds(6, 20, 85, 16);
		add(lblDate);

		JLabel lblTime = new JLabel("Price :");
		lblTime.setBounds(6, 60, 139, 16);
		add(lblTime);

		Time = new JTextField();
		Time.setBounds(74, 55, 130, 26);
		add(Time);
		Time.setColumns(10);

		JLabel lblNameOfCustomer = new JLabel("Current Name :");
		lblNameOfCustomer.setBounds(6, 140, 139, 16);
		add(lblNameOfCustomer);

		JLabel lblNewLabel = new JLabel("New Name : ");
		lblNewLabel.setBounds(6, 180, 139, 16);
		add(lblNewLabel);

		nameOfCustomer = new JTextField();
		nameOfCustomer.setBounds(157, 135, 130, 26);
		add(nameOfCustomer);
		nameOfCustomer.setColumns(10);

		phoneNumber = new JTextField();
		phoneNumber.setBounds(157, 175, 130, 26);
		add(phoneNumber);
		phoneNumber.setColumns(10);

		JLabel lblEmailAddress = new JLabel("Price :");
		lblEmailAddress.setBounds(6, 218, 139, 16);
		add(lblEmailAddress);

		emailAddress = new JTextField();
		emailAddress.setBounds(157, 213, 130, 26);
		add(emailAddress);
		emailAddress.setColumns(10);

		JLabel lblTableNumber = new JLabel("Category :");
		lblTableNumber.setBounds(6, 262, 139, 16);
		add(lblTableNumber);

		numberOfPersons = new JTextField();
		numberOfPersons.setBounds(157, 337, 130, 26);
		add(numberOfPersons);
		numberOfPersons.setColumns(10);

		JLabel lblTable = new JLabel("Category :");
		lblTable.setBounds(270, 20, 118, 16);
		add(lblTable);

		String[] Status2 = new String[] { "Main", "Appetizer", "Dessert", "AlcoholicBeverage",
				"NonAlcolholicBeverage" };
		JComboBox<String> comboBox2 = new JComboBox<String>(Status2);
		comboBox2.setBounds(252, 37, 136, 27);
		add(comboBox2);
		comboBox2.setToolTipText("Appetizer");

		JButton btnNewButton = new JButton("Add Menu Item");
		btnNewButton.setBounds(444, 36, 147, 29);
		add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errorMessage.setText("");
				String itemName = Date.getText();
				Double itemPrice = Double.parseDouble(Time.getText());
				String itemCategory = String.valueOf(comboBox2.getSelectedItem());

				try {
					RestoAppController.addMenuItem(itemName, itemPrice, itemCategory);
				} catch (InvalidInputException e2) {
					error = e2.getMessage();
					e2.printStackTrace();
					errorMessage.setText(error);
				}
				if (error == null || error.length() == 0) {
					// populate page with data

					Date.setText("");
					Time.setText("");
					//comboBox2.setText("");
				
				}

			}
		});

		String[] Status = new String[] { "Dessert", "Main", "Appetizer", "AlcoholicBeverage",
				"NonAlcolholic Beverage" };
		JComboBox<String> comboBox = new JComboBox<String>(Status);
		comboBox.setBounds(157, 251, 130, 27);
		add(comboBox);
		comboBox.setToolTipText("Appetizer");

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(6, 342, 61, 16);
		add(lblName);

		JButton btnUpdateMenuItem = new JButton("Update Menu Item");
		btnUpdateMenuItem.setBounds(444, 190, 147, 29);
		add(btnUpdateMenuItem);
		btnUpdateMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errorMessage.setText("");
				String currentName = nameOfCustomer.getText();
				String newName = phoneNumber.getText();
				Double price = Double.parseDouble(emailAddress.getText());
				String ItemCat = String.valueOf(comboBox.getSelectedItem());

				try {
					RestoAppController.updateMenuItem(currentName, newName, price, ItemCat);
				} catch (InvalidInputException e3) {
					error = e3.getMessage();
					e3.printStackTrace();
					errorMessage.setText(error);
				}
				if (error == null || error.length() == 0) {
					// populate page with data

					nameOfCustomer.setText("");
					phoneNumber.setText("");
					emailAddress.setText("");
					
					//comboBox2.setText("");
				
				}

			}
		});

		JButton btnDeleteMenuItem = new JButton("Delete Menu Item");
		btnDeleteMenuItem.setBounds(444, 337, 147, 29);
		add(btnDeleteMenuItem);
		btnDeleteMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errorMessage.setText("");
				String itemName = numberOfPersons.getText();

				System.out.print(itemName);
				try {
					RestoAppController.removeMenuItem(itemName);
				} catch (InvalidInputException e4) {
					error = e4.getMessage();
					e4.printStackTrace();
					errorMessage.setText(error);
				}
				if (error == null || error.length() == 0) {
					// populate page with data

					numberOfPersons.setText("");
				
					
					//comboBox2.setText("");
				
				}

			}
		});

		JSeparator separator = new JSeparator();
		separator.setBounds(6, 106, 800, 12);
		add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 302, 800, 12);
		add(separator_1);

	}

}

class GetOrderItems extends JPanel {
	private JButton back;
	private JButton btnEndOrder;
	private JPanel contentPane;
	private final JPanel contentPanel = new JPanel();
	private JLabel errorMessage;
	private String error = null;

	private GetOrderItems(JPanel panel) {
		configGetOrderItems(panel);
	}

	public static GetOrderItems createViewOrder(JPanel panel) {

		return new GetOrderItems(panel);

	}

	private void configGetOrderItems(JPanel panel) {
		removeAll();
		// elements for error message

		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(650, 25, 500, 26);

		add(errorMessage);

		// setting setLayout to null is very important
		contentPane = panel;
		setLayout(null);
		setPreferredSize(new Dimension(300, 250));

		// creates the title
		TitledBorder border = new TitledBorder("Get Order Items");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		setBorder(border);

		// 1st visualizer
		List<String> tempListSeats = new ArrayList<>();
		RestoApp restoApp = RestoAppApplication.getr();
		List<Table> tables = restoApp.getCurrentTables();
		for (int i = 0; i < tables.size(); i++) {
			tempListSeats.add("Table " + tables.get(i).getNumber());
		}

		String[] seats = new String[tempListSeats.size()];
		for (int i = 0; i < seats.length; i++) {
			seats[i] = tempListSeats.get(i);
		}
		JList listSeats = new JList(seats);
		listSeats.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane listScroller1 = new JScrollPane(listSeats);
		listScroller1.setBounds(15, 25, 120, 300);
		add(listScroller1);

		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "MainMenu");
			}
		});
		back.setBounds(600, 290, 120, 26);
		add(back);
		errorMessage.setText(error);

		JButton btnvieworderitems = new JButton("View Order Items");
		btnvieworderitems.setBounds(175, 125, 139, 29);
		add(btnvieworderitems);

		btnvieworderitems.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				errorMessage.setText("");
				List<OrderItem> orderItems = new ArrayList<OrderItem>();
				int indexOfSelection = listSeats.getSelectedIndex();
				String n = "";
				error = null;
				try {
					//Table table = RestoAppVisualizer.selectedTables.get(0);
					Table t2 = tables.get(indexOfSelection);
					
					orderItems = RestoAppController.getOrderItems(t2);

				} catch (InvalidInputException e) {
					error = e.getMessage();
					errorMessage.setText(error);
				} catch (ArrayIndexOutOfBoundsException f) {
					n = "Select a table to view its order items. ";
					errorMessage.setText(n);

				}

				// 2nd visualizer
				Order order;
				DefaultListModel listModel = new DefaultListModel();
				JList list3 = new JList(listModel);
				JScrollPane l = new JScrollPane(list3);
				l.setBounds(350, 25, 225, 300);
				add(l);

				for (int i = 0; i < orderItems.size(); i++) {
					for (int t = 0; t < orderItems.get(i).getSeats().size(); t++) {
						listModel.addElement(orderItems.get(i).getPricedMenuItem().getMenuItem().getName());
					}
				}
			}
		});
	}
}

class IndicateTable extends JPanel {
	private JButton back;
	private JButton btnStartOrder;
	private JButton btnEndOrder;
	private JPanel contentPane;
	private final JPanel contentPanel = new JPanel();
	private JLabel errorMessage;
	private String error = null;
	private static Boolean singletonController = false;

	public IndicateTable(JPanel panel) {
		configIndicateTable(panel);
	}

	public static IndicateTable createIndicateTable(JPanel panel) {

		return new IndicateTable(panel);

	}

	private void configIndicateTable(JPanel panel) {
		removeAll();
		// elements for error message

		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(250, 250, 500, 26);

		add(errorMessage);

		// setting setLayout to null is very important
		contentPane = panel;
		setLayout(null);
		setPreferredSize(new Dimension(300, 250));

		// creates the title
		TitledBorder border = new TitledBorder("Indicate Table");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		setBorder(border);

		// ordersList
		List<String> tempListSeats2 = new ArrayList<>();
		RestoApp restoApp = RestoAppApplication.getr();
		List<Order> Orders = restoApp.getOrders();
		for (int j = 0; j < Orders.size(); j++) {
			for (int p = 0; p < Orders.get(j).getTables().size(); p++) {
				tempListSeats2
						.add("Order " + Orders.get(j).getNumber() + " Table: " + Orders.get(j).getTable(p).getNumber());
			}
		}

		String[] seats2 = new String[tempListSeats2.size()];
		for (int i = 0; i < seats2.length; i++) {
			seats2[i] = tempListSeats2.get(i);
		}

		JList listSeats2 = new JList(seats2);
		listSeats2.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		JScrollPane listScroller5 = new JScrollPane(listSeats2);
		listScroller5.setBounds(500, 35, 120, 300);
		add(listScroller5);

		// tables list
		List<String> tempListSeats = new ArrayList<>();
		List<Table> tables = restoApp.getCurrentTables();
		for (int i = 0; i < tables.size(); i++) {
			tempListSeats.add("Table " + tables.get(i).getNumber());
		}

		String[] seats = new String[tempListSeats.size()];
		for (int i = 0; i < seats.length; i++) {
			seats[i] = tempListSeats.get(i);
		}
		JList listSeats = new JList(seats);
		listSeats.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane listScroller2 = new JScrollPane(listSeats);
		listScroller2.setBounds(30, 35, 120, 300);
		add(listScroller2);

		btnStartOrder = new JButton("Start Order");
		btnStartOrder.setBounds(200, 75, 150, 29);
		add(btnStartOrder);

		btnStartOrder.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				errorMessage.setText("");
				if (listSeats.getSelectedIndices() == null) {
					errorMessage.setText("please select an order");
				}
				int[] d = listSeats.getSelectedIndices();
				if (listSeats.getSelectedValue() == null) {
					errorMessage.setText("Please select a table");
				}
				List<Integer> tabList = new ArrayList();
				for (int o = 0; o < d.length; o++) {
					tabList.add(tables.get(d[o]).getNumber());
				}
				try {
					errorMessage.setText("");
					RestoAppController.startOrder(tabList);
				} catch (InvalidInputException e) {
					error = e.getMessage();
					errorMessage.setText(error);
				}
				RestoAppPage.updateDisplay2(contentPane, "IndicateTable");

			}
		});

		JLabel T222 = new JLabel("select a table from the left");
		T222.setBounds(200, 115, 600, 16);
		add(T222);

		btnEndOrder = new JButton("End Order");
		btnEndOrder.setBounds(200, 175, 150, 29);
		add(btnEndOrder);

		btnEndOrder.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				errorMessage.setText("");

				int d = listSeats2.getSelectedIndex();
				if (listSeats2.getSelectedValue() == null) {
					errorMessage.setText("please select an order");
				}
				String line = tempListSeats2.get(d);
				char numb = 0;
				for (int g = 0; g < line.length(); g++) {
					if (line.charAt(g) == ' ') {
						numb = line.charAt(g + 1);
					}
				}
				String number = "" + numb;
				int num = Integer.parseInt(number);

				try {
					errorMessage.setText("");
					RestoAppController.endOrder(num);
				} catch (InvalidInputException e) {
					error = e.getMessage();
					errorMessage.setText(error);
				}
//				RestoAppPage.refresh(null);
				RestoAppPage.updateDisplay2(contentPane, "IndicateTable");
			}
		});

		JLabel T2222 = new JLabel("select a order from the right");
		T2222.setBounds(200, 215, 600, 16);
		add(T2222);

		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "MainMenu");
			}
		});
		back.setBounds(200, 275, 120, 26);
		add(back);
		errorMessage.setText(error);

	}

}

class AddReservation extends JPanel {
	private JButton back;
	private JButton btnAddReservation;
	private JPanel contentPane;
	private final JPanel contentPanel = new JPanel();
	private JLabel errorMessage;

	// private JFrame frmReservation;
	private JTextField Date;
	private JTextField time;
	private JTextField nameOfCustomer;
	private JTextField emailAddress;
	private JTextField phoneNumber;
	private JTextField numberOfPersons;

	private JSpinner spinner;
	private int valueOfSpinner;
	private String error = null;
	// private static Boolean singletonController = false;

	private AddReservation(JPanel panel) {
		configAddReservation(panel);
	}

	public static AddReservation createAddReservation(JPanel panel) {
		return new AddReservation(panel);
	}

	private void configAddReservation(JPanel panel) {

		// JDatePickerImpl overviewDatePicker;
		JLabel overviewDateLabel;
		JTable overviewTable;
		JScrollPane overviewScrollPane;

		// JDatePickerImpl assignmentDatePicker;
		JLabel assignmentDateLabel;
		// elements for error message

		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(500, 100, 500, 26);

		add(errorMessage);

		// setting setLayout to null is very important
		contentPane = panel;
		setLayout(null);
		setPreferredSize(new Dimension(300, 250));

		// creates the title
		TitledBorder border = new TitledBorder("Add Reservation");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		setBorder(border);

		// bus assignment - date
		// assignmentDatePicker.getModel().setValue(null);

		List<String> tempListSeats = new ArrayList<>();
		RestoApp restoApp = RestoAppApplication.getr();
		List<Table> tables = restoApp.getCurrentTables();
		for (int i = 0; i < tables.size(); i++) {
			tempListSeats.add("Table " + tables.get(i).getNumber() + ", Seat " + tables.get(i).numberOfCurrentSeats());
		}

		String[] seats = new String[tempListSeats.size()];
		for (int i = 0; i < seats.length; i++) {
			seats[i] = tempListSeats.get(i);
		}
		JList listSeats = new JList(seats);
		listSeats.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane listScroller2 = new JScrollPane(listSeats);
		listScroller2.setBounds(325, 55, 120, 200);
		add(listScroller2);

		JLabel Tables = new JLabel("Tables :");
		Tables.setBounds(350, 28, 120, 16);
		add(Tables);

		Date = new JTextField();
		Date.setBounds(157, 15, 130, 26);
		add(Date);
		Date.setColumns(10);
		// assignmentDatePicker.getComponentPopupMenu().setLocation(100, 100);

		JLabel lblDate = new JLabel("Date(MM/dd/yyyy) :");
		lblDate.setBounds(6, 20, 130, 16);
		add(lblDate);

		JLabel lblTime = new JLabel("Time(HHMM) :");
		lblTime.setBounds(6, 60, 139, 16);
		add(lblTime);

		time = new JTextField();
		time.setBounds(157, 55, 130, 26);
		add(time);
		time.setColumns(10);

		JLabel lblNameOfCustomer = new JLabel("Name of customer :");
		lblNameOfCustomer.setBounds(6, 100, 139, 16);
		add(lblNameOfCustomer);

		JLabel lblNewLabel = new JLabel("Phone number : ");
		lblNewLabel.setBounds(6, 140, 139, 16);
		add(lblNewLabel);

		nameOfCustomer = new JTextField();
		nameOfCustomer.setBounds(157, 95, 130, 26);
		add(nameOfCustomer);
		nameOfCustomer.setColumns(10);

		phoneNumber = new JTextField();
		phoneNumber.setBounds(157, 135, 130, 26);
		add(phoneNumber);
		phoneNumber.setColumns(10);

		JLabel lblEmailAddress = new JLabel("Email address :");
		lblEmailAddress.setBounds(6, 180, 139, 16);
		add(lblEmailAddress);

		emailAddress = new JTextField();
		emailAddress.setBounds(157, 173, 130, 26);
		add(emailAddress);
		emailAddress.setColumns(10);

		JLabel lblTableNumber = new JLabel("Number of persons :");
		lblTableNumber.setBounds(6, 220, 139, 16);
		add(lblTableNumber);

		numberOfPersons = new JTextField();
		numberOfPersons.setBounds(157, 213, 130, 26);
		add(numberOfPersons);
		numberOfPersons.setColumns(10);

		JLabel T = new JLabel("Table(s) number (s) :");
		T.setBounds(6, 260, 150, 16);
		add(T);

		JLabel example = new JLabel("Enter a list of table numbers (ex: 1,5,6,12)");
		example.setBounds(6, 285, 300, 16);
		add(example);

		JTextField number = new JTextField();
		number.setBounds(157, 260, 130, 26);
		add(number);
		number.setColumns(10);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JButton btnAddReservation = new JButton("Add Reservation");
		btnAddReservation.setBounds(311, 299, 139, 29);
		add(btnAddReservation);
		btnAddReservation.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				errorMessage.setText("");
				error = null;
				String n = "";
				Time t2 = null;
				// call the controller

				try {
					SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
					java.util.Date utilDate = null;
					try {
						utilDate = sdf1.parse(Date.getText());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						errorMessage.setText(e1.getMessage());
					}
					java.sql.Date date = new java.sql.Date(utilDate.getTime());

					if (time.getText().length() == 5) {
						t2 = new Time(Integer.parseInt(time.getText().substring(0, 2)),
								Integer.parseInt(time.getText().substring(3, 5)), 0);
					} else if (time.getText().length() == 4) {
						t2 = new Time(Integer.parseInt(time.getText().substring(0, 1)),
								Integer.parseInt(time.getText().substring(2, 4)), 0);
					}

					int numberOfP = Integer.parseInt(numberOfPersons.getText());
					String name = nameOfCustomer.getText();
					String number2 = phoneNumber.getText();
					String email = emailAddress.getText();
					String tabs = number.getText();
					String[] tableNums = tabs.split(",");
					int[] tableInts = new int[tableNums.length];

					for (int i = 0; i < tableNums.length; i++) {
						tableInts[i] = Integer.parseInt(tableNums[i]);
					}

					Date d = date;
					Time t = t2;
					int index = (listSeats.getSelectedIndex());

					RestoAppController.reserveTable(d, t, numberOfP, name, number2, email, tableInts);

				} catch (InvalidInputException e) {
					error = e.getMessage();
					e.printStackTrace();
					errorMessage.setText(error);
				} catch (NumberFormatException f) {
					n = "All Fields must be specified to add a reservation. ";
					errorMessage.setText(n);

				}

				// update visuals

				// errorMessage.setText(error);
				// errorMessage.setText(n);

				if (error == null || error.length() == 0) {
					// populate page with data

					nameOfCustomer.setText("");
					phoneNumber.setText("");
					emailAddress.setText("");
					time.setText("");
					Date.setText("");
					numberOfPersons.setText("");
					number.setText("");

				}
				RestoAppPage.refresh(null);
			}

		});

		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "MainMenu");
			}
		});
		back.setBounds(80, 299, 120, 26);
		add(back);

	}
}

class AddTab extends JPanel {

	private JButton back;
	private JButton btnAddTable;
	private JPanel contentPane;
	private final JPanel contentPanel = new JPanel();
	private JLabel errorMessage;
	private JTextField lblNumberTextField;
	private JTextField lblTableWidthTextField_1;
	private JTextField lblTableLengthTextField_2;
	private JTextField lblXPositionTextField_3;
	private JTextField lblYPositionTextField_4;
	private JSpinner spinner;
	private int valueOfSpinner;
	private String error = null;
	private static Boolean singletonController = false;

	public AddTab(JPanel panel) {
		if (!singletonController) {
			AddTab(panel, true);
			singletonController = true;
		}
	}

	private void AddTab(JPanel panel, boolean trivial) {
		// elements for error message

		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(0, 250, 500, 26);

		add(errorMessage);

		// setting setLayout to null is very important
		contentPane = panel;
		setLayout(null);
		setPreferredSize(new Dimension(300, 250));

		// creates the title
		TitledBorder border = new TitledBorder("Add Table");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		setBorder(border);

		lblNumberTextField = new JTextField();
		lblNumberTextField.setBounds(112, 26, 86, 26);
		add(lblNumberTextField);
		lblNumberTextField.setColumns(10);

		JLabel lblNumber = new JLabel("Table Number:");
		lblNumber.setBounds(6, 31, 94, 16);
		add(lblNumber);

		JLabel lblNumberOfSeats = new JLabel("Number of Seats:");
		lblNumberOfSeats.setBounds(230, 31, 113, 16);
		add(lblNumberOfSeats);

		spinner = new JSpinner();
		spinner.setBounds(351, 26, 48, 26);
		add(spinner);

		lblTableWidthTextField_1 = new JTextField();
		lblTableWidthTextField_1.setBounds(114, 84, 86, 26);
		add(lblTableWidthTextField_1);
		lblTableWidthTextField_1.setColumns(10);

		JLabel lblTableWidth = new JLabel("Table Width:");
		lblTableWidth.setBounds(6, 89, 94, 16);
		add(lblTableWidth);

		lblTableLengthTextField_2 = new JTextField();
		lblTableLengthTextField_2.setBounds(345, 84, 86, 26);
		add(lblTableLengthTextField_2);
		lblTableLengthTextField_2.setColumns(10);

		JLabel lblTableLength = new JLabel("Table Length:");
		lblTableLength.setBounds(230, 89, 94, 16);
		add(lblTableLength);

		lblXPositionTextField_3 = new JTextField();
		lblXPositionTextField_3.setBounds(112, 148, 86, 26);
		add(lblXPositionTextField_3);
		lblXPositionTextField_3.setColumns(10);

		JLabel lblXPosition = new JLabel("X position:");
		lblXPosition.setBounds(6, 153, 94, 16);
		add(lblXPosition);

		lblYPositionTextField_4 = new JTextField();
		lblYPositionTextField_4.setBounds(345, 148, 86, 26);
		add(lblYPositionTextField_4);
		lblYPositionTextField_4.setColumns(10);

		JLabel lblYPosition = new JLabel("Y position:");
		lblYPosition.setBounds(230, 153, 94, 16);
		add(lblYPosition);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

			JButton btnAddTable = new JButton("Add Table");
			btnAddTable.setBounds(345, 210, 120, 26);
			add(btnAddTable);
			btnAddTable.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					errorMessage.setText("");
					error = null;
					String n = "";
					// call the controller

					try {
						int number = Integer.parseInt(lblNumberTextField.getText());
						int width = Integer.parseInt(lblTableWidthTextField_1.getText());
						int length = Integer.parseInt(lblTableLengthTextField_2.getText());
						int x = Integer.parseInt(lblXPositionTextField_3.getText());
						int y = Integer.parseInt(lblYPositionTextField_4.getText());
						int nbOfSeats = (Integer) spinner.getValue();

						RestoAppController.createTable(number, x, y, width, length, nbOfSeats);

					} catch (InvalidInputException e) {
						error = e.getMessage();
						e.printStackTrace();
						errorMessage.setText(error);
						// System.out.println("catch2");
					} catch (NumberFormatException f) {
						n = "All Fields must be specified to add a table. ";
						errorMessage.setText(n);

					}

					// update visuals

					// errorMessage.setText(error);

					// errorMessage.setText(n);
					// System.out.println(error);
					if (error == null || error.length() == 0) {
						// populate page with data

						lblNumberTextField.setText("");
						lblTableWidthTextField_1.setText("");
						lblTableLengthTextField_2.setText("");
						lblXPositionTextField_3.setText("");
						lblYPositionTextField_4.setText("");
					}
					RestoAppPage.refresh(null);

					RestoAppPage.visualizer.repaint();
				}

			});

			back = new JButton("Back");
			back.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CardLayout cardLayout = (CardLayout) contentPane.getLayout();
					cardLayout.show(contentPane, "MainMenu");
				}
			});
			back.setBounds(80, 210, 120, 26);
			add(back);

		}

	}

}

class Cancel extends JPanel {

	private JButton back;
	private JPanel contentPane;
	private JTextField lblNumberTextField;
	String error;
	JLabel errorMessage;

	private static Boolean singletonController = false;

	public Cancel(JPanel panel) {
		if (!singletonController) {
			Cancel(panel, true);
			singletonController = true;
		}
	}

	public void Cancel(JPanel panel, boolean something) {

		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(0, 250, 500, 26);
		add(errorMessage);

		contentPane = panel;
		setLayout(null);

		TitledBorder border = new TitledBorder("Cancel Order");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		setBorder(border);

		// construct components
		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "MainMenu");
			}
		});
		back.setBounds(80, 210, 120, 26);
		add(back);

		JLabel rblNumber = new JLabel("Table Number:");
		rblNumber.setBounds(6, 31, 94, 16);
		add(rblNumber);

		lblNumberTextField = new JTextField();
		lblNumberTextField.setBounds(112, 26, 86, 26);
		add(lblNumberTextField);
		lblNumberTextField.setColumns(10);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

			JButton btnAddTable = new JButton("Cancel Order");
			btnAddTable.setBounds(345, 210, 120, 26);
			add(btnAddTable);
			btnAddTable.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					errorMessage.setText("");
					error = null;
					// call the controller

					try {
						int tableNum = Integer.parseInt(lblNumberTextField.getText());
						RestoAppController.cancelOrder(Table.getWithNumber(tableNum));
					} catch (InvalidInputException e) {
						error = e.getMessage();
						errorMessage.setText("You might have a blank field, or a use a letter please try again");
						e.printStackTrace();

					}

					// update visuals
					errorMessage.setText(error);
					if (error == null || error.length() == 0) {
						// populate page with data

						lblNumberTextField.setText("");

					}
					RestoAppPage.visualizer.repaint();
				}

			});
		}
	}

}

/*
 * class CancelOrderItem extends JPanel { private JButton back; private JButton
 * btnStartOrder; private JButton btnEndOrder; private JPanel contentPane;
 * private final JPanel contentPanel = new JPanel(); private JLabel
 * errorMessage; private String error = null;
 * 
 * private CancelOrderItem(JPanel panel) { configCancelOrderItem(panel); }
 * 
 * public static CancelOrderItem createCancelViewOrder(JPanel panel) {
 * 
 * return new CancelOrderItem(panel);
 * 
 * }
 * 
 * private void configCancelOrderItem(JPanel panel) { removeAll(); // elements
 * for error message
 * 
 * errorMessage = new JLabel(); errorMessage.setForeground(Color.RED);
 * errorMessage.setBounds(500, 25, 500, 26);
 * 
 * add(errorMessage);
 * 
 * // setting setLayout to null is very important contentPane = panel;
 * setLayout(null); setPreferredSize(new Dimension(300, 250));
 * 
 * // creates the title TitledBorder border = new
 * TitledBorder("Cancel Order Items");
 * border.setTitleJustification(TitledBorder.CENTER);
 * border.setTitlePosition(TitledBorder.TOP); setBorder(border);
 * 
 * 
 * //1st visualizer List<String> tempListSeats = new ArrayList<>(); RestoApp
 * restoApp = RestoAppApplication.getr(); List<Table> tables =
 * restoApp.getCurrentTables(); for (int i = 0; i < tables.size(); i++) {
 * tempListSeats.add("Table " + tables.get(i).getNumber()); }
 * 
 * String[] seats = new String[tempListSeats.size()]; for (int i = 0; i <
 * seats.length; i++) { seats[i] = tempListSeats.get(i); } JList listSeats = new
 * JList(seats);
 * listSeats.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); JScrollPane
 * listScroller1 = new JScrollPane(listSeats); listScroller1.setBounds(15, 25,
 * 120, 300); add(listScroller1);
 * 
 * back = new JButton("Back"); back.addActionListener(new ActionListener() {
 * public void actionPerformed(ActionEvent e) { CardLayout cardLayout =
 * (CardLayout) contentPane.getLayout(); cardLayout.show(contentPane,
 * "MainMenu"); } }); back.setBounds(500, 290, 120, 26); add(back);
 * errorMessage.setText(error);
 * 
 * 
 * 
 * JButton btnvieworderitems = new JButton("View Order Items");
 * btnvieworderitems.setBounds(175, 125, 139, 29); add(btnvieworderitems);
 * 
 * btnvieworderitems.addActionListener(new java.awt.event.ActionListener() {
 * public void actionPerformed(java.awt.event.ActionEvent evt) {
 * 
 * 
 * errorMessage.setText(""); List<OrderItem> orderItems = new
 * ArrayList<OrderItem>();
 * 
 * int indexOfSelection = listSeats.getSelectedIndex(); String n = ""; error =
 * null; try { Table t2 = tables.get(indexOfSelection);
 * System.out.println(t2.toString());
 * 
 * 
 * orderItems = RestoAppController.getOrderItems(t2); for (int i=0 ; i<=
 * orderItems.size() ; i++) { System.out.println(orderItems.toString()); } }
 * catch (InvalidInputException e) { error = e.getMessage();
 * e.printStackTrace(); errorMessage.setText(error); } catch
 * (ArrayIndexOutOfBoundsException f) { n =
 * "Select a table to view it's order items. "; System.out.println("catch");
 * errorMessage.setText(n);
 * 
 * } //2nd visualizer
 * 
 * List<String> tempListSeats2 = new ArrayList<>();
 * 
 * for (int i = 0; i < orderItems.size(); i++) { tempListSeats2.add("Items: " +
 * orderItems.get(i).getPricedMenuItem().getMenuItem().getName()); }
 * 
 * String[] seats2 = new String[tempListSeats2.size()]; for (int i = 0; i <
 * seats2.length; i++) { seats2[i] = tempListSeats2.get(i); }
 * //RestoAppPage.refresh(tables.get(indexOfSelection)); JList listSeats2 = new
 * JList(seats2);
 * listSeats2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); JScrollPane
 * listScroller2 = new JScrollPane(listSeats2); listScroller2.setBounds(350, 25,
 * 120, 300); System.out.println(this.getClass()); add(listScroller2);
 * System.out.println(indexOfSelection); RestoAppPage.refresh(listScroller2);
 * 
 * } });
 * 
 * JButton btnCancelorderitems = new JButton("Cancel Order Items");
 * btnCancelorderitems.setBounds(475, 125, 139, 29); add(btnCancelorderitems);
 * 
 * btnCancelorderitems.addActionListener(new java.awt.event.ActionListener() {
 * public void actionPerformed(java.awt.event.ActionEvent evt) {
 * errorMessage.setText(""); List<OrderItem> orderItems = new
 * ArrayList<OrderItem>();
 * 
 * int indexOfSelection = listSeats.getSelectedIndex(); String n = ""; error =
 * null; try { Table t2 = tables.get(indexOfSelection);
 * System.out.println(t2.toString());
 * 
 * 
 * orderItems = RestoAppController.getOrderItems(t2); for (int i=0 ; i<=
 * orderItems.size() ; i++) { System.out.println(orderItems.toString()); } }
 * catch (InvalidInputException e) { error = e.getMessage();
 * e.printStackTrace(); errorMessage.setText(error); } catch
 * (ArrayIndexOutOfBoundsException f) { n =
 * "Select a table to view it's order items. "; System.out.println("catch");
 * errorMessage.setText(n);
 * 
 * } //2nd visualizer
 * 
 * List<String> tempListSeats2 = new ArrayList<>();
 * 
 * for (int i = 0; i < orderItems.size(); i++) { tempListSeats2.add("Items: " +
 * orderItems.get(i).getPricedMenuItem().getMenuItem().getName()); }
 * 
 * String[] seats2 = new String[tempListSeats2.size()]; for (int i = 0; i <
 * seats2.length; i++) { seats2[i] = tempListSeats2.get(i); }
 * //RestoAppPage.refresh(tables.get(indexOfSelection)); JList listSeats2 = new
 * JList(seats2);
 * listSeats2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); JScrollPane
 * listScroller2 = new JScrollPane(listSeats2); listScroller2.setBounds(350, 25,
 * 120, 300); System.out.println(this.getClass()); add(listScroller2);
 * System.out.println(indexOfSelection); RestoAppPage.refresh(listScroller2); }
 * 
 * 
 * 
 * 
 * });
 * 
 * }}
 */
class CancelOrderItem extends JPanel {

	private JButton back;
	private JPanel contentPane;
	private JTextField lblNumberTextField;
	private final JPanel contentPanel = new JPanel();
	String error;
	JLabel errorMessage;
	private JSpinner spinner;
	private int valueOfSpinner;

	// private static Boolean singletonController = false;

	/*public CancelOrderItem(JPanel panel) {
		if (!singletonController) {
			CancelOrderItem(panel, true);
			singletonController = true;
		}
	} */
 	private CancelOrderItem(JPanel panel) {
 		CancelOrderItem(panel);
 	}
 	int b =0;
 	public static CancelOrderItem createCancelViewOrder(JPanel panel) {
 		
 			return new CancelOrderItem(panel);
 	}
 
 	private void CancelOrderItem(JPanel panel) {
 		removeAll();
 		
 		errorMessage = new JLabel();
 		errorMessage.setForeground(Color.RED);
 		errorMessage.setBounds(0, 250, 500, 26);
 		add(errorMessage);
 
 		contentPane = panel;
 		setLayout(null);
 
 		TitledBorder border = new TitledBorder("Cancel OrderItem");
 		border.setTitleJustification(TitledBorder.CENTER);
 		border.setTitlePosition(TitledBorder.TOP);
 		setBorder(border);
 
 		// construct components
 		back = new JButton("Back");
 		back.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent e) {
 				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
 				cardLayout.show(contentPane, "MainMenu");
 			}
 		});
 		back.setBounds(250, 265, 120, 26);
 		add(back);
 		
 		//code inserted below
 		RestoApp ro = RestoAppApplication.getr();
 		//testing, for current table, create order....
 
 		List<Order> orders = ro.getOrders();
 		List<OrderItem> orderItems = new ArrayList<OrderItem>();
 		for(Order aOrder: orders) {
 			if(aOrder != null) {
 				for(int i=0; i<aOrder.getOrderItems().size();i++) {
 					orderItems.add(aOrder.getOrderItem(i));
 				}
 			}
 		}

 		String[] strngOrderItems = new String[orderItems.size()];
 		for (int i = 0; i < strngOrderItems.length; i++) {
			strngOrderItems[i] = "Table: "+orderItems.get(i).getSeat(orderItems.get(i).getSeats().size()-1).getTable().getNumber()+": "+" OrderItem:"+orderItems.get(i).getPricedMenuItem().getMenuItem().getName();
 		}
 		
 		
 		JList listOrderItems = new JList(strngOrderItems);
 		listOrderItems.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
 		JScrollPane listScroller3 = new JScrollPane(listOrderItems);
 		listScroller3.setBounds(100, 55, 300, 200);
 		add(listScroller3);
 //get selected ndex
 		JLabel OrderItems = new JLabel("OrderItems :");
 		OrderItems.setBounds(101, 28, 120, 16);
 		add(OrderItems);
 		//code inserted above
 		{
 			JPanel buttonPane = new JPanel();
 			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
 
 			JButton btnAddTable = new JButton("Cancel OrderItem");
 			btnAddTable.setBounds(100, 265, 120, 26);
 			add(btnAddTable);
 			btnAddTable.addActionListener(new java.awt.event.ActionListener() {
 				public void actionPerformed(java.awt.event.ActionEvent evt) {
 					errorMessage.setText("");
 					int indexOfSeleccion = listOrderItems.getSelectedIndex();
 					error = null;
 					// call the controller
 
 					try {
 						OrderItem orderItemsSelected = orderItems.get(indexOfSeleccion);
 						RestoAppController.cancelOrderItem(orderItemsSelected);
 
 					} catch (InvalidInputException e) {
 						error = e.getMessage();
 						errorMessage.setText("You might have a blank field, or a use a letter please try again");
 						e.printStackTrace();
 
 					}
 
 					// update visuals
 					errorMessage.setText(error);
 					if (error == null || error.length() == 0) {
 						// populate page with data
 
 						
 
 					}
 					RestoAppPage.updateDisplay2(contentPane, "CancelOrderItem");
 					
 				}
 
 			});
 		}
 	}
 
 }

class RemoveTab extends JPanel {

	private JButton back;
	private JPanel contentPane;
	private JTextField lblNumberTextField;
	String error;
	private JLabel errorMessage;

	private static Boolean singletonController = false;

	public RemoveTab(JPanel panel) {
		if (!singletonController) {
			RemoveTab(panel, true);
			singletonController = true;
		}
	}

	public void RemoveTab(JPanel panel, boolean something) {

		contentPane = panel;
		setLayout(null);
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(0, 250, 500, 26);
		add(errorMessage);

		TitledBorder border = new TitledBorder("Remove Table");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		setBorder(border);

		// construct components
		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "MainMenu");
			}
		});

		back.setBounds(80, 210, 120, 26);
		add(back);

		lblNumberTextField = new JTextField();
		lblNumberTextField.setBounds(112, 26, 86, 26);
		add(lblNumberTextField);
		lblNumberTextField.setColumns(10);

		JLabel lblNumber = new JLabel("Table Number:");
		lblNumber.setBounds(6, 31, 94, 16);
		add(lblNumber);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

			JButton btnAddTable = new JButton("Remove Table");
			btnAddTable.setBounds(345, 210, 120, 26);
			add(btnAddTable);
			btnAddTable.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					errorMessage.setText("");
					error = null;
					// call the controller

					try {
						int number = Integer.parseInt(lblNumberTextField.getText());

						RestoAppController.changeTableNumtoTable(number);

					} catch (InvalidInputException e) {
						error = e.getMessage();
						e.printStackTrace();
						errorMessage.setText(error);
					}

					// update visuals
					// errorMessage.setText(error);
					if (error != null && error.length() > 0) {
						// populate page with data

						lblNumberTextField.setText("");
					}

					RestoAppPage.refresh(null);
					RestoAppPage.visualizer.repaint();
				}

			});
		}
	}

}

class UpdateTab extends JPanel {

	private JButton back;
	private JPanel contentPane;
	private JTextField seatsTextField_2;
	private JTextField tblnumTextField;
	private JTextField lblNumberTextField;

	String error;
	private JLabel errorMessage;
	private JTextField lblTableLengthTextField_2;

	private static Boolean singletonController = false;

	public UpdateTab(JPanel panel) {
		if (!singletonController) {
			UpdateTab(panel, true);
			singletonController = true;
		}
	}

	public void UpdateTab(JPanel panel, boolean something) {

		errorMessage = new JLabel();

		contentPane = panel;
		setLayout(null);
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(0, 250, 500, 26);
		add(errorMessage);

		TitledBorder border = new TitledBorder("Update Table");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		setBorder(border);

		// construct components
		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "MainMenu");
			}
		});
		back.setBounds(80, 210, 120, 26);
		add(back);

		JLabel lNumber = new JLabel("New Table Number:");
		lNumber.setBounds(210, 31, 120, 16);
		add(lNumber);

		seatsTextField_2 = new JTextField();
		seatsTextField_2.setBounds(112, 80, 86, 26);
		add(seatsTextField_2);
		seatsTextField_2.setColumns(10);

		tblnumTextField = new JTextField();
		tblnumTextField.setBounds(330, 26, 86, 26);
		add(tblnumTextField);
		tblnumTextField.setColumns(10);

		JLabel lblNumber = new JLabel("Number of seats:");
		lblNumber.setBounds(6, 90, 120, 20);
		add(lblNumber);

		JLabel rblNumber = new JLabel("Table Number:");
		rblNumber.setBounds(6, 31, 94, 16);
		add(rblNumber);

		lblNumberTextField = new JTextField();
		lblNumberTextField.setBounds(112, 26, 86, 26);
		add(lblNumberTextField);
		lblNumberTextField.setColumns(10);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

			JButton btnAddTable = new JButton("Update Table");
			btnAddTable.setBounds(345, 210, 120, 26);
			add(btnAddTable);
			btnAddTable.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					errorMessage.setText("");
					error = null;
					// call the controller

					try {
						int newNumber = Integer.parseInt(tblnumTextField.getText());
						int number = Integer.parseInt(lblNumberTextField.getText());
						int numberOfSeats = Integer.parseInt(seatsTextField_2.getText());

						RestoAppController.updateTable(number, newNumber, numberOfSeats);

					} catch (InvalidInputException e) {
						error = e.getMessage();
						e.printStackTrace();
						errorMessage.setText(error);

					}

					// update visuals

					if (error == null || error.length() == 0) {
						// populate page with data

						lblNumberTextField.setText("");
						tblnumTextField.setText("");
						seatsTextField_2.setText("");

					}

					RestoAppPage.refresh(null);
					RestoAppPage.visualizer.repaint();
				}

			});
		}

	}

}

class MoveTab extends JPanel {

	private JButton back;
	private JPanel contentPane;
	private JTextField lblNumberTextField;
	private JTextField corX;
	private JTextField corY;
	String error;
	JLabel errorMessage;

	private static Boolean singletonController = false;

	public MoveTab(JPanel panel) {
		if (!singletonController) {
			MoveTab(panel, true);
			singletonController = true;
		}
	}

	public void MoveTab(JPanel panel, boolean something) {

		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(0, 250, 500, 26);
		add(errorMessage);

		contentPane = panel;
		setLayout(null);

		TitledBorder border = new TitledBorder("Move Table");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		setBorder(border);

		// construct components
		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "MainMenu");
			}
		});
		back.setBounds(80, 210, 120, 26);
		add(back);

		JLabel lNumber = new JLabel("Cor Y:");
		lNumber.setBounds(210, 80, 120, 16);
		add(lNumber);

		corY = new JTextField();
		corY.setBounds(330, 80, 86, 26);
		add(corY);
		corY.setColumns(10);

		JLabel lblNumber = new JLabel("cor X:");
		lblNumber.setBounds(6, 90, 120, 20);
		add(lblNumber);

		corX = new JTextField();
		corX.setBounds(112, 80, 86, 26);
		add(corX);
		corX.setColumns(10);

		JLabel rblNumber = new JLabel("Table Number:");
		rblNumber.setBounds(6, 31, 94, 16);
		add(rblNumber);

		lblNumberTextField = new JTextField();
		lblNumberTextField.setBounds(112, 26, 86, 26);
		add(lblNumberTextField);
		lblNumberTextField.setColumns(10);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

			JButton btnAddTable = new JButton("Update Table");
			btnAddTable.setBounds(345, 210, 120, 26);
			add(btnAddTable);
			btnAddTable.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					errorMessage.setText("");
					error = null;
					// call the controller

					try {
						int tableNum = Integer.parseInt(lblNumberTextField.getText());
						int x = Integer.parseInt(corX.getText());
						int y = Integer.parseInt(corY.getText());

					} catch (Exception e) {
						errorMessage.setText("Please enter something in the text");
					}

					try {
						int tableNum = Integer.parseInt(lblNumberTextField.getText());
						int x = Integer.parseInt(corX.getText());
						int y = Integer.parseInt(corY.getText());

						RestoAppController.changeTableLocation(tableNum, x, y);

					} catch (InvalidInputException e) {
						error = e.getMessage();
						errorMessage.setText(error);
						e.printStackTrace();

					}

					// update visuals
					// errorMessage.setText(error);
					if (error == null || error.length() == 0) {
						// populate page with data

						lblNumberTextField.setText("");
						corX.setText("");
						corY.setText("");

					}

					RestoAppPage.refresh(null);
					RestoAppPage.visualizer.repaint();

				}

			});
		}
	}

}

class DisplayMenuItems extends JPanel {

	private JButton back;
	private JPanel contentPane;
	JLabel errorMessage;

	private DisplayMenuItems(JPanel panel, ItemCategory category) {
		configDisplayMenuItems(panel, category);
	}

	public static DisplayMenuItems createDisplayMenuItems(JPanel panel, ItemCategory category) {
		DisplayMenuItems dM = new DisplayMenuItems(panel, category);
		return dM;
	}

	private void configDisplayMenuItems(JPanel panel, ItemCategory category) {
		this.removeAll();
		contentPane = panel;
		setLayout(null);
		String error;

		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(250, 250, 500, 26);
		add(errorMessage);

		TitledBorder border = new TitledBorder(category.name());
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		setBorder(border);

		List<MenuItem> tempMenuItems = new ArrayList<MenuItem>();
		try {
			tempMenuItems = RestoAppController.getMenuItems(category);
		} catch (Exception e) {

		}
		String[] menuItems = new String[tempMenuItems.size()];
		for (int i = 0; i < menuItems.length; i++) {
			menuItems[i] = tempMenuItems.get(i).getName() + " "
					+ tempMenuItems.get(i).getCurrentPricedMenuItem().getPrice();
		}
		JList list = new JList(menuItems);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		JScrollPane listScroller = new JScrollPane(list);
		list.setVisibleRowCount(5);
		listScroller.setBounds(50, 40, 400, 100);
		add(listScroller);

		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 9, 1);
		JSpinner spinner = new JSpinner(spinnerModel);
		spinner.setBounds(163, 141, 48, 26);
		add(spinner);

		JLabel lblQuantity = new JLabel("Quantity :");
		lblQuantity.setBounds(50, 141, 113, 16);
		add(lblQuantity);

		JLabel lblSeats = new JLabel("Select seats :");
		lblSeats.setBounds(50, 161, 113, 16);
		add(lblSeats);

		List<String> tempListSeats = new ArrayList<>();
		RestoApp restoApp = RestoAppApplication.getr();
		List<Table> tables = restoApp.getCurrentTables();
		for (int i = 0; i < tables.size(); i++) {
			for (int j = 0; j < tables.get(i).numberOfCurrentSeats(); j++) {
				tempListSeats.add("Table " + tables.get(i).getNumber() + ", Seat " + (j + 1));
			}
		}
		String[] seats = new String[tempListSeats.size()];
		for (int i = 0; i < seats.length; i++) {
			seats[i] = tempListSeats.get(i);
		}
		JList listSeats = new JList(seats);
		listSeats.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane listScroller2 = new JScrollPane(listSeats);
		listScroller2.setBounds(50, 180, 120, 105);
		add(listScroller2);

		back = new JButton("Order");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errorMessage.setText("");
				// got string menuitem
				int d = list.getSelectedIndex();
				if (list.getSelectedValue() == null) {
					errorMessage.setText("Please select a menuItem");
				}
				String a = menuItems[d];
				a = a.replaceAll("[0-9]", "");
				a = a.replace(".", "");
				String f = "";
				for (int t = 0; t < a.length() - 1; t++) {
					f += a.charAt(t);
				}

				MenuItem menuitem = null;
				List<MenuItem> tempMenuItems = new ArrayList<MenuItem>();
				try {
					tempMenuItems = RestoAppController.getMenuItems(category);
				} catch (InvalidInputException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				for (MenuItem m : tempMenuItems) {
					if (m.getName().equals(f)) {
						menuitem = m;
					}
				}

				System.out.println(menuitem.getName());
				// got quantity
				int quantity = (int) spinner.getValue();
				// table number and seats
				int[] seat = listSeats.getSelectedIndices();
				List<Seat> selected = new ArrayList<>();
				for (int i : seat) {
					for (int k = 0; k < tables.size(); k++) {
						for (int j = 0; j < tables.get(k).numberOfCurrentSeats(); j++) {
							if (i == 0) {
								System.out.println("table" + tables.get(k).getNumber() + " seat " + (j + 1)
										+ " quantity " + quantity);

								Seat s1 = tables.get(k).getSeat(j);
								selected.add(s1);

							}
							i--;
						}
					}

				}

				try {
					RestoAppController.orderMenuItem(menuitem, quantity, selected);
				} catch (InvalidInputException e1) {
					String error1;
					error1 = e1.getMessage();
					errorMessage.setText(error1);
				}

				spinner.setValue(1);
				list.clearSelection();
				listSeats.clearSelection();
				// TODO: add item to order
			}
		});
		back.setBounds(345, 300, 120, 26);
		add(back);

		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "DisplayMenu");
			}
		});
		back.setBounds(50, 300, 120, 26);
		add(back);
	}
}

class Statistics extends JPanel {
	private JPanel contentPane;
	private JButton back;
	private JLabel lblError = new JLabel();
	private Date lowerDate;
	private Time lowerTime;
	private Date upperDate;
	private Time upperTime;

	private Date toDate(int year, int month, int day) {
		return new Date(year, month, day);
	}

	private Time toTime(int hour, int min) {
		return new Time(hour, min, 0);
	}

	public Statistics(JPanel panel, int year, int month, int day, int hour, int min, int year2, int month2, int day2,
			int hour2, int min2) {
		this.removeAll();
		contentPane = panel;
		setLayout(null);

		TitledBorder border = new TitledBorder("Statistics");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		setBorder(border);
		if (!(year == 0 && month == 0 && day == 0 && hour == 0 && min == 0 && year2 == 0 && month2 == 0 && day2 == 0
				&& hour2 == 0 && min2 == 0)) {
			lowerDate = toDate(year, month, day);
			lowerTime = toTime(hour, min);
			upperDate = toDate(year2, month2, day2);
			upperTime = toTime(hour2, min2);
		}
		JLabel title;
		if ((lowerDate != null)&&false) {
			title = new JLabel("Stats from " + lowerDate.toString() + ", " + lowerTime.toString() + ", to "
					+ lowerDate.toString() + ", " + lowerTime.toString());
		} else {
			title = new JLabel("Stats from lifetime");
		}
		title.setFont(getFont().deriveFont(Font.ITALIC));
		title.setFont(getFont().deriveFont(25f));

		title.setBounds(60, 40, 813, 26);
		add(title);
		/**
		 * 
		 */
		HashMap<MenuItem, Integer> hash = new HashMap<>();
		try {
			hash = RestoAppController.getTopSellingItems(lowerDate, lowerTime, upperDate, upperTime);
		} catch (InvalidInputException e1) {
			lblError.setText(e1.getMessage());
			lblError.setForeground(Color.red);
			lblError.setVisible(true);
			RestoAppPage.refresh(null);
			CardLayout cardLayout = (CardLayout) contentPane.getLayout();
			cardLayout.show(contentPane, "DateSelection");
		}
		List<MenuItem> listMenuItems = RestoAppApplication.getr().getMenu().getMenuItems();
		String[] menuItems = new String[listMenuItems.size()];
		for (int i = 0; i < listMenuItems.size(); i++) {
			if (hash.get(listMenuItems.get(i)) != null) {
				menuItems[i] = listMenuItems.get(i).getName() + " " + hash.get(listMenuItems.get(i));
			} else {
				menuItems[i] = listMenuItems.get(i).getName() + " " + 0;
			}
		}
		JList list = new JList(menuItems);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		JScrollPane listScroller = new JScrollPane(list);
		list.setVisibleRowCount(5);
		listScroller.setBounds(50, 80, 700, 150);
		add(listScroller);

		/**
		 * 
		 */
		lblError.setBounds(50, 250, 120, 26);
		add(lblError);

		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "DateSelection");
			}
		});
		back.setBounds(50, 300, 120, 26);
		add(back);
	}

	public Statistics(JPanel panel) {

	}
}

class DateSelection extends JPanel {
	private JPanel contentPane;
	private JButton back;
	private JButton next;
	private List<JTextField> listTxt = new ArrayList<JTextField>();
	private JLabel lblError = new JLabel();

	public DateSelection(JPanel panel) {
		this.removeAll();
		contentPane = panel;
		setLayout(null);

		TitledBorder border = new TitledBorder("Date Selection");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		setBorder(border);

		lblError.setBounds(50, 250, 120, 26);
		add(lblError);

		/**
		 * Lower Bound
		 */
		JLabel lblLLower = new JLabel("Lower bound date");
		lblLLower.setFont(getFont().deriveFont(Font.ITALIC));
		lblLLower.setBounds(60, 40, 113, 16);
		add(lblLLower);

		JTextField tly = new JTextField();
		tly.setBounds(160, 129, 48, 23);
		add(tly);
		listTxt.add(tly);

		JTextField tlmo = new JTextField();
		tlmo.setBounds(160, 101, 48, 23);
		add(tlmo);
		listTxt.add(tlmo);

		JLabel lblLDay = new JLabel("Day :");
		lblLDay.setBounds(50, 70, 113, 16);
		add(lblLDay);

		JTextField tld = new JTextField();
		tld.setBounds(160, 73, 48, 23);
		add(tld);
		listTxt.add(tld);

		JLabel lblLMonth = new JLabel("Month :");
		lblLMonth.setBounds(50, 100, 113, 16);
		add(lblLMonth);

		JLabel lblLYear = new JLabel("Year :");
		lblLYear.setBounds(50, 130, 113, 16);
		add(lblLYear);

		JLabel lblLHour = new JLabel("Hour :");
		lblLHour.setBounds(50, 160, 113, 16);
		add(lblLHour);

		JTextField tlh = new JTextField();
		tlh.setBounds(160, 157, 48, 23);
		add(tlh);
		listTxt.add(tlh);

		JLabel lblLMin = new JLabel("Minute :");
		lblLMin.setBounds(50, 190, 113, 16);
		add(lblLMin);

		JTextField tlmi = new JTextField();
		tlmi.setBounds(160, 185, 48, 23);
		add(tlmi);
		listTxt.add(tlmi);
		/**
		 * Upper Bound
		 */
		JLabel lblUpper = new JLabel("Upper bound date");
		lblUpper.setFont(getFont().deriveFont(Font.ITALIC));
		lblUpper.setBounds(560, 40, 113, 16);
		add(lblUpper);

		JLabel lblUDay = new JLabel("Day :");
		lblUDay.setBounds(550, 70, 113, 16);
		add(lblUDay);

		JLabel lblUMonth = new JLabel("Month :");
		lblUMonth.setBounds(550, 100, 113, 16);
		add(lblUMonth);

		JLabel lblUYear = new JLabel("Year :");
		lblUYear.setBounds(550, 130, 113, 16);
		add(lblUYear);

		JLabel lblUHour = new JLabel("Hour :");
		lblUHour.setBounds(550, 160, 113, 16);
		add(lblUHour);

		JLabel lblUMin = new JLabel("Minute :");
		lblUMin.setBounds(550, 190, 113, 16);
		add(lblUMin);

		JTextField tuy = new JTextField();
		tuy.setBounds(660, 129, 48, 23);
		add(tuy);
		listTxt.add(tuy);

		JTextField tumo = new JTextField();
		tumo.setBounds(660, 101, 48, 23);
		add(tumo);
		listTxt.add(tumo);

		JTextField tud = new JTextField();
		tud.setBounds(660, 73, 48, 23);
		add(tud);
		listTxt.add(tud);

		JTextField tuh = new JTextField();
		tuh.setBounds(660, 157, 48, 23);
		add(tuh);
		listTxt.add(tuh);

		JTextField tumi = new JTextField();
		tumi.setBounds(660, 185, 48, 23);
		add(tumi);
		listTxt.add(tumi);

		next = new JButton("Next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (allNull()) {
						RestoAppPage.updateStats(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
						CardLayout cardLayout = (CardLayout) contentPane.getLayout();
						cardLayout.show(contentPane, "Statistics");
					} else if (verifyDates()) {
						RestoAppPage.updateStats(Integer.parseInt(listTxt.get(0).getText()),
								Integer.parseInt(listTxt.get(1).getText()), Integer.parseInt(listTxt.get(2).getText()),
								Integer.parseInt(listTxt.get(3).getText()), Integer.parseInt(listTxt.get(4).getText()),
								Integer.parseInt(listTxt.get(5).getText()), Integer.parseInt(listTxt.get(6).getText()),
								Integer.parseInt(listTxt.get(7).getText()), Integer.parseInt(listTxt.get(8).getText()),
								Integer.parseInt(listTxt.get(9).getText()));
						CardLayout cardLayout = (CardLayout) contentPane.getLayout();
						cardLayout.show(contentPane, "Statistics");
					} else {
						lblError.setText("Invalid inputs");
						lblError.setForeground(Color.red);
						lblError.setVisible(true);
						RestoAppPage.refresh(null);
					}
				} catch (NumberFormatException evt) {
					lblError.setText("Only integers");
					lblError.setForeground(Color.red);
					lblError.setVisible(true);
					RestoAppPage.refresh(null);
				}
			}
		});
		next.setBounds(600, 290, 120, 26);
		add(next);

		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "MainMenu");
			}
		});
		back.setBounds(50, 300, 120, 26);
		add(back);
	}

	public boolean allNull() {
		boolean isnull = false;
		for (JTextField txt : listTxt) {
			isnull = !txt.getText().equals("") || isnull;
		}
		return !isnull;
	}

	private boolean verifyDates() throws NumberFormatException {
		boolean isnull = false;
		for (JTextField txt : listTxt) {
			isnull = !txt.getText().equals("") || isnull;
		}
		if (isnull) {
			for (JTextField txt : listTxt) {
				Integer.parseInt(txt.getText());
			}
		}

		// TODO TEST INPUT CASES AND LIMIT

		return true;
	}
}

class DisplayMenu extends JPanel {

	private JButton back;
	private JPanel contentPane;
	private JTextField lblNumberTextField;
	String error;
	private JLabel errorMessage;

	private static Boolean singletonControl = false;

	private DisplayMenu(JPanel panel) {
		configDisplayMenu(panel);
	}

	public static DisplayMenu createDisplayMenu(JPanel panel) {
		if (!singletonControl) {
			DisplayMenu dM = new DisplayMenu(panel);
			singletonControl = true;
			return dM;
		}
		return null;
	}

	private void configDisplayMenu(JPanel panel) {
		final int NB_CATEGORIES = 5;

		contentPane = panel;
		setLayout(null);

		TitledBorder border = new TitledBorder("Menu");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		setBorder(border);

		final int NB_CATEGORY_BUTTONS = NB_CATEGORIES;

		// An array of lists of JButtons
		JButton[] categoryButtons = new JButton[NB_CATEGORY_BUTTONS];

		JButton tempJButton;
		List<ItemCategory> listItemCategories = RestoAppController.getItemCategories();

		for (int i = 0; i < NB_CATEGORY_BUTTONS; i++) {
			String categoryName = listItemCategories.get(i).name().toString();
			tempJButton = new JButton(categoryName);
			categoryButtons[i] = tempJButton;
		}

		for (int i = 0; i < NB_CATEGORY_BUTTONS; i++) {
			categoryButtons[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CardLayout cardLayout = (CardLayout) contentPane.getLayout();
					switch (((JButton) e.getSource()).getLabel()) {
					case "Appetizer":
						RestoAppPage.updateDisplay(contentPane, ItemCategory.Appetizer);
						break;
					case "Main":
						RestoAppPage.updateDisplay(contentPane, ItemCategory.Main);
						break;
					case "Dessert":
						RestoAppPage.updateDisplay(contentPane, ItemCategory.Dessert);
						break;
					case "AlcoholicBeverage":
						RestoAppPage.updateDisplay(contentPane, ItemCategory.AlcoholicBeverage);
						break;
					case "NonAlcoholicBeverage":
						RestoAppPage.updateDisplay(contentPane, ItemCategory.NonAlcoholicBeverage);
						break;
					}
					cardLayout.show(contentPane, "DisplayMenuItems" + ((JButton) e.getSource()).getLabel());

				}
			});
			add(categoryButtons[i]);
			switch (i) {
			case 0:
				categoryButtons[i].setBounds(90, 26, 120, 60);
				break;
			case 1:
				categoryButtons[i].setBounds(90, 124, 120, 60);
				break;
			case 2:
				categoryButtons[i].setBounds(300, 26, 120, 60);
				break;
			case 3:
				categoryButtons[i].setBounds(300, 124, 120, 60);
				break;
			case 4:
				categoryButtons[i].setBounds(200, 220, 120, 60);
				break;
			}
		}

		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "MainMenu");
			}
		});
		back.setBounds(50, 300, 120, 26);
		add(back);
	}

}

class IssueBill extends JPanel {
	private JButton back;
	private JButton btnStartOrder;
	private JButton btnEndOrder;
	private JPanel contentPane;
	private final JPanel contentPanel = new JPanel();
	private JLabel errorMessage;
	private String error = null;

	private IssueBill(JPanel panel) {
		configIssueBill(panel);
	}

	public static IssueBill createIssueBill(JPanel panel) {
		return new IssueBill(panel);
	}

	private void configIssueBill(JPanel panel) {
		removeAll();
		// elements for error message

		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(650, 25, 500, 26);

		add(errorMessage);

		// setting setLayout to null is very important
		contentPane = panel;
		setLayout(null);
		setPreferredSize(new Dimension(300, 250));

		// creates the title
		TitledBorder border = new TitledBorder("Issue Bill");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		setBorder(border);

		// 1st visualizer
		List<String> tempListSeats = new ArrayList<>();
		RestoApp restoApp = RestoAppApplication.getr();
		List<Table> tables = restoApp.getCurrentTables();
		for (int i = 0; i < tables.size(); i++) {
			for (int j = 0; j < tables.get(i).numberOfCurrentSeats(); j++) {
				tempListSeats.add("Table " + tables.get(i).getNumber() + ", Seat " + (j + 1));
			}
		}
		String[] seats = new String[tempListSeats.size()];
		for (int i = 0; i < seats.length; i++) {
			seats[i] = tempListSeats.get(i);
		}
		JList listSeats = new JList(seats);
		listSeats.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane listScroller2 = new JScrollPane(listSeats);
		listScroller2.setBounds(50, 60, 120, 220);
		add(listScroller2);

		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "MainMenu");
			}
		});
		back.setBounds(700, 290, 120, 26);
		add(back);
		errorMessage.setText(error);

		JButton btnvieworderitems = new JButton("Issue Bill");
		btnvieworderitems.setBounds(210, 125, 139, 29);
		add(btnvieworderitems);

		btnvieworderitems.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				errorMessage.setText("");
				// table number and seats
				int[] seat = listSeats.getSelectedIndices();
				List<Seat> selected = new ArrayList<>();
				for (int i : seat) {
					for (int k = 0; k < tables.size(); k++) {
						for (int j = 0; j < tables.get(k).numberOfCurrentSeats(); j++) {
							if (i == 0) {
								System.out.println("table" + tables.get(k).getNumber() + " seat " + (j + 1));

								Seat s1 = tables.get(k).getSeat(j);
								selected.add(s1);

							}
							i--;
						}
					}

				}

				try {
					RestoAppController.issueBill(selected);
				} catch (InvalidInputException e1) {
					String error1;
					error1 = e1.getMessage();
					errorMessage.setText(error1);
				}

				// 2nd visualizer

				List<String> tempListSeats2 = new ArrayList<>();
				double totalPrice = 0;

				for (Seat s3 : selected) {
					List<Bill> b1 = s3.getBills();

					for (Bill b : b1) {
						List<OrderItem> o = b.getOrder().getOrderItems();
						double price;
						for (OrderItem o1 : o) {
							for (int y = 0; y < o1.getSeats().size(); y++) {
								if (o1.getSeat(y).equals(s3)) {

									price = o1.getPricedMenuItem().getPrice() * o1.getQuantity();
									if (o1.getSeats().size() > 1) {
										price = price / o1.getSeats().size();
									}
									tempListSeats2.add("Items: " + o1.getPricedMenuItem().getMenuItem().getName()
											+ " Price " + price);
									totalPrice = totalPrice + price;
									if (o1.equals(o.get(o.size() - 1))
											&& s3.equals(selected.get(selected.size() - 1))) {
										tempListSeats2.add("Total = " + totalPrice);
									} else {

									}

								}
							}
						}
					}
				}

				String[] seats2 = new String[tempListSeats2.size()];
				for (int i = 0; i < seats2.length; i++) {
					seats2[i] = tempListSeats2.get(i);
				}

				DefaultListModel listModel = new DefaultListModel();
				JList listSeats2 = new JList(listModel);
				JScrollPane listScroller = new JScrollPane(listSeats2);
				listScroller.setBounds(350, 25, 200, 300);
				add(listScroller);

				for (int q = 0; q < seats2.length; q++) {
					listModel.addElement(seats2[q]);
				}

			}
		});

	}
}

/*
 * private final JPanel contentPanel = new JPanel(); private JLabel
 * errorMessage; private JTextField lblNumberTextField; private JTextField
 * lblTableWidthTextField_1; private JTextField lblTableLengthTextField_2;
 * private JTextField lblXPositionTextField_3; private JTextField
 * lblYPositionTextField_4; private JSpinner spinner; private int
 * valueOfSpinner;
 * 
 * //Main Menu private final JPanel mainMenu = new JPanel(); private JButton
 * addTab; private JButton removeTable; private JButton updateTable; private
 * JButton changeTableLocation; private JButton menu;
 * 
 * //RemoveTable
 * 
 * 
 * // data elements private String error = null;
 * 
 * /** Creates new form BtmsPage
 */
/*
 * public RestoAppPage() { initComponents(); refreshData(); } /** Launch the
 * application.
 */
// public void initComponents {
// try {
// testt dialog = new testt();
// dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
// dialog.setVisible(true);
// } catch (Exception e) {
// e.printStackTrace();
// }
// }

/**
 * Create the dialog.
 */
/*
 * public void initComponents() {
 * 
 * // elements for error message errorMessage = new JLabel();
 * errorMessage.setForeground(Color.RED);
 * 
 * // data elements
 * 
 * setTitle("Add Table");
 * 
 * 
 * 
 * setBounds(100, 100, 450, 300); getContentPane().setLayout(new
 * BorderLayout()); contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
 * getContentPane().add(contentPanel, BorderLayout.CENTER);
 * contentPanel.setLayout(null);
 * 
 * lblNumberTextField = new JTextField(); lblNumberTextField.setBounds(112, 26,
 * 86, 26); contentPanel.add(lblNumberTextField);
 * lblNumberTextField.setColumns(10);
 * 
 * JLabel lblNumber = new JLabel("Table Number:"); lblNumber.setBounds(6, 31,
 * 94, 16); contentPanel.add(lblNumber);
 * 
 * JLabel lblNumberOfSeats = new JLabel("Number of Seats:");
 * lblNumberOfSeats.setBounds(230, 31, 113, 16);
 * contentPanel.add(lblNumberOfSeats);
 * 
 * spinner = new JSpinner(); spinner.setBounds(351, 26, 48, 26);
 * contentPanel.add(spinner);
 * 
 * // valueOfSpinner = (Integer)spinner.getValue();
 * 
 * 
 * lblTableWidthTextField_1 = new JTextField();
 * lblTableWidthTextField_1.setBounds(114, 84, 86, 26);
 * contentPanel.add(lblTableWidthTextField_1);
 * lblTableWidthTextField_1.setColumns(10);
 * 
 * JLabel lblTableWidth = new JLabel("Table Width:"); lblTableWidth.setBounds(6,
 * 89, 94, 16); contentPanel.add(lblTableWidth);
 * 
 * lblTableLengthTextField_2 = new JTextField();
 * lblTableLengthTextField_2.setBounds(345, 84, 86, 26);
 * contentPanel.add(lblTableLengthTextField_2);
 * lblTableLengthTextField_2.setColumns(10);
 * 
 * JLabel lblTableLength = new JLabel("Table Length:");
 * lblTableLength.setBounds(230, 89, 94, 16); contentPanel.add(lblTableLength);
 * 
 * lblXPositionTextField_3 = new JTextField();
 * lblXPositionTextField_3.setBounds(112, 148, 86, 26);
 * contentPanel.add(lblXPositionTextField_3);
 * lblXPositionTextField_3.setColumns(10);
 * 
 * JLabel lblXPosition = new JLabel("X position:"); lblXPosition.setBounds(6,
 * 153, 94, 16); contentPanel.add(lblXPosition);
 * 
 * lblYPositionTextField_4 = new JTextField();
 * lblYPositionTextField_4.setBounds(345, 148, 86, 26);
 * contentPanel.add(lblYPositionTextField_4);
 * lblYPositionTextField_4.setColumns(10);
 * 
 * JLabel lblYPosition = new JLabel("Y position:"); lblYPosition.setBounds(230,
 * 153, 94, 16); contentPanel.add(lblYPosition); { JPanel buttonPane = new
 * JPanel(); buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
 * getContentPane().add(buttonPane, BorderLayout.SOUTH);
 * 
 * JButton btnAddTable = new JButton("Add Table"); buttonPane.add(btnAddTable);
 * btnAddTable.addActionListener(new java.awt.event.ActionListener(){ public
 * void actionPerformed(java.awt.event.ActionEvent evt) {
 * btnAddTableActionPerformed(evt); }
 * 
 * }); } //pack();
 * 
 * }
 * 
 * 
 * 
 * private void refreshData() {// error errorMessage.setText(error); if (error
 * == null || error.length() == 0) { // populate page with data
 * 
 * lblNumberTextField.setText(""); lblTableWidthTextField_1.setText("");
 * lblTableLengthTextField_2.setText(""); lblXPositionTextField_3.setText("");
 * lblYPositionTextField_4.setText("");
 * 
 * 
 * }
 * 
 * 
 * // this is needed because the size of the window changes depending on whether
 * an error message is shown or not //pack(); }
 * 
 * private void btnAddTableActionPerformed(java.awt.event.ActionEvent evt) {
 * 
 * //clear error message error = null; // call the controller
 * 
 * try { int number = Integer.parseInt(lblNumberTextField.getText()); int width
 * = Integer.parseInt(lblTableWidthTextField_1.getText()); int length =
 * Integer.parseInt(lblTableLengthTextField_2.getText()); int x =
 * Integer.parseInt(lblXPositionTextField_3.getText()); int y =
 * Integer.parseInt(lblYPositionTextField_4.getText()); int nbOfSeats =
 * (Integer)spinner.getValue();
 * 
 * RestoAppController.createTable(number, x,y,width,length, nbOfSeats);
 * 
 * } catch (InvalidInputException e) { error = e.getMessage(); }
 * 
 * // update visuals refreshData(); }
 */
