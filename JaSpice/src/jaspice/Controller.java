package jaspice;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.RectangleEdge;

public class Controller {

    private Tools tools;

    private MainFrame mainFrame;
    public String filePath;

    public Controller() {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {

                    mainFrame = new MainFrame(new OpenFileMenuItemListener());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public class OpenFileMenuItemListener implements ActionListener {

        private String lastPath;

        public void actionPerformed(ActionEvent e) {

            // SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            // @Override
            // protected Boolean doInBackground() throws Exception {
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

            int returnValue = jfc.showOpenDialog(mainFrame);
            // int returnValue = jfc.showSaveDialog(null);
            Thread thread = new Thread(() -> {
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    File selectedFile = jfc.getSelectedFile();
                    filePath = selectedFile.getAbsolutePath();
                    lastPath = filePath;

                    try {

                        RawFileContent content = Tools.rawFileReader(filePath);

                        ListSelection listSelection = new ListSelection();

                        mainFrame.addNewInternalFrame(content, listSelection, filePath);
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } finally {
                        mainFrame.setCursor(Cursor.getDefaultCursor());

                    }
                }
            });
            thread.start();

        }

    }

    public static void main(String[] args) {
        new Controller();
    }
}
