package jaspice;

import java.awt.BasicStroke;
import java.awt.Color;

import javax.swing.JList;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

public class RebuildChart {
	MyInternalFrame myFrame;
	private JFreeChart lineChart;
	

	public void rebuildChart(JList<String> theList) /* throws IllegalLogException */ {
		
		int[] v = theList.getSelectedIndices();
		if (v.length == 0) {
			return; // nothing is selected
		}
		StringBuilder b = new StringBuilder();
		for (Integer idx : v) {
			b.append(myFrame.getContent().getVarName(idx)).append(' ');
			System.out.println(b);
		}

		myFrame.setLineChart(ChartFactory.createXYLineChart(myFrame.getContent().getTitle(),
				myFrame.getContent().getVarName(0) + "[" + Tools.units.get(myFrame.getContent().getVarName(0).charAt(0))
						+ "]",
				b.toString() + "[" + Tools.units.get(b.toString().charAt(0)) + "]",
				myFrame.getContent().createDataset(v), PlotOrientation.VERTICAL, true, true, false));

		if (myFrame.isYlog() == true && myFrame.isYlin() == false) {
			myFrame.setPlot(myFrame.getLineChart().getXYPlot());
			try {
				LogarithmicAxis yAxis = new LogarithmicAxis(
						b.toString() + "[" + Tools.units.get(b.toString().charAt(0)) + "]");

				myFrame.getPlot().setRangeAxis(yAxis);

				XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) myFrame.getPlot().getRenderer();
				renderer.setSeriesShapesVisible(0, true);
			} catch (RuntimeException ex) {
				JOptionPane.showMessageDialog(myFrame.getInternalFrame(),
						"The negative values or values close to zerio present in thist graph on the y axis will be shown in a linear mode");
				LogarithmicAxis yAxis = new LogarithmicAxis(
						b.toString() + "[" + Tools.units.get(b.toString().charAt(0)) + "]");

				yAxis.setAllowNegativesFlag(true);

				myFrame.getPlot().setRangeAxis(yAxis);
				XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) myFrame.getPlot().getRenderer();
				renderer.setSeriesShapesVisible(0, true);

				
			}
		}

		if (myFrame.isXlog() == true && myFrame.isXlin() == false) {
			myFrame.setPlot(myFrame.getLineChart().getXYPlot());
			try {

				LogarithmicAxis xAxis = new LogarithmicAxis(myFrame.getContent().getVarName(0) + "["
						+ Tools.units.get(myFrame.getContent().getVarName(0).charAt(0)) + "]");

				myFrame.getPlot().setDomainAxis(xAxis);
				XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) myFrame.getPlot().getRenderer();
				renderer.setSeriesShapesVisible(0, true);
			} catch (RuntimeException ex) {
				JOptionPane.showMessageDialog(myFrame.getInternalFrame(),
						"The negative values or values close to zerio present in thist graph on the x axis will be shown in a linear mode");

				LogarithmicAxis xAxis = new LogarithmicAxis(myFrame.getContent().getVarName(0) + "["
						+ Tools.units.get(myFrame.getContent().getVarName(0).charAt(0)) + "]");

				xAxis.setAllowNegativesFlag(true);

				myFrame.getPlot().setDomainAxis(xAxis);
				XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) myFrame.getPlot().getRenderer();
				renderer.setSeriesShapesVisible(0, true);

				// throw new IllegalLogException();
			}
		}

		ChartPanel chartPanel = new ChartPanel(myFrame.getLineChart(), true, true, true, true, true);
		Crosshair xCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
		Crosshair yCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));

		chartPanel.addChartMouseListener(new MyChartMouseAdapter(myFrame, xCrosshair, yCrosshair));
		CrosshairOverlay crosshairOverlay = new CrosshairOverlay();

		xCrosshair.setLabelVisible(true);
		yCrosshair.setLabelVisible(true);
		crosshairOverlay.addDomainCrosshair(xCrosshair);
		crosshairOverlay.addRangeCrosshair(yCrosshair);
		chartPanel.addOverlay(crosshairOverlay);
		chartPanel.setPreferredSize(new java.awt.Dimension(900, 800));
		myFrame.setChartPanel(chartPanel);
		System.out.println(Thread.currentThread().getName());
	}
	
	public void setMyInternalFrame(MyInternalFrame f) {
		myFrame=f;
	}
	
	
}
