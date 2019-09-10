package jaspice;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.jfree.chart.ChartPanel;

public class MainFrame extends JFrame /* implements ListSelectionListener */ {

	private ArrayList<MyInternalFrame> internalFrames = new ArrayList<>();
	private ArrayList<JMenuItem> viewMenuItems = new ArrayList<>();

	private JDesktopPane contentPane;
	private JDesktopPane filePane;

	public JTextField textField;
	private JMenuBar jMenuBar = new JMenuBar();
	private JMenu subMenuCloseFile = new JMenu("Close");
	private JMenu viewMenu = new JMenu("View");

	private String filePath;
	private ChartPanel p;

	public MainFrame(ActionListener openFileMenuItemListener) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(10000, 8000);
		setBounds(100, 100, 450, 300);

		contentPane = new JDesktopPane();

		this.getContentPane().add(contentPane, BorderLayout.CENTER);

		setJMenuBar(jMenuBar);

		JMenu fileMenu = new JMenu("File");

		jMenuBar.add(fileMenu);

		jMenuBar.add(viewMenu);

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

			}

			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				// TODO Auto-generated method stub
				viewMenu.remove(ni);
				System.out.println("kot");
			}

			@Override
			public void internalFrameActivated(InternalFrameEvent e) {
				// TODO Auto-generated method stub

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
