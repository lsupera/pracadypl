package jaspice;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;

import org.jfree.chart.ChartPanel;

public class MainFrame extends JFrame /* implements ListSelectionListener */ {

	private ArrayList<MyInternalFrame> internalFrames = new ArrayList<>();

	private JDesktopPane contentPane;
	private JDesktopPane filePane;


	public JTextField textField;



	private JButton fileButton;

	private String filePath;
	private ChartPanel p;

	public MainFrame(ActionListener fileButtonListener) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(10000, 8000);
		setBounds(100, 100, 450, 300);

		contentPane = new JDesktopPane();
		// contentPane.setLayout(new FlowLayout());

		this.getContentPane().add(contentPane, BorderLayout.CENTER);
		// JScrollPane scrPane = new JScrollPane(contentPane);
		// setContentPane(scrPane);
		// contentPane.add(filePane, BorderLayout.SOUTH);

		// contentPane.add(addNextChartButton, BorderLayout.EAST);

		fileButton = new JButton("Open file");
		fileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		fileButton.addActionListener(fileButtonListener);
		fileButton.setBounds(149, 76, 89, 23);
                
                
                JTextField dd = new JTextField("0");
                dd.setBounds(149, 106, 89, 23);
                dd.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int n = Integer.parseInt(dd.getText()) - 1;
                        JInternalFrame f = internalFrames.get(n).getInternalFrame();
                        if( f.isVisible() )
                            f.setVisible(false);
                        else
                            f.setVisible(true);
                    }
                });

		contentPane.add(getFileButton(), BorderLayout.NORTH);
                contentPane.add(dd, BorderLayout.SOUTH);
		setVisible(true);
	}

	public void addNewInternalFrame(RawFileContent content, MyListSelectionListener listSelectionListener, String name) {
            
                int id = internalFrames.size() + 1;
            
                MyInternalFrame nf = new MyInternalFrame( content, listSelectionListener, name + " (" + id + ")");

		internalFrames.add( nf );
                
                
                contentPane.add(nf.getInternalFrame());

	}


	public JButton getFileButton() {
		return fileButton;
	}

	public String getFilePath() {
		return filePath;
	}


	public JTextField getNewTextField() {
		return textField;
	}

	public ChartPanel getP() {
		return p;
	}

	public MyInternalFrame getInternalFrame( int i ) {
		return internalFrames.get(i);
	}
        

}
