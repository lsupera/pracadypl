/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaspice;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionListener;
import org.jfree.chart.ChartPanel;

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
    
    private RawFileContent content;
    private List<Map.Entry<String, String>> vars;
    private MyListSelectionListener listSelectionListener;

    private JButton fileButton;

    private String filePath;
    private ChartPanel chartPanel;

    private String title;

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

        internalFrame.setVisible(true);

    }

    public void setChartPanel(ChartPanel p) {
        System.out.println(Thread.currentThread().getName());
        filePane.setVisible(false);
        if( chartPanel != null )
            filePane.remove(chartPanel);
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
}
