package jaspice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class helpMenuListener implements ActionListener {
	MainFrame mainFrame;
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 
		JOptionPane.showMessageDialog(mainFrame,
				"Use open file to load a *.raw file. \n You can load multiple files at the same tiem.\n Navigate between files(open frames) using view menu. "
						+ "\n If you wish to save a chart you can use save option (*.png) in file menu (active for a currently selected frame) \n or right click on the chart (*.png, *.pdf,*.jpeg) \n "
						+ " You cannot save anything if no chart is created.\n"
						+ "You can create multiple charts on the same graph while selecting more than one item from \n"
						+ " the JList in a JInternalFrame and pressing ctrl.\n"
						+ " You can switch between linear and logarithmic scale (for x, y or both axes).\n"
						+ " If you choose a log scale in case of negative values a warning will appear ant the respective values will be shown in linear mode.\n"
						+ "Right click also for print, zoom, scale and range options.",
				"PLOTspice instructions", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void setFrame(MainFrame f) {
		mainFrame = f;
	}

}
