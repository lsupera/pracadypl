package jaspice;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class OpenFileMenuItemListener implements ActionListener {

	private String filePath;
	private String lastPath;
	

	
	
	public void actionPerformed(ActionEvent e) {

		JFileChooser jfc = new JFileChooser(
				lastPath == null ? FileSystemView.getFileSystemView().getHomeDirectory() : new File(lastPath));
		jfc.setFileFilter(new FileNameExtensionFilter(".raw", "RAW"));
		jfc.addChoosableFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.isDirectory() || (f.isFile() && f.getName().endsWith("raw"));
			}

			@Override
			public String getDescription() {
				return "Only Spice RAW BINARY files";
			}
		});

		int returnValue = jfc.showOpenDialog(MainFrame.getMainFrame());

		Thread thread = new Thread(() -> {
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				MainFrame.getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				File selectedFile = jfc.getSelectedFile();
				filePath = selectedFile.getAbsolutePath();
				lastPath = filePath;

				try {

					RawFileContent content = Tools.rawFileReader(filePath);

					MainFrame.getMainFrame().addNewInternalFrame(content, new ListSelection(), filePath,
							new MyInternalFrameListener());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					MainFrame.getMainFrame().setCursor(Cursor.getDefaultCursor());

				}
			}
		});
		thread.start();

	}


 
}

