package jaspice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewMenuItemListener implements ActionListener {
	MyInternalFrame myFrame;
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		myFrame.getInternalFrame().moveToFront();
		myFrame.getInternalFrame().setLocation(200, 200);
	}
public void setMyFrame(MyInternalFrame f){
	myFrame=f;
}
}
