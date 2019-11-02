package jaspice;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

public class BuildChart {
	
	private MyInternalFrameView internalFrame;
	
	public BuildChart(MyInternalFrameView internalFrame, RawFileContent content) {
		super();
		this.internalFrame = internalFrame;
		this.content = content;
	}


	private RawFileContent content;
	private MyInternalFrame myFrame;
	private ChartPanel chartPanel;
	private JFreeChart lineChart;
	private XYPlot plot;
	
	

	public void buildChart(JList<String> theList) /* throws IllegalLogException */ {

		int[] v = theList.getSelectedIndices();
		if (v.length == 0) {
			return; // nothing is selected
		}
		StringBuilder b = new StringBuilder();
		for (Integer idx : v) {
			b.append(content.getVarName(idx)).append(' ');
			System.out.println(b);
		}

		lineChart=(ChartFactory.createXYLineChart(content.getTitle(),
				myFrame.getContent().getVarName(0) + "[" + Tools.units.get(content.getVarName(0).charAt(0))
						+ "]",
				b.toString() + "[" + Tools.units.get(b.toString().charAt(0)) + "]",
				content.createDataset(v), PlotOrientation.VERTICAL, true, true, false));

		if (internalFrame.isYlog() == true && internalFrame.isYlin() == false) {
			plot=lineChart.getXYPlot();
			try {
				LogarithmicAxis yAxis = new LogarithmicAxis(
						b.toString() + "[" + Tools.units.get(b.toString().charAt(0)) + "]");

				plot.setRangeAxis(yAxis);

				XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
				renderer.setSeriesShapesVisible(0, true);
			} catch (RuntimeException ex) {
				JOptionPane.showMessageDialog(internalFrame,
						"The negative values or values close to zerio present in thist graph on the y axis will be shown in a linear mode");
				LogarithmicAxis yAxis = new LogarithmicAxis(
						b.toString() + "[" + Tools.units.get(b.toString().charAt(0)) + "]");

				yAxis.setAllowNegativesFlag(true);

				plot.setRangeAxis(yAxis);
				XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
				renderer.setSeriesShapesVisible(0, true);

			}
		}

		if (internalFrame.isXlog() == true && internalFrame.isXlin() == false) {
			plot=lineChart.getXYPlot();
			try {

				LogarithmicAxis xAxis = new LogarithmicAxis(content.getVarName(0) + "["
						+ Tools.units.get(content.getVarName(0).charAt(0)) + "]");

				plot.setDomainAxis(xAxis);
				XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
				renderer.setSeriesShapesVisible(0, true);
			} catch (RuntimeException ex) {
				JOptionPane.showMessageDialog(internalFrame,
						"The negative values or values close to zerio present in thist graph on the x axis will be shown in a linear mode");

				LogarithmicAxis xAxis = new LogarithmicAxis(content.getVarName(0) + "["
						+ Tools.units.get(content.getVarName(0).charAt(0)) + "]");

				xAxis.setAllowNegativesFlag(true);

				plot.setDomainAxis(xAxis);
				XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
				renderer.setSeriesShapesVisible(0, true);

				// throw new IllegalLogException();
			}
		}

		
		chartPanel= new ChartPanel(lineChart, true, true, true, true, true);
						
		Crosshair xCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
		Crosshair yCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));

		chartPanel.addChartMouseListener(new MyChartMouseAdapter(internalFrame, xCrosshair, yCrosshair));
		CrosshairOverlay crosshairOverlay = new CrosshairOverlay();

		xCrosshair.setLabelVisible(true);
		yCrosshair.setLabelVisible(true);
		crosshairOverlay.addDomainCrosshair(xCrosshair);
		crosshairOverlay.addRangeCrosshair(yCrosshair);
		chartPanel.addOverlay(crosshairOverlay);
		chartPanel.setPreferredSize(new java.awt.Dimension(900, 800));
		
	
		internalFrame.filePane.setVisible(false);
		if (internalFrame.chartPanel != null) {
			internalFrame.filePane.remove(internalFrame.chartPanel);
		}
		internalFrame.chartPanel = chartPanel;
		internalFrame.filePane.add(internalFrame.chartPanel, BorderLayout.CENTER);
		internalFrame.filePane.paint(internalFrame.getGraphics());
		internalFrame.filePane.setVisible(true);
		internalFrame.repaint();
		
		//myFrame.setChartPanel(chartPanel);
		
	}
	

	public void setMyInternalFrame(MyInternalFrame f) {
		myFrame = f;
	}

}
