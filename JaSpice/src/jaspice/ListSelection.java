/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaspice;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class  ListSelection  implements ListSelectionListener {

    private MyInternalFrame myFrame;

    @Override
    public void valueChanged(ListSelectionEvent e) {

        if (!e.getValueIsAdjusting()) {

            JList<String> theList = (JList) e.getSource();
            // Solely for diagnostics
            System.out.println(String.valueOf(theList.getSelectedIndex()));
            //try {
                myFrame.rebuildChart.rebuildChart(theList);
            //} catch (IllegalLogException ex) {
                
            	
            	
            	System.out.println("CO TERAZ?");
            //}
        }
    }

     void setFrame(MyInternalFrame f) {
        myFrame = f;
    }
}
