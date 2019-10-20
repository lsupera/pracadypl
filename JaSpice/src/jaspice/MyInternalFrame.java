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
	private JMenuBar axis;
	private JMenu scale;
	private boolean yLin;
	private boolean yLog;

	private JCheckBoxMenuItem yLogarithmic;
	private JCheckBoxMenuItem yLinear;
	RebuildChart rebuildChart = new RebuildChart();
	private boolean xLin;
	private boolean xLog;

	private JCheckBoxMenuItem xLogarithmic;
	private JCheckBoxMenuItem xLinear;

	public MyInternalFrame(RawFileContent content, ListSelection listSelectionListener, String name,
			MyInternalFrameListener internalFrameListener) {

		rebuildChart.setMyInternalFrame(this);
		this.content = content;
		this.vars = content.getVars();

		this.title = name;

		internalFrame = new JInternalFrame(name);

		filePane = new JDesktopPane();

		internalFrame.add(filePane);

		internalFrame.addInternalFrameListener(internalFrameListener);

		filePane.setBorder(new EmptyBorder(5, 5, 5, 5));
		filePane.setLayout(new BorderLayout(0, 0));

		jListY = new JList(vars.toArray());

		jListY.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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

		axis = new JMenuBar();
		internalFrame.setJMenuBar(axis);

		scale = new JMenu("Scale");
		axis.add(scale);
		yLogarithmic = new JCheckBoxMenuItem("Y axis Logarithmic");
		yLinear = new JCheckBoxMenuItem("Y axis Linear");
		scale.add(yLinear);
		yLinear.setState(true);

		yLog = false;
		yLin = true;

		yLinear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (yLog == true) {
					yLogarithmic.setState(false);
					yLog = false;
					yLin = true;
					// try {

					rebuildChart.rebuildChart(jListY);
					/*
					 * } catch (IllegalLogException ex) { /*throw new
					 * RuntimeException("THIS CAN NOT HAPPEN!"); }
					 */
				}
			}

		});
		scale.add(yLogarithmic);

		yLogarithmic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (yLog == false) {
					yLinear.setState(false);
					yLog = true;
					yLin = false;
					// try {

					rebuildChart.rebuildChart(jListY);
					/*
					 * } catch (IllegalLogException ex) { logarithmic.setState(false); log = false;
					 * lin = true; try { rebuildChart(jListY); } catch (IllegalLogException ex2) {
					 * //throw new RuntimeException("THIS CAN NOT HAPPEN!"); } }
					 */
				}
			}
		});

		xLogarithmic = new JCheckBoxMenuItem("X axis Logarithmic");
		xLinear = new JCheckBoxMenuItem("X axis Linear");
		scale.add(xLinear);
		xLinear.setState(true);

		xLog = false;
		xLin = true;

		xLinear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (xLog == true) {
					xLogarithmic.setState(false);
					xLog = false;
					xLin = true;
					// try {

					rebuildChart.rebuildChart(jListY);
					/*
					 * } catch (IllegalLogException ex) { /*throw new
					 * RuntimeException("THIS CAN NOT HAPPEN!"); }
					 */
				}
			}

		});
		scale.add(xLogarithmic);

		xLogarithmic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (xLog == false) {
					xLinear.setState(false);
					xLog = true;
					xLin = false;
					// try {

					rebuildChart.rebuildChart(jListY);
					/*
					 * } catch (IllegalLogException ex) { logarithmic.setState(false); log = false;
					 * lin = true; try { rebuildChart(jListY); } catch (IllegalLogException ex2) {
					 * //throw new RuntimeException("THIS CAN NOT HAPPEN!"); } }
					 */
				}
			}
		});

		internalFrame.setVisible(true);

	}

	public void setChartPanel(ChartPanel p) {
		System.out.println(Thread.currentThread().getName());
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

}
