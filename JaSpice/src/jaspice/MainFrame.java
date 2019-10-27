package jaspice;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import org.jfree.chart.ChartPanel;

public class MainFrame extends JFrame {

	private ArrayList<MyInternalFrame> internalFrames = new ArrayList<>();
	private Map<String, JMenuItem> viewMenuItems = new HashMap<>();

	private JDesktopPane contentPane;

	public JTextField textField;
	private JMenuBar jMenuBar = new JMenuBar();

	private JMenuItem saveFileMenuItem = new JMenuItem(
			"Save the chart for the chosen frame as *.png file (right click on the chart for pdf/jpeg files)");
	private JMenu viewMenu = new JMenu("View");
	private JMenu fileMenu=new JMenu("File");
	private JMenu helpMenu = new JMenu("Help");
	private JMenuItem saveFileMenuitem;
	private JMenuItem instructionsHelpMenuItem;
	private String filePath;
	private ChartPanel p;

	
	
	private static MainFrame mainFrame=new MainFrame(new OpenFileMenuItemListener());
	
	private  MainFrame(OpenFileMenuItemListener openFileMenuItemListener) {

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(10000, 8000);
		setBounds(100, 100, 450, 300);

		contentPane = new JDesktopPane();

		this.setTitle("PLOTspice. L.Supera - bachelor's thesis.");
		this.setAlwaysOnTop(false);
		this.getContentPane().add(contentPane, BorderLayout.CENTER);

		setJMenuBar(jMenuBar);

		jMenuBar.add(getFileMenu());

		jMenuBar.add(getViewMenu());

		jMenuBar.add(helpMenu);
		instructionsHelpMenuItem = new JMenuItem("Instructions");
		helpMenu.add(instructionsHelpMenuItem);

		HelpMenuListener myHelpMenuListener = new HelpMenuListener();
		myHelpMenuListener.setFrame(this);
		instructionsHelpMenuItem.addActionListener(myHelpMenuListener);

		JMenuItem openFileMenuItem = new JMenuItem("Open file");

		openFileMenuItem.addActionListener(openFileMenuItemListener);

		getFileMenu().add(openFileMenuItem);

		setVisible(true);
	}

	public void addNewInternalFrame(RawFileContent content, ListSelection listSelectionListener, String name,
			MyInternalFrameListener internalFrameListener) {

		int id = getInternalFrames().size();

		MyInternalFrame nf = new MyInternalFrame(content, listSelectionListener, name + " (" + id + ")",
				internalFrameListener);

		JMenuItem viewMenuItem = new JMenuItem(nf.getInternalFrame().getTitle());

		getViewMenuItems().put(nf.getTitle(), viewMenuItem);

		getInternalFrames().add(nf);

		contentPane.add(nf.getInternalFrame(), 100);

		getViewMenu().add(viewMenuItem);

		//internalFrameListener.setFrame(this);

		viewMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				nf.getInternalFrame().moveToFront();
				nf.getInternalFrame().setLocation(200, 200);

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
		return getInternalFrames().get(i);
	}

	public JMenu getFileMenu() {
		return fileMenu;
	}

	public JMenu getViewMenu() {
		return viewMenu;
	}

	public JMenuItem getSaveFileMenuitem() {
		return saveFileMenuitem;
	}

	public JMenuItem getSaveFileMenuItem() {
		return saveFileMenuItem;
	}

	public ArrayList<MyInternalFrame> getInternalFrames() {
		return internalFrames;
	}

	public Map<String, JMenuItem> getViewMenuItems() {
		return viewMenuItems;
	}

	public static MainFrame getMainFrame() {
		return mainFrame;
	}

}
