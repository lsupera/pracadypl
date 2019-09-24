/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaspice;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.jfree.chart.ChartFactory;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

/**
 *
 * @author jstar
 */
public class MyInternalFrame {

    private JInternalFrame internalFrame;

    private JDesktopPane contentPane;
    private JDesktopPane filePane;
    private JList jListY;
    private JScrollPane scrollY;
    public JTextField textField;
    
        private JFreeChart lineChart;

    private RawFileContent content;
    private List<Map.Entry<String, String>> vars;
    private MyListSelectionListener listSelectionListener;

    private JButton fileButton;

    private String filePath;
    private ChartPanel chartPanel;

    private String title;
    private JMenuBar axis;
    private JMenu scale;
    private boolean lin;
    private boolean log;

    private JCheckBoxMenuItem logarithmic;
    private JCheckBoxMenuItem linear;

    public MyInternalFrame(RawFileContent content, MyListSelectionListener listSelectionListener, String name) {

        this.content = content;
        this.vars = content.getVars();

        this.title = name;

        internalFrame = new JInternalFrame(name);

        filePane = new JDesktopPane();

        internalFrame.add(filePane);
        filePane.setBorder(new EmptyBorder(5, 5, 5, 5));
        filePane.setLayout(new BorderLayout(0, 0));

        jListY = new JList(vars.toArray());

        jListY.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        getjListY().setSize(400, 600);

        scrollY = new JScrollPane(getjListY());
        scrollY.setName("Y axis");

        filePane.add(scrollY, BorderLayout.WEST);

        this.listSelectionListener = listSelectionListener;

        this.listSelectionListener.setFrame(this);

        internalFrame.setVisible(true);
        internalFrame.setResizable(true);
        internalFrame.setClosable(true);
        internalFrame.setMaximizable(true);
        internalFrame.setBounds(100, 100, 600, 600);

        // f1 = (JFrame) SwingUtilities.windowForComponent(filePane);
        // f1.setTitle("CURRENT FILE: " + string);
        getjListY().addListSelectionListener(listSelectionListener);

        axis = new JMenuBar();
        internalFrame.setJMenuBar(axis);

        scale = new JMenu("Scale");
        axis.add(scale);
        logarithmic = new JCheckBoxMenuItem("Logarithmic");
        linear = new JCheckBoxMenuItem("Linear");
        scale.add(linear);
        linear.setState(true);

        log = false;
        lin = true;

        linear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (log == true) {
                    logarithmic.setState(false);
                    log = false;
                    lin = true;
                    try {
                        rebuildChart(jListY);
                    } catch (IllegalLogException ex) {
                        throw new RuntimeException("THIS CAN NOT HAPPEN!");
                    }
                }
            }

        });
        scale.add(logarithmic);

        logarithmic.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (log == false) {
                    linear.setState(false);
                    log = true;
                    lin = false;
                    try {
                        rebuildChart(jListY);
                    } catch (IllegalLogException ex) {
                        logarithmic.setState(false);
                        log = false;
                        lin = true;
                        try {
                            rebuildChart(jListY);
                        } catch (IllegalLogException ex2) {
                            throw new RuntimeException("THIS CAN NOT HAPPEN!");
                        }
                    }
                }
            }
        });

        internalFrame.setVisible(true);

    }

    public void rebuildChart(JList<String> theList) throws IllegalLogException {
        MyInternalFrame myFrame = this;
        int[] v = theList.getSelectedIndices();
        if (v.length == 0) {
            return; // nothing is selected
        }
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
                JOptionPane.showMessageDialog(myFrame.getInternalFrame(), "The negative values or values close to zerio present in thist graph will be shown in a linear mode");
                LogarithmicAxis yAxis = new LogarithmicAxis("Y");
                yAxis.setAllowNegativesFlag(true);
                plot.setRangeAxis(yAxis);
                XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
                renderer.setSeriesShapesVisible(0, true);
                
                //throw new IllegalLogException();
            }
        }

        ChartPanel chartPanel = new ChartPanel(lineChart);
        Crosshair xCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        Crosshair yCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));

        chartPanel.addChartMouseListener(new MyChartMouseAdapter(this, xCrosshair, yCrosshair));
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

    public JTextField getNewTextField() {
        return textField;
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

    public boolean isLin() {
        return lin;
    }

    public boolean isLog() {
        return log;
    }
}
