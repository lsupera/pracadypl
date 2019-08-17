package jaspice;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;

import org.jfree.chart.ChartPanel;

public class MainFrame extends JFrame /* implements ListSelectionListener */ {

    private JPanel contentPane;
    private JList jListY;
    private JScrollPane scrollY;
    public JTextField textField;
    private List<Map.Entry<String, String>> vars;
    private ListSelectionListener listSelectionListener;
    private ActionListener fileActionListener;
    private ActionListener addNextChartListener;
    private JPanel chartPanel;
    private JButton fileButton;
    private JButton addNextChart;
    private String filePath;
    private ChartPanel p;
    public MainFrame(ActionListener fileButtonListener) {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

          
        fileButton = new JButton("Open file");
        /*fileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));*/
        fileButton.addActionListener(fileButtonListener);
        
        
        contentPane.add(getFileButton(), BorderLayout.NORTH);


        setVisible(true);
    }

    public void addFilePanel(List vars, ListSelectionListener listSelectionListener, String string, ActionListener addNextChartListener) {
        this.vars = vars;
        
        
        setSize(1000, 800);
        
        if( scrollY != null ) contentPane.remove(scrollY);
        if( chartPanel != null ) contentPane.remove(chartPanel);
        

        this.chartPanel = new JPanel();
        chartPanel.setSize(800, 600);

        jListY = new JList(vars.toArray());
        jListY.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        getjListY().setSize(400, 600);

        scrollY = new JScrollPane(getjListY());

        contentPane.add(scrollY, BorderLayout.WEST);

        contentPane.add(chartPanel, BorderLayout.CENTER);
        this.listSelectionListener = listSelectionListener;

        
        addNextChart=new JButton("Add next chart");
        contentPane.add(addNextChart,BorderLayout.EAST);
        
        this.addNextChartListener=addNextChartListener;
        addNextChart.addActionListener(addNextChartListener);
        
        textField=new JTextField(20);
                
        textField.setText("CURRENT FILE: "+string);
        
        contentPane.add(getNewTextField(), BorderLayout.SOUTH);
        getjListY().addListSelectionListener(listSelectionListener);

        setVisible(true);

    }

    

    public void setChartPanel(ChartPanel p) {
        this.p=p;
    	contentPane.setVisible(false);
        contentPane.remove(chartPanel);
        chartPanel=p;
        contentPane.add(chartPanel, BorderLayout.CENTER);
        contentPane.paint(getGraphics());
        contentPane.setVisible(true);
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

	public ChartPanel getP() {
		return p;
	}

}
