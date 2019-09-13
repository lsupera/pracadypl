package jaspice;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;

public class MainFrame extends JFrame /* implements ListSelectionListener */ {

	private ArrayList<MyInternalFrame> internalFrames = new ArrayList<>();
	private ArrayList<JMenuItem> viewMenuItems = new ArrayList<>();

	private JDesktopPane contentPane;
	private JDesktopPane filePane;

	public JTextField textField;
	private JMenuBar jMenuBar = new JMenuBar();
	private JMenu subMenuCloseFile = new JMenu("Close");
	private JMenuItem saveFileMenuItem = new JMenuItem("Save the chart for the active frame as png file (right click on the chart for pdf/jpeg)" );
	private JMenu viewMenu = new JMenu("View");
	private JMenu fileMenu;
	private JMenu helpMenu;
	private JMenuItem saveFileMenuitem;
	private JMenuItem instructionsHelpMenuItem;
	private String filePath;
	private ChartPanel p;
	private JFileChooser fcSave;

	public MainFrame(ActionListener openFileMenuItemListener) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(10000, 8000);
		setBounds(100, 100, 450, 300);

		contentPane = new JDesktopPane();

		this.getContentPane().add(contentPane, BorderLayout.CENTER);

		setJMenuBar(jMenuBar);

		fileMenu = new JMenu("File");

		jMenuBar.add(fileMenu);

		jMenuBar.add(viewMenu);
		
		helpMenu= new JMenu("Help");
		jMenuBar.add(helpMenu);
		instructionsHelpMenuItem=new JMenuItem("Instructions");
		helpMenu.add(instructionsHelpMenuItem);
		
		
		instructionsHelpMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				JOptionPane.showMessageDialog(null, "Use open file to load a file. \n You can open multiple fiels at the same tiem.\n Navigate between files(open frames) using view menu. "
						+ "\n If you wish to save a chart you can use save option (*.png) in file menu \n or right click on the chart (*.png, *.pdf,*.jpeg) \n "
						+ " You cannot save anything if no chart is created.\n"
						+ "You can create multiple charts on the same graph while selecting more than one item from \n"
						+ " the JList in a JInternalFrame and pressing ctrl."
						);
			}
			
			
		});

		JMenuItem openFileMenuItem = new JMenuItem("Open file");

		openFileMenuItem.addActionListener(openFileMenuItemListener);

		fileMenu.add(openFileMenuItem);

		setVisible(true);
	}

	public void addNewInternalFrame(RawFileContent content, MyListSelectionListener listSelectionListener,
			String name) {

		int id = internalFrames.size();

		MyInternalFrame nf = new MyInternalFrame(content, listSelectionListener, name + " (" + id + ")");

		internalFrames.add(nf);

		contentPane.add(nf.getInternalFrame());

		JMenuItem ni = new JMenuItem(nf.getInternalFrame().getTitle());
		viewMenu.add(ni);

		viewMenuItems.add(ni);

		JInternalFrame f = internalFrames.get(id).getInternalFrame();

		f.addInternalFrameListener(new InternalFrameListener() {

			@Override
			public void internalFrameOpened(InternalFrameEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void internalFrameIconified(InternalFrameEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void internalFrameDeiconified(InternalFrameEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void internalFrameDeactivated(InternalFrameEvent e) {
				// TODO Auto-generated method stub
				fileMenu.remove(saveFileMenuItem);
			}

			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				// TODO Auto-generated method stub
				viewMenu.remove(ni);

			}

			private String lastPath;
			@Override
			public void internalFrameActivated(InternalFrameEvent e) {
				// TODO Auto-generated method stub
								
				fileMenu.add(saveFileMenuItem);
				
				saveFileMenuItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						fcSave = new JFileChooser();
						fcSave.setDialogTitle("Specify a file to save - use .png extension");

						fcSave.setFileFilter(new FileNameExtensionFilter(".png", "PNG"));
						int returnValue = fcSave.showSaveDialog(null);
						if (returnValue == JFileChooser.APPROVE_OPTION) {
							File fileToSave = fcSave.getSelectedFile();

							filePath = fileToSave.getAbsolutePath();

							lastPath = filePath;

							System.out.println("Save as file: " + fileToSave.getAbsolutePath());

							try {

								
								
								ChartUtilities.saveChartAsPNG(fileToSave, nf.getChartPanel().getChart(), 400, 400);
								

							} catch (IOException e1) {

								// TODO Auto-generated catch block

								JOptionPane.showMessageDialog(null, "error");

							}
						}

					}
				});
			}
		});

		ni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				f.moveToFront();
				f.setLocation(200, 200);

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
