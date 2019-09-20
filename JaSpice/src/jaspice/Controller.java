package jaspice;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.RectangleEdge;

public class Controller {

    private Tools tools;

    private MainFrame mainFrame;
    public String filePath;
    private JFreeChart lineChart;

    public Controller() {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {

                    mainFrame = new MainFrame(new OpenFileMenuItemListener());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public class OpenFileMenuItemListener implements ActionListener {

        private String lastPath;

        public void actionPerformed(ActionEvent e) {

            // SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            // @Override
            // protected Boolean doInBackground() throws Exception {
            JFileChooser jfc = new JFileChooser(
                    lastPath == null ? FileSystemView.getFileSystemView().getHomeDirectory() : new File(lastPath));
            jfc.setFileFilter(new FileNameExtensionFilter(".raw", "RAW"));
            jfc.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || (f.isFile() && f.getName().endsWith("raw"));
                }

                @Override
                public String getDescription() {
                    return "Only Spice RAW BINARY files";
                }
            });

            int returnValue = jfc.showOpenDialog(mainFrame);
            // int returnValue = jfc.showSaveDialog(null);
            Thread thread = new Thread(() -> {
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    File selectedFile = jfc.getSelectedFile();
                    filePath = selectedFile.getAbsolutePath();
                    lastPath = filePath;

                    try {

                        RawFileContent content = Tools.rawFileReader(filePath);

                        ListSelection listSelection = new ListSelection();

                        mainFrame.addNewInternalFrame(content, listSelection, filePath);
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } finally {
                        mainFrame.setCursor(Cursor.getDefaultCursor());

                    }
                }
            });
            thread.start();

        }

    }

    public class ListSelection implements MyListSelectionListener {

        private MyInternalFrame myFrame;

        @Override
        public void valueChanged(ListSelectionEvent e) {

            if (!e.getValueIsAdjusting()) {

                JList<String> theList = (JList) e.getSource();
                // Solely for diagnostics
                System.out.println(String.valueOf(theList.getSelectedIndex()));
                try {
                    rebuildChart(theList);
                } catch (IllegalLogException ex) {
                    // A CO TERAZ?
                }
            }
        }

        @Override
        public void rebuildChart(JList<String> theList) throws IllegalLogException {
            int[] v = theList.getSelectedIndices();
            if( v.length == 0 )
                return; // nothing is selected
            StringBuilder b = new StringBuilder();
            for (Integer idx : v) {
                b.append(myFrame.getContent().getVarName(idx)).append(' ');
                System.out.println(b);
            }

            lineChart = ChartFactory.createXYLineChart(myFrame.getContent().getTitle(), myFrame.getContent().getVarName(0), b.toString(),
                    myFrame.getContent().createDataset(v), PlotOrientation.VERTICAL, true, true, false);

            if (myFrame.isLog() == true && myFrame.isLin() == false) {
                XYPlot plot = lineChart.getXYPlot();
                try {
                    LogarithmicAxis yAxis = new LogarithmicAxis("Y");
                    plot.setRangeAxis(yAxis);
                    XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
                    renderer.setSeriesShapesVisible(0, true);
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(myFrame.getInternalFrame(), "Log scale allowed for positive range only.");
                    throw new IllegalLogException();
                }
            }

            ChartPanel chartPanel = new ChartPanel(lineChart);
            Crosshair xCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
            Crosshair yCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));

            chartPanel.addChartMouseListener(new MyChartMouseAdapter(xCrosshair, yCrosshair));
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

        public void setFrame(MyInternalFrame f) {
            myFrame = f;
        }

        private class MyChartMouseAdapter implements ChartMouseListener {

            Crosshair xCrosshair;
            Crosshair yCrosshair;

            public MyChartMouseAdapter(Crosshair xCrosshair, Crosshair yCrosshair) {
                super();
                this.xCrosshair = xCrosshair;
                this.yCrosshair = yCrosshair;
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent event) {
                Rectangle2D dataArea = myFrame.getChartPanel().getScreenDataArea();
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

    }

    public static void main(String[] args) {
        new Controller();
    }
}
