package jaspice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.jfree.chart.ChartUtilities;

public class MyInternalFrameListener implements InternalFrameListener {

	//private MainFrame mainFrame;
	private MyInternalFrame myFrame;

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		MainFrame.getMainFrame().getFileMenu().remove(MainFrame.getMainFrame().getSaveFileMenuItem());
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		MainFrame.getMainFrame().getViewMenu().remove(MainFrame.getMainFrame().getViewMenuItems().get(myFrame.getInternalFrame().getTitle()));

	}

	private String lastPath;

	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub

		MainFrame.getMainFrame().getFileMenu().add(MainFrame.getMainFrame().getSaveFileMenuItem());

		MainFrame.getMainFrame().getSaveFileMenuItem().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (myFrame.getLineChart() != null) {
					JFileChooser fcSave = new JFileChooser(
							lastPath == null ? FileSystemView.getFileSystemView().getHomeDirectory()
									: new File(lastPath));
					fcSave.setDialogTitle("Specify a file to save - use .png extension");

					fcSave.setFileFilter(new FileNameExtensionFilter(".png", "PNG"));
					int returnValue = fcSave.showSaveDialog(null);
					if (returnValue == JFileChooser.APPROVE_OPTION) {
						File fileToSave = fcSave.getSelectedFile();

						String filePath = fileToSave.getAbsolutePath();

						lastPath = filePath;

						System.out.println("Save as file: " + fileToSave.getAbsolutePath());

						try {

							ChartUtilities.saveChartAsPNG(fileToSave, myFrame.getChartPanel().getChart(), 400, 400);

						} catch (IOException e1) {

							// TODO Auto-generated catch block

							System.out.println("lipasica");

						}
					}

				} else {
					JOptionPane.showMessageDialog(null, "Create a chart in the chosen frame before saving");
				}

			}
		});
	}

	

	public void setFrame(MyInternalFrame f) {
		myFrame = f;
	}

}
