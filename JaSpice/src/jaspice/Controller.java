package jaspice;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Controller {

	private Tools tools;
	private RawFileContent content;
	private MainFrame mainFrame;
	public String filePath;
	private JFreeChart lineChart;

	public Controller() {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					mainFrame = new MainFrame(new FileButtonListener());

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	private XYDataset createDataset(int[] variables) {

		int np = content.getNoPoints();
		XYSeriesCollection dataset = new XYSeriesCollection();
		for (Integer v : variables) {
			XYSeries s = new XYSeries(content.getVarName(v));
			double[][] cdata = content.getSeries();
			for (int i = 0; i < np; i++) {
				s.add(cdata[0][i], cdata[v][i]);
			}

			dataset.addSeries(s);
		}

		return dataset;
	}

	public class FileButtonListener implements ActionListener {

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

			int returnValue = jfc.showOpenDialog(null);
			// int returnValue = jfc.showSaveDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				File selectedFile = jfc.getSelectedFile();
				filePath = selectedFile.getAbsolutePath();
				lastPath = filePath;

				Thread t = new Thread() {
					public void run() {
						try {

							content = Tools.rawFileReader(filePath);
							mainFrame.addFilePanel(content.getVars(), new ListSelection(), filePath,
									new AddNextChartListener());

						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} finally {
							mainFrame.setCursor(Cursor.getDefaultCursor());

						}

					}

				};

				t.start();
			}

		}
	}

	public class ListSelection implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				JList<String> theList = (JList) e.getSource();
				// Solely for diagnostics
				// System.out.println(String.valueOf(theList.getSelectedIndex()));
				int[] v = theList.getSelectedIndices();
				StringBuilder b = new StringBuilder();
				for (Integer idx : v) {
					b.append(content.getVarName(idx)).append(' ');
				}
				lineChart = ChartFactory.createXYLineChart(content.getTitle(), content.getVarName(0), b.toString(),
						createDataset(v), PlotOrientation.VERTICAL, true, true, false);

				/*
				 * XYPlot plot=lineChart.getXYPlot(); LogarithmicAxis yAxis=new
				 * LogarithmicAxis("Y"); plot.setRangeAxis(yAxis); XYLineAndShapeRenderer
				 * renderer = (XYLineAndShapeRenderer)plot.getRenderer();
				 * renderer.setSeriesShapesVisible(0, true);
				 */

				ChartPanel chartPanel = new ChartPanel(lineChart);
				chartPanel.setPreferredSize(new java.awt.Dimension(900, 800));
				mainFrame.setChartPanel(chartPanel);
			}
		}

	}

	public class AddNextChartListener implements ActionListener {

		private MainFrame nextChart;

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			nextChart = new MainFrame(new AddNextChartFileButtonListener());

		}

		public class AddNextChartFileButtonListener implements ActionListener {

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

				int returnValue = jfc.showOpenDialog(null);
				// int returnValue = jfc.showSaveDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					nextChart.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					File selectedFile = jfc.getSelectedFile();
					filePath = selectedFile.getAbsolutePath();
					lastPath = filePath;

					Thread t = new Thread() {
						public void run() {
							try {

								content = Tools.rawFileReader(filePath);
								nextChart.addFilePanel(content.getVars(), new NextChartListSelection(), filePath,
										new AddNextChartListener());

							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} finally {
								nextChart.setCursor(Cursor.getDefaultCursor());

							}

						}

					};

					t.start();
				}

			}
		}
		
		public class NextChartListSelection implements ListSelectionListener {

			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					JList<String> theList = (JList) e.getSource();
					// Solely for diagnostics
					// System.out.println(String.valueOf(theList.getSelectedIndex()));
					int[] v = theList.getSelectedIndices();
					StringBuilder b = new StringBuilder();
					for (Integer idx : v) {
						b.append(content.getVarName(idx)).append(' ');
					}
					lineChart = ChartFactory.createXYLineChart(content.getTitle(), content.getVarName(0), b.toString(),
							createDataset(v), PlotOrientation.VERTICAL, true, true, false);

					/*
					 * XYPlot plot=lineChart.getXYPlot(); LogarithmicAxis yAxis=new
					 * LogarithmicAxis("Y"); plot.setRangeAxis(yAxis); XYLineAndShapeRenderer
					 * renderer = (XYLineAndShapeRenderer)plot.getRenderer();
					 * renderer.setSeriesShapesVisible(0, true);
					 */

					ChartPanel chartPanel = new ChartPanel(lineChart);
					chartPanel.setPreferredSize(new java.awt.Dimension(900, 800));
					nextChart.setChartPanel(chartPanel);
				}
			}

		}

		/*
		 * private String lastPath;
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { // TODO Auto-generated
		 * method stub
		 * 
		 * JFileChooser fileChooser = new JFileChooser( lastPath == null ?
		 * FileSystemView.getFileSystemView().getHomeDirectory() : new File(lastPath));
		 * fileChooser.setDialogTitle("Specify a file to save - use .png extension");
		 * fileChooser.setFileFilter(new FileNameExtensionFilter(".png", "PNG")); int
		 * userSelection = fileChooser.showSaveDialog(null);
		 * 
		 * if (userSelection == JFileChooser.APPROVE_OPTION) { File fileToSave =
		 * fileChooser.getSelectedFile(); filePath = fileToSave.getAbsolutePath();
		 * lastPath = filePath; System.out.println("Save as file: " +
		 * fileToSave.getAbsolutePath());
		 * 
		 * try { ChartUtilities.saveChartAsPNG(fileToSave, lineChart, 400, 400); } catch
		 * (IOException e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
		 * }
		 * 
		 * }
		 */

	}

	public static void main(String[] args) throws Exception {

		new Controller();

	}

}
