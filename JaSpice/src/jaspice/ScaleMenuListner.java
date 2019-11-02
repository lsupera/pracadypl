package jaspice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;

public class ScaleMenuListner implements ActionListener {
	MyInternalFrame myFrame;
	MyInternalFrameView myInternalFrameView;

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		
		myInternalFrameView.setxLog(false);
		myInternalFrameView.setxLin(true);
		myInternalFrameView.setyLog(false);
		myInternalFrameView.setyLin(true);
		
		switch (((JCheckBoxMenuItem) e.getSource()).getText()) {
		case "X axis Logarithmic": {
			if (((JCheckBoxMenuItem) e.getSource()).getState() == true) {

				myInternalFrameView.setxLog(true);
				myInternalFrameView.setxLin(false);
				myInternalFrameView.setxLinearCheckBoxMenuItem(false);
				myFrame.getBuildChart().buildChart(myFrame.getjListY());

			} else {
				myInternalFrameView.setxLog(false);
				myInternalFrameView.setxLin(true);
				myInternalFrameView.setxLinearCheckBoxMenuItem(true);
				myFrame.getBuildChart().buildChart(myFrame.getjListY());

			}
			break;

		}
		case "X axis Linear": {
			if (((JCheckBoxMenuItem) e.getSource()).getState() == true) {

				myInternalFrameView.setxLog(false);
				myInternalFrameView.setxLin(true);
				myInternalFrameView.setxLogCheckBoxMenuItem(false);
				myFrame.getBuildChart().buildChart(myFrame.getjListY());

			} else {
				myInternalFrameView.setxLog(true);
				myInternalFrameView.setxLin(false);
				myInternalFrameView.setxLogCheckBoxMenuItem(true);
				myFrame.getBuildChart().buildChart(myFrame.getjListY());

			}
			break;
		}
		case "Y axis Logarithmic": {
			if (((JCheckBoxMenuItem) e.getSource()).getState() == true) {

				myInternalFrameView.setyLog(true);
				myInternalFrameView.setyLin(false);
				myInternalFrameView.setyLinearCheckBoxMenuItem(false);
				myFrame.getBuildChart().buildChart(myFrame.getjListY());

			} else {
				myInternalFrameView.setyLog(false);
				myInternalFrameView.setyLin(true);
				myInternalFrameView.setyLinearCheckBoxMenuItem(true);
				myFrame.getBuildChart().buildChart(myFrame.getjListY());

			}
			break;
		}
		case "Y axis Linear": {
			if (((JCheckBoxMenuItem) e.getSource()).getState() == true) {

				myInternalFrameView.setyLog(false);
				myInternalFrameView.setyLin(true);
				myInternalFrameView.setyLogarithmicCheckBoxMenuItem(false);
				myFrame.getBuildChart().buildChart(myFrame.getjListY());
			}
			else {
				myInternalFrameView.setyLog(true);
				myInternalFrameView.setyLin(false);
				myInternalFrameView.setyLogarithmicCheckBoxMenuItem(true);
				myFrame.getBuildChart().buildChart(myFrame.getjListY());
			}
			break;

		}

		}
	}

	public void setFrame(MyInternalFrame f) {
		myFrame = f;
	}
	public void setMyFrameView (MyInternalFrameView f) {
		 myInternalFrameView = f;
	}
	
}
