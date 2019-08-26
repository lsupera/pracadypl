package jaspice;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;

import org.jfree.chart.ChartPanel;

public class MainFrame extends JFrame /* implements ListSelectionListener */ {

	private JPanel contentPane;
	private JPanel filePane;
	private JList jListY;
	private JScrollPane scrollY;
	public JTextField textField;
	private List<Map.Entry<String, String>> vars;
	private ListSelectionListener listSelectionListener;
	private ActionListener fileActionListener;
	private ActionListener addNextChartListener;
	private JPanel chartPanel;

	private JButton fileButton;
	private JButton addNextChartButton;
	private String filePath;
	private ChartPanel p;
	private JFrame f1;

	public MainFrame(ActionListener fileButtonListener) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(10000, 8000);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		//contentPane.setLayout(new FlowLayout());
		
		JScrollPane scrPane = new JScrollPane(contentPane);
		
		setContentPane(scrPane);
		//contentPane.add(filePane, BorderLayout.SOUTH);
		addNextChartButton = new JButton("Add next chart");
		//contentPane.add(addNextChartButton, BorderLayout.EAST);

		fileButton = new JButton("Open file");
		fileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		fileButton.addActionListener(fileButtonListener);

		contentPane.add(getFileButton(), BorderLayout.NORTH);

		setVisible(true);
	}

	public void addFilePanel(List vars, ListSelectionListener listSelectionListener, String string) {
		this.vars = vars;

		
		filePane = new JPanel();

		filePane.setBorder(new EmptyBorder(5, 5, 5, 5));
		filePane.setLayout(new BorderLayout(0, 0));
		setSize(1000, 800);

		if (scrollY != null)
			filePane.remove(scrollY);
		if (chartPanel != null)
			filePane.remove(chartPanel);

		this.chartPanel = new JPanel();

		chartPanel.setSize(800, 600);

		jListY = new JList(vars.toArray());

		jListY.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getjListY().setSize(400, 600);

		scrollY = new JScrollPane(getjListY());
		scrollY.setName("Y axis");

		filePane.add(scrollY, BorderLayout.WEST);

		filePane.add(chartPanel, BorderLayout.CENTER);
		this.listSelectionListener = listSelectionListener;

		
		
		contentPane.add(filePane);

		this.addNextChartListener = addNextChartListener;
		addNextChartButton.addActionListener(addNextChartListener);

		textField = new JTextField(20);

		textField.setText("CURRENT FILE: " + string);

		f1 = (JFrame) SwingUtilities.windowForComponent(filePane);
		f1.setTitle("CURRENT FILE: " + string);

		filePane.add(getNewTextField(), BorderLayout.SOUTH);
		getjListY().addListSelectionListener(listSelectionListener);

		setVisible(true);

	}

	
	public void setChartPanel(ChartPanel p) {
		this.p = p;
		filePane.setVisible(false);
		filePane.remove(chartPanel);
		chartPanel = p;
		filePane.add(chartPanel, BorderLayout.CENTER);
		filePane.paint(getGraphics());
		filePane.setVisible(true);
	}

	public JButton getFileButton() {
		return fileButton;
	}

	public String getFilePath() {
		return filePath;
	}

	public JList getjListY() {
		return jListY;
	}

	public JTextField getNewTextField() {
		return textField;
	}

	public ChartPanel getP() {
		return p;
	}

}
