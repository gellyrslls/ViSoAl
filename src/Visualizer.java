import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class Visualizer extends JFrame implements ActionListener {

	public static final int WIDTH = 1620;
	public static final int HEIGHT = 1200;
	public static boolean sorted = false;
	public static char sortingAlgo;
	public static int noOfComparisons = 0;
	JLabel title, name, timeComplexity, spaceComplexity, comparisons;
	JButton shuffle, visualize;
	JComboBox<String> algorithms;
	JPanel mainPanel = new JPanel();
	ArrayList<Integer> heights = generateArr();
	DrawRect rectArr = new DrawRect(heights);

	// TODO: complete Initialization of sorting Algorithms
	BubbleSort bs = new BubbleSort();
	HeapSort hs = new HeapSort();
	InsertionSort is = new InsertionSort();
	MergeSort ms = new MergeSort();

	public Visualizer() throws InterruptedException {
		// Frame variables
		this.setTitle("Sorting Algorithms Visualizer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.WHITE);

		// Main frame layout
		this.setLayout(new BorderLayout());

		// Panels
		mainPanel.setBackground(new Color(64, 64, 64));
		mainPanel.setLayout(new BorderLayout());

		rectArr.setBackground(new Color(64, 64, 64));

		// Displayed Labels
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(new Color(64, 64, 64));
		title = new JLabel("Sorting Algorithms Visualizer");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Panton", Font.BOLD, 44));
		titlePanel.add(title);

		JPanel infoPanel = new JPanel(new GridLayout(3, 1));
		infoPanel.setBackground(new Color(64, 64, 64));
		name = new JLabel("Angelo Rosillosa");
		name.setForeground(Color.WHITE);
		name.setFont(new Font("Panton", Font.BOLD, 26));
		infoPanel.add(name);

		timeComplexity = new JLabel("Time Complexity: ");
		timeComplexity.setForeground(Color.WHITE);
		timeComplexity.setFont(new Font("MV Boli", Font.BOLD, 22));
		infoPanel.add(timeComplexity);

		spaceComplexity = new JLabel("Space Complexity: ");
		spaceComplexity.setForeground(Color.WHITE);
		spaceComplexity.setFont(new Font("MV Boli", Font.BOLD, 22));
		infoPanel.add(spaceComplexity);

		comparisons = new JLabel("No. of Comparisons: ");
		comparisons.setForeground(Color.WHITE);
		comparisons.setFont(new Font("MV Boli", Font.BOLD, 22));
		infoPanel.add(comparisons);

		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(infoPanel, BorderLayout.CENTER);

		// Drop-down menu
		JPanel dropdownPanel = new JPanel();
		dropdownPanel.setBackground(new Color(64, 64, 64));
		algorithms = new JComboBox<>(
				new String[] { "Select Algorithm", "Bubble Sort", "Heap Sort", "Insertion Sort", "Merge Sort" });
		algorithms.setFont(new Font("MV Boli", Font.BOLD, 26));
		dropdownPanel.add(algorithms);

		// Buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(new Color(64, 64, 64));
		visualize = new JButton("Visualize");
		visualize.setFont(new Font("MV Boli", Font.BOLD, 26));
		shuffle = new JButton("Shuffle");
		shuffle.setFont(new Font("MV Boli", Font.BOLD, 26));
		buttonsPanel.add(visualize);
		buttonsPanel.add(shuffle);

		mainPanel.add(dropdownPanel, BorderLayout.WEST);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

		// Adding panels to the frame
		this.add(mainPanel, BorderLayout.NORTH);
		this.add(rectArr, BorderLayout.CENTER);

		// Handling User Input
		Visualizer handler = this;
		algorithms.addActionListener(handler);
		visualize.addActionListener(handler);
		shuffle.addActionListener(handler);

		// Handle resizing events
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				adjustComponents();
			}
		});

		// Validate and set the frame visible
		this.validate();
		this.setSize(WIDTH, HEIGHT);
		this.setVisible(true);
	}

	// Generating a new shuffled array
	public ArrayList<Integer> generateArr() {
		ArrayList<Integer> arr = new ArrayList<>();
		Random random = new Random();

		for (int i = 0; i < 40; i++) { // Adjust the loop size as needed
			int height = random.nextInt(400) + 200; // Generate heights between 200 and 599
			arr.add(height);
		}

		Collections.shuffle(arr);
		System.out.println(arr);
		return arr;
	}

	// Event Handling
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == algorithms) {
			noOfComparisons = 0;
			if ("Bubble Sort".equals(algorithms.getSelectedItem())) {
				sortingAlgo = 'b';
				timeComplexity.setText("Time Complexity: O(n^2)");
				spaceComplexity.setText("Space Complexity: O(1)");
				comparisons.setText("No. of Comparisons: " + noOfComparisons);
			} else if ("Heap Sort".equals(algorithms.getSelectedItem())) {
				sortingAlgo = 'h';
				timeComplexity.setText("Time Complexity: O(nlog(n))");
				spaceComplexity.setText("Space Complexity: O(1)");
				comparisons.setText("No. of Comparisons: " + noOfComparisons);
			} else if ("Insertion Sort".equals(algorithms.getSelectedItem())) {
				sortingAlgo = 'i';
				timeComplexity.setText("Time Complexity: O(n^2)");
				spaceComplexity.setText("Space Complexity: O(1)");
				comparisons.setText("No. of Comparisons: " + noOfComparisons);
			} else if ("Merge Sort".equals(algorithms.getSelectedItem())) {
				sortingAlgo = 'm';
				timeComplexity.setText("Time Complexity: O(nlog(n))");
				spaceComplexity.setText("Space Complexity: O(n)");
				comparisons.setText("No. of Comparisons: " + noOfComparisons);
			} else {
				sortingAlgo = 'x';
			}
		} else if (event.getSource() == visualize && !sorted) {
			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() throws Exception {
					if (sortingAlgo == 'b') {
						try {
							bs.sort(heights, rectArr, Visualizer.this);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else if (sortingAlgo == 'h') {
						try {
							hs.sort(heights, rectArr, Visualizer.this);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else if (sortingAlgo == 'i') {
						try {
							is.sort(heights, rectArr, Visualizer.this);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else if (sortingAlgo == 'm') {
						try {
							ms.sort(heights, 0, heights.size() - 1, rectArr, Visualizer.this);
							noOfComparisons = 0;
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					return null;
				}

				@Override
				protected void done() {
					sorted = true;
					// Update any UI components here after sorting completes
				}
			};

			worker.execute();
		} else if (event.getSource() == shuffle) {
			heights = generateArr();
			rectArr.removeAll();
			rectArr.setArr(heights);
			rectArr.revalidate();
			rectArr.paintImmediately(0, 0, getWidth(), getHeight());
			sorted = false;
		}
	}

	// Adjust components based on the new size
	// Adjust components based on the new size
	private void adjustComponents() {
		int newWidth = getWidth();
		int newHeight = getHeight();

		// Update the sizes and positions of components based on the new frame size
		title.setPreferredSize(new Dimension(newWidth, newHeight / 10));
		name.setPreferredSize(new Dimension(newWidth, newHeight / 10));
		timeComplexity.setPreferredSize(new Dimension(newWidth / 2, newHeight / 15));
		spaceComplexity.setPreferredSize(new Dimension(newWidth / 2, newHeight / 15));
		comparisons.setPreferredSize(new Dimension(newWidth / 2, newHeight / 15));

		// Update the size and position of the mainPanel
		mainPanel.setPreferredSize(new Dimension(newWidth, newHeight / 3));

		// Update the size and position of the rectArr panel
		rectArr.setPreferredSize(new Dimension(newWidth, newHeight / 2)); // Remove this line

		// Update the size and position of dropdownPanel
		algorithms.setPreferredSize(new Dimension(newWidth / 4, newHeight / 20));

		// Update the size and position of buttonsPanel
		visualize.setPreferredSize(new Dimension(newWidth / 4, newHeight / 20));
		shuffle.setPreferredSize(new Dimension(newWidth / 4, newHeight / 20));

		// Repaint the frame after adjustments
		revalidate();
		repaint();
	}

	public static void main(String[] args) throws InterruptedException {
		new Visualizer();
	}
}
