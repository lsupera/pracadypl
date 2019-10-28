/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaspice;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;

/**
 *
 * @author jstar
 */
public class MyInternalFrame {

	private JInternalFrame internalFrame;

	private JDesktopPane filePane;
	private JList jListY;
	private JScrollPane scrollY;

	private JFreeChart lineChart;
	private XYPlot plot;

	private RawFileContent content;
	private List<Map.Entry<String, String>> vars;

	private JButton fileButton;

	private String filePath;
	private ChartPanel chartPanel;

	private String title;
	private JMenuBar axisMenuBar;
	private JMenu scaleMenu;
	private boolean yLin;
	private boolean yLog;

	private JCheckBoxMenuItem yLogarithmicCheckBoxMenuItem;
	private JCheckBoxMenuItem yLinearCheckBoxMenuItem;
	private RebuildChart rebuildChart = new RebuildChart();
	private boolean xLin;
	private boolean xLog;

	private JCheckBoxMenuItem xLogarithmicCheckBoxMenuItem;
	private JCheckBoxMenuItem xLinearCheckBoxMenuItem;

	public MyInternalFrame(RawFileContent content, ListSelection listSelectionListener, String name,
			MyInternalFrameListener internalFrameListener) {

		getRebuildChart().setMyInternalFrame(this);
		this.content = content;
		this.vars = content.getVars();

		this.title = name;
		ScaleMenuListner scaleMenuListener = new ScaleMenuListner();
		scaleMenuListener.setFrame(this);
		internalFrame = new JInternalFrame(name);

		filePane = new JDesktopPane();

		internalFrame.add(filePane);

		internalFrame.addInternalFrameListener(internalFrameListener);

		filePane.setBorder(new EmptyBorder(5, 5, 5, 5));
		filePane.setLayout(new BorderLayout(0, 0));

		jListY = new JList(vars.toArray());

		getjListY().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getjListY().setSize(400, 600);

		scrollY = new JScrollPane(getjListY());
		scrollY.setName("Y axis");

		filePane.add(scrollY, BorderLayout.WEST);

		listSelectionListener.setFrame(this);
		internalFrameListener.setFrame(this);

		internalFrame.setVisible(true);
		internalFrame.setResizable(true);
		internalFrame.setClosable(true);
		internalFrame.setMaximizable(true);
		internalFrame.setBounds(100, 100, 600, 600);

		getjListY().addListSelectionListener(listSelectionListener);

		axisMenuBar = new JMenuBar();
		internalFrame.setJMenuBar(axisMenuBar);

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

		scaleMenu.add(xLogarithmicCheckBoxMenuItem);

		xLogarithmicCheckBoxMenuItem.addActionListener(scaleMenuListener);

		internalFrame.setVisible(true);

	}

	public void setChartPanel(ChartPanel p) {

		filePane.setVisible(false);
		if (chartPanel != null) {
			filePane.remove(chartPanel);
		}
		chartPanel = p;
		filePane.add(chartPanel, BorderLayout.CENTER);
		filePane.paint(internalFrame.getGraphics());
		filePane.setVisible(true);
		internalFrame.repaint();

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

	public ChartPanel getChartPanel() {
		return chartPanel;
	}

	public JInternalFrame getInternalFrame() {
		return internalFrame;
	}

	public String getTitle() {
		return title;
	}

	public RawFileContent getContent() {
		return content;
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

	public XYPlot getPlot() {
		return plot;
	}

	public JFreeChart getLineChart() {
		return lineChart;
	}

	public void setLineChart(JFreeChart lineChart) {
		// TODO Auto-generated method stub
		this.lineChart = lineChart;
	}

	public void setPlot(XYPlot plot) {
		// TODO Auto-generated method stub
		this.plot = plot;
	}

	public RebuildChart getRebuildChart() {
		return rebuildChart;
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
