package jaspice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;

public class ScaleMenuListner implements ActionListener {
	MyInternalFrame myFrame;

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		myFrame.setxLog(false);
		myFrame.setxLin(true);
		myFrame.setyLog(false);
		myFrame.setyLin(true);
		
		switch (((JCheckBoxMenuItem) e.getSource()).getText()) {
		case "X axis Logarithmic": {
			if (((JCheckBoxMenuItem) e.getSource()).getState() == true) {

				myFrame.setxLog(true);
				myFrame.setxLin(false);
				myFrame.setxLinearCheckBoxMenuItem(false);
				myFrame.getRebuildChart().rebuildChart(myFrame.getjListY());

			} else {
				myFrame.setxLog(false);
				myFrame.setxLin(true);
				myFrame.setxLinearCheckBoxMenuItem(true);
				myFrame.getRebuildChart().rebuildChart(myFrame.getjListY());

			}
			break;

		}
		case "X axis Linear": {
			if (((JCheckBoxMenuItem) e.getSource()).getState() == true) {

				myFrame.setxLog(false);
				myFrame.setxLin(true);
				myFrame.setxLogCheckBoxMenuItem(false);
				myFrame.getRebuildChart().rebuildChart(myFrame.getjListY());

			} else {
				myFrame.setxLog(true);
				myFrame.setxLin(false);
				myFrame.setxLogCheckBoxMenuItem(true);
				myFrame.getRebuildChart().rebuildChart(myFrame.getjListY());

			}
			break;
		}
		case "Y axis Logarithmic": {
			if (((JCheckBoxMenuItem) e.getSource()).getState() == true) {

				myFrame.setyLog(true);
				myFrame.setyLin(false);
				myFrame.setyLinearCheckBoxMenuItem(false);
				myFrame.getRebuildChart().rebuildChart(myFrame.getjListY());

			} else {
				myFrame.setyLog(false);
				myFrame.setyLin(true);
				myFrame.setyLinearCheckBoxMenuItem(true);
				myFrame.getRebuildChart().rebuildChart(myFrame.getjListY());

			}
			break;
		}
		case "Y axis Linear": {
			if (((JCheckBoxMenuItem) e.getSource()).getState() == true) {

				myFrame.setyLog(false);
				myFrame.setyLin(true);
				myFrame.setyLogarithmicCheckBoxMenuItem(false);
				myFrame.getRebuildChart().rebuildChart(myFrame.getjListY());
			}
			else {
				myFrame.setyLog(true);
				myFrame.setyLin(false);
				myFrame.setyLogarithmicCheckBoxMenuItem(true);
				myFrame.getRebuildChart().rebuildChart(myFrame.getjListY());
			}
			break;

		}

		}
	}

	public void setFrame(MyInternalFrame f) {
		myFrame = f;
	}
}
