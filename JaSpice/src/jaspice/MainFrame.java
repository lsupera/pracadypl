package jaspice;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import org.jfree.chart.ChartPanel;

public class MainFrame extends JFrame /* implements ListSelectionListener */ {

	private ArrayList<MyInternalFrame> internalFrames = new ArrayList<>();
	private ArrayList<JMenuItem> subMenuCloseFileItems = new ArrayList<>();

	private JDesktopPane contentPane;
	private JDesktopPane filePane;

	public JTextField textField;
	private JMenuBar jMenuBar = new JMenuBar();
	JMenu subMenuCloseFile = new JMenu("Close");

	private String filePath;
	private ChartPanel p;

	public MainFrame(ActionListener openFileMenuItemListener) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(10000, 8000);
		setBounds(100, 100, 450, 300);

		contentPane = new JDesktopPane();
		// contentPane.setLayout(new FlowLayout());

		this.getContentPane().add(contentPane, BorderLayout.CENTER);
		// JScrollPane scrPane = new JScrollPane(contentPane);
		// setContentPane(scrPane);
		// contentPane.add(filePane, BorderLayout.SOUTH);

		// contentPane.add(addNextChartButton, BorderLayout.EAST);

		setJMenuBar(jMenuBar);

		JMenu fileMenu = new JMenu("File");

		jMenuBar.add(fileMenu);

		JMenuItem openFileMenuItem = new JMenuItem("Open file");

		openFileMenuItem.addActionListener(openFileMenuItemListener);

		fileMenu.add(openFileMenuItem);
		fileMenu.add(subMenuCloseFile);

		setVisible(true);
	}

	public void addNewInternalFrame(RawFileContent content, MyListSelectionListener listSelectionListener,
			String name) {

		int id = internalFrames.size();

		MyInternalFrame nf = new MyInternalFrame(content, listSelectionListener, name + " (" + id + ")");

		internalFrames.add(nf);

		contentPane.add(nf.getInternalFrame());

		JMenuItem ni = new JMenuItem(nf.getInternalFrame().getTitle());
		subMenuCloseFileItems.add(ni);

		subMenuCloseFile.add(ni);
		
		
		
		ni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				JInternalFrame f = internalFrames.get(id).getInternalFrame();
				if (f.isVisible()) {
					f.setVisible(false);
					subMenuCloseFile.remove(subMenuCloseFileItems.get(id));}
				else
					f.setVisible(true);
			}
		});

	}

	public String getFilePath() {
		return filePath;
	}

	public JTextField getNewTextField() {
		return textField;
	}

	public ChartPanel getP() {
		return p;
	}

	public MyInternalFrame getInternalFrame(int i) {
		return internalFrames.get(i);
	}

}
