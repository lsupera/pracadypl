/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaspice;

import java.awt.geom.Rectangle2D;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.RectangleEdge;

public class MyChartMouseAdapter implements ChartMouseListener {

    Crosshair xCrosshair;
    Crosshair yCrosshair;
    private MyInternalFrameView myFrame;

    public MyChartMouseAdapter(MyInternalFrameView myFrame, Crosshair xCrosshair, Crosshair yCrosshair) {
        super();
        this.myFrame = myFrame;
        this.xCrosshair = xCrosshair;
        this.yCrosshair = yCrosshair;
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent event) {
        Rectangle2D dataArea = myFrame.chartPanel.getScreenDataArea();
        JFreeChart chart = event.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis xAxis = plot.getDomainAxis();
        double x = xAxis.java2DToValue(event.getTrigger().getX(), dataArea, RectangleEdge.BOTTOM);
        double y = DatasetUtilities.findYValue(plot.getDataset(), 0, x);
        xCrosshair.setValue(x);
        yCrosshair.setValue(y);
        // TODO Auto-generated method stub

    }

    @Override
    public void chartMouseClicked(ChartMouseEvent arg0) {
        // TODO Auto-generated method stub

    }
}
