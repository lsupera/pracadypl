package jaspice;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.concurrent.ExecutionException;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.RectangleEdge;

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

	/*
	 * private XYDataset createDataset(int[] variables) {
	 * 
	 * int np = content.getNoPoints(); XYSeriesCollection dataset = new
	 * XYSeriesCollection(); for (Integer v : variables) { XYSeries s = new
	 * XYSeries(content.getVarName(v)); double[][] cdata = content.getSeries(); for
	 * (int i = 0; i < np; i++) { s.add(cdata[0][i], cdata[v][i]); }
	 * 
	 * dataset.addSeries(s); }
	 * 
	 * return dataset; }
	 */

	public class FileButtonListener implements ActionListener {

		private String lastPath;

		public void actionPerformed(ActionEvent e) {

			SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {

				@Override
				protected Boolean doInBackground() throws Exception {
					// System.out.println(Thread.currentThread().getName());
					JFileChooser jfc = new JFileChooser(
							lastPath == null ? FileSystemView.getFileSystemView().getHomeDirectory()
									: new File(lastPath));
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

						try {

							content = Tools.rawFileReader(filePath);
							ListSelection listSelection = new ListSelection();
							mainFrame.addFilePanel(content.getVars(), listSelection, filePath);

						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} finally {
							mainFrame.setCursor(Cursor.getDefaultCursor());

						}

					}

					return null;

				}

			};

			worker.execute();
		}
	}

	public class ListSelection implements ListSelectionListener {

		ChartPanel chartPanel;

		public void valueChanged(ListSelectionEvent e) {

			SwingWorker<Boolean, ChartPanel> workerlist = new SwingWorker<Boolean, ChartPanel>() {

				@Override
				protected Boolean doInBackground() throws Exception {
					// TODO Auto-generated method stub

					if (!e.getValueIsAdjusting()) {
						JList<String> theList = (JList) e.getSource();
						// Solely for diagnostics
						// System.out.println(String.valueOf(theList.getSelectedIndex()));
						int[] v = theList.getSelectedIndices();
						StringBuilder b = new StringBuilder();
						for (Integer idx : v) {
							b.append(content.getVarName(idx)).append(' ');
						}
						lineChart = ChartFactory.createXYLineChart(content.getTitle(), content.getVarName(0),
								b.toString(), content.createDataset(v), PlotOrientation.VERTICAL, true, true, false);

						/*
						 * XYPlot plot=lineChart.getXYPlot(); LogarithmicAxis yAxis=new
						 * LogarithmicAxis("Y"); plot.setRangeAxis(yAxis); XYLineAndShapeRenderer
						 * renderer = (XYLineAndShapeRenderer)plot.getRenderer();
						 * renderer.setSeriesShapesVisible(0, true);
						 */

						ChartPanel chartPanel = new ChartPanel(lineChart);
						mainFrame.setChartPanel(chartPanel);
						Crosshair xCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
						Crosshair yCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));

						chartPanel.addChartMouseListener(new ChartMouseListener() {

							@Override
							public void chartMouseMoved(ChartMouseEvent event) {
								Rectangle2D dataArea = mainFrame.getP().getScreenDataArea();
								JFreeChart chart = event.getChart();
								XYPlot plot = (XYPlot) chart.getPlot();
								ValueAxis xAxis = plot.getDomainAxis();
								double x = xAxis.java2DToValue(event.getTrigger().getX(), dataArea,
										RectangleEdge.BOTTOM);
								double y = DatasetUtilities.findYValue(plot.getDataset(), 0, x);
								xCrosshair.setValue(x);
								yCrosshair.setValue(y);
								// TODO Auto-generated method stub

							}

							@Override
							public void chartMouseClicked(ChartMouseEvent arg0) {
								// TODO Auto-generated method stub

							}
						});
						CrosshairOverlay crosshairOverlay = new CrosshairOverlay();

						xCrosshair.setLabelVisible(true);
						yCrosshair.setLabelVisible(true);
						crosshairOverlay.addDomainCrosshair(xCrosshair);
						crosshairOverlay.addRangeCrosshair(yCrosshair);
						chartPanel.addOverlay(crosshairOverlay);
						chartPanel.setPreferredSize(new java.awt.Dimension(900, 800));

						publish(chartPanel);

					}
					return true;
				}

				protected void process() {
					// Here we receive the values that we publish().
					// They may come grouped in chunks.
					mainFrame.setChartPanel(chartPanel);

				}

			};

			workerlist.execute();

		}

	}

	public static void main(String[] args) throws Exception {

		new Controller();

	}

}
