package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Graphics;

import ca.mcgill.ecse223.resto.model.Table;

public class GraphicTable {
	public static void draw(Graphics g, Table table, int zoom) {
		int x = table.getX()*zoom, y = table.getY()*zoom, width = table.getWidth()*zoom , length = table.getLength()*zoom;
		g.setColor(Color.BLACK);
		
		g.drawLine(x, y, x + width, y);
		g.drawLine(x, y, x, y + length);
		g.drawLine(x, y + length, x + width, y + length);
		g.drawLine(x + width, y, x + width, y + length);
		if(RestoAppVisualizer.isSelected(table)){
			g.setColor(Color.red);
			g.fillRect(x+1, y+1, width-1, length-1);
		}
		g.setColor(Color.BLACK);
		g.drawString("ID:"+table.getNumber() , x + width / 2, y + length / 2);
		g.drawString("Seats:"+table.getCurrentSeats().size(), x + width / 2, y + length / 2 + 12);
		g.drawString("X"+x/zoom+":Y"+y/zoom, x + width / 2, y + length / 2 + 24);
	}
}