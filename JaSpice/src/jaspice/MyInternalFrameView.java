package jaspice;

import java.awt.BorderLayout;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartPanel;


public class MyInternalFrameView extends JInternalFrame {

	private JCheckBoxMenuItem yLogarithmicCheckBoxMenuItem;
	private JCheckBoxMenuItem yLinearCheckBoxMenuItem;
	private JCheckBoxMenuItem xLogarithmicCheckBoxMenuItem;
	private JCheckBoxMenuItem xLinearCheckBoxMenuItem;
	private JMenuBar axisMenuBar;
	private JMenu scaleMenu;
	public JDesktopPane filePane;
	public ChartPanel chartPanel=new ChartPanel(null);
	private JScrollPane scrollY;
	private boolean yLin;
	private boolean yLog;
	private boolean xLin;
	private boolean xLog;
	

	public MyInternalFrameView(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable, ScaleMenuListner scaleMenuListener, JList jListY, ListSelection listSelectionListener) {
		super(title, resizable, closable, maximizable, iconifiable);
		filePane = new JDesktopPane();

		add(filePane);
		
		filePane.setBorder(new EmptyBorder(5, 5, 5, 5));
		filePane.setLayout(new BorderLayout(0, 0));
		
		jListY.addListSelectionListener(listSelectionListener);
		jListY.setSize(400, 600);
		scrollY = new JScrollPane(jListY);
		scrollY.setName("Y axis");

		filePane.add(scrollY, BorderLayout.WEST);
		
		axisMenuBar = new JMenuBar();
		setJMenuBar(axisMenuBar);

		scaleMenu = new JMenu("Scale");
		axisMenuBar.add(scaleMenu);

		yLogarithmicCheckBoxMenuItem = new JCheckBoxMenuItem("Y axis Logarithmic");

		yLinearCheckBoxMenuItem = new JCheckBoxMenuItem("Y axis Linear");
		yLinearCheckBoxMenuItem.setState(true);
		scaleMenu.add(yLinearCheckBoxMenuItem);

		yLinearCheckBoxMenuItem.addActionListener(scaleMenuListener);

		scaleMenu.add(yLogarithmicCheckBoxMenuItem);

		yLogarithmicCheckBoxMenuItem.addActionListener(scaleMenuListener);

		xLogarithmicCheckBoxMenuItem = new JCheckBoxMenuItem("X axis Logarithmic");
		xLinearCheckBoxMenuItem = new JCheckBoxMenuItem("X axis Linear");
		xLinearCheckBoxMenuItem.setState(true);
		scaleMenu.add(xLinearCheckBoxMenuItem);

		xLinearCheckBoxMenuItem.addActionListener(scaleMenuListener);
		xLogarithmicCheckBoxMenuItem.addActionListener(scaleMenuListener);

		scaleMenu.add(xLogarithmicCheckBoxMenuItem);
		setBounds(100, 100, 600, 600);
		setVisible(true);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public void setyLin(boolean yLin) {
		this.yLin = yLin;
	}

	public void setyLog(boolean yLog) {
		this.yLog = yLog;
	}

	public void setxLin(boolean xLin) {
		this.xLin = xLin;
	}

	public void setxLog(boolean xLog) {
		this.xLog = xLog;
	}
	
	public boolean isXlin() {
		return xLin;
	}

	public boolean isXlog() {
		return xLog;
	}

	public boolean isYlin() {
		return yLin;
	}

	public boolean isYlog() {
		return yLog;
	}
	
	
	public void setxLinearCheckBoxMenuItem(boolean b) {
		xLinearCheckBoxMenuItem.setState(b);
	}

	public void setxLogCheckBoxMenuItem(boolean b) {
		// TODO Auto-generated method stub
		xLogarithmicCheckBoxMenuItem.setState(b);
	}

	public void setyLogarithmicCheckBoxMenuItem(boolean b) {
		yLogarithmicCheckBoxMenuItem.setState(b);
	}

	public void setyLinearCheckBoxMenuItem(boolean b) {
		yLinearCheckBoxMenuItem.setState(b);
	}

}
