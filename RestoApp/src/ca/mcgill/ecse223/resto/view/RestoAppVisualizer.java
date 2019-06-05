package ca.mcgill.ecse223.resto.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;

@SuppressWarnings("serial")
public class RestoAppVisualizer extends JFrame {

	public static final int CANVAS_WIDTH = 400;
	public static final int CANVAS_HEIGHT = 200;

	public static final Color LINE_COLOR = Color.WHITE;
	public static final Color CANVAS_BACKGROUND = Color.LIGHT_GRAY;

	private int pX = 0, pY = 0;
	private int dX = 0, dY = 0;
	private int rX = 0, rY = 0;
	
	private int currentCanvasX = 0;
	private int currentCanvasY = 0;

	public int horLinesGap = 20;
	public int verLinesGap = 20;
	private static int zoom = 20;

	private Grid grid;
	private JFrame UI;
	private static List<Table> tables = RestoAppApplication.getr().getCurrentTables();
	public static List<Table> selectedTables = new ArrayList<Table>();

	public RestoAppVisualizer(JFrame frame) {
		UI = frame;
		grid = new Grid();
		grid.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		grid.setBackground(CANVAS_BACKGROUND);

		Container cp = getContentPane();
		cp.add(grid);

		addEventListeners();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		pack();
		setVisible(true);
	}
	
	private static Table[] getSelectedTables() {
		return (Table[])selectedTables.toArray();
	}
	
	public static Table getCorrespondingTable(int x, int y) {
		int tx, ty, width, length;
		for (Table table : tables) {
			tx = table.getX()*zoom;
			ty = table.getY()*zoom;
			width = table.getWidth()*zoom;
			length = table.getLength()*zoom;
			System.out.println(tx+":"+ty+":"+width+":"+length);
			if(x > tx && x < tx+width && y > ty && y < ty + length) {
				return table;
			}
		}
		return null;
	}
	
	public static boolean isSelected(Table table) {
		return selectedTables.contains((Table)table);
	}
	
	public void addCorrespondingTable(int x, int y) {
		Table tempTable = getCorrespondingTable(x, y);
		if(tempTable != null) {
			selectedTables.add(tempTable);
		}
		repaint();
	}
	public void addCorrespondingTable(Table table) {
		if(table != null) {
			selectedTables.add(table);
		}
		repaint();
	}
	
	public void removeCorrespondingTable(int x, int y) {
		Table tempTable = getCorrespondingTable(x, y);
		if(tempTable != null) {
			selectedTables.remove(tempTable);
		}
		repaint();
	}
	public void removeCorrespondingTable(Table table) {
			selectedTables.remove(table);
			repaint();
	}
	
	public void toggleTableSelection(Table table) {
		if(isSelected(table)) {
			removeCorrespondingTable(table);
		}else {
			addCorrespondingTable(table);
		}
		repaint();
	}

	private void addEventListeners() {

		grid.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.getWheelRotation() > 0) {
					if (horLinesGap > 2 && verLinesGap > 2 & zoom > 2) {
						horLinesGap -= 1;
						verLinesGap -= 1;
						zoom -= 1;
					}

				} else {
					horLinesGap += 1;
					verLinesGap += 1;
					zoom += 1;
				}
				repaint();
			}
		});

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				System.out.println(
						"Canvas size: " + e.getComponent().getWidth() + "x, by " + e.getComponent().getHeight() + "y");
			}
		});

		grid.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				System.out.println("click");
				toggleTableSelection(getCorrespondingTable(e.getX(), e.getY()));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				if (SwingUtilities.isLeftMouseButton(e)) {
					pX = e.getX();
					pY = e.getY();
					System.out.println("press");
					UI.toFront();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseReleased(e);
				if (SwingUtilities.isLeftMouseButton(e)) {
					rX = e.getX();
					rY = e.getY();
					System.out.println("release");
					UI.toFront();
				}
			}
		});

		grid.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					dX = pX - e.getX();
					dY = pY - e.getY();
					repaint();
					System.out.println("drag");
				}
			}
		});

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ESCAPE:
					dispatchEvent(new WindowEvent((JFrame) e.getComponent(), WindowEvent.WINDOW_CLOSING));
					break;
				}
			}
		});
	}

	class Grid extends JPanel {

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			int width = getWidth(), height = getHeight();
			g.setColor(LINE_COLOR);
			for (int i = 0; i < width / horLinesGap; i++) {
				g.drawLine(((i * horLinesGap - dX) % width + width) % width, 0,
						((i * horLinesGap - dX ) % width + width) % width, height);
			}
			for (int i = 0; i < height / verLinesGap; i++) {
				g.drawLine(0, ((i * verLinesGap - dY) % height + height) % height, width,
						((i * verLinesGap - dY) % height + height) % height);
			}
			for (Table currentTable : RestoAppApplication.getr().getCurrentTables()) {
				GraphicTable.draw(g, currentTable, zoom);
			}
		}

	}

}
