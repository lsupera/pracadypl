/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaspice;

import javax.swing.JList;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author jstar
 */
public interface MyListSelectionListener extends ListSelectionListener {
    public void setFrame( MyInternalFrame f );
    public void rebuildChart(JList<String> theList) throws IllegalLogException;
}
