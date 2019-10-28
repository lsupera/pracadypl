package jaspice;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class Controller {

	
	public String filePath;

	public Controller() {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
										
					MainFrame.getMainFrame();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	
	public static void main(String[] args) {
		new Controller();
	}
}
