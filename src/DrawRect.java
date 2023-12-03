import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DrawRect extends JPanel {

	public static final int HEIGHT = 900;
	public static final int WIDTH = 30;
	public static final Color RECT_COLOR = new Color(135, 206, 235);
	public static final double HEIGHT_PERCENTAGE = 0.9;

	ArrayList<Integer> arr;

	public DrawRect(ArrayList<Integer> heights) {
		arr = heights;
	}

	// Changing the instance variable so that paint component is called
	public void setArr(ArrayList<Integer> newArr) {
		arr = newArr;
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		super.paintComponent(graphics);
		graphics.setColor(RECT_COLOR);

		int numRectangles = arr.size();
		int availableWidth = getWidth() - 20; // Adjusted for padding

		int totalWidth = numRectangles * (WIDTH + 4);
		int startX = (availableWidth - totalWidth) / 2 + 10; // Center the rectangles

		for (int i = numRectangles - 1; i >= 0; i--) {
			int x = startX + (numRectangles - 1 - i) * (WIDTH + 4);
			int rectHeight = (int) (HEIGHT_PERCENTAGE * arr.get(i));
			int y = getHeight() - rectHeight;
			graphics.fillRect(x, y, WIDTH, rectHeight);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		int numRectangles = arr.size();
		int totalWidth = numRectangles * (WIDTH + 4);
		return new Dimension(totalWidth, HEIGHT);
	}
}
