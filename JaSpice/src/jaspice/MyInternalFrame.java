/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaspice;

import java.util.List;
import java.util.Map;

import javax.swing.JButton;

import javax.swing.JInternalFrame;
import javax.swing.JList;

import org.jfree.chart.ChartPanel;

/**
 *
 * @author jstar
 */
public class MyInternalFrame {

	private MyInternalFrameView internalFrame;

	private JList jListY;

	private RawFileContent content;
	private List<Map.Entry<String, String>> vars;

	private String filePath;
	private ChartPanel chartPanel;

	private String title;

	private BuildChart buildChart;

	public MyInternalFrame(RawFileContent content, ListSelection listSelectionListener, String name,
			MyInternalFrameListener internalFrameListener) {

		this.content = content;
		this.vars = content.getVars();

		this.title = name;
		ScaleMenuListner scaleMenuListener = new ScaleMenuListner();
		scaleMenuListener.setFrame(this);

		listSelectionListener.setFrame(this);
		internalFrameListener.setFrame(this);

		jListY = new JList(vars.toArray());

		internalFrame = new MyInternalFrameView(name, true, true, true, true, scaleMenuListener, jListY,
				listSelectionListener);
		scaleMenuListener.setMyFrameView(internalFrame);

		buildChart = new BuildChart(internalFrame, content);
		getBuildChart().setMyInternalFrame(this);

	}

	public String getFilePath() {
		return filePath;
	}

	public JList getjListY() {
		return jListY;
	}

	public ChartPanel getChartPanel() {
		return chartPanel;
	}

	public JInternalFrame getInternalFrame() {
		return internalFrame;
	}

	public String getTitle() {
		return title;
	}

	public RawFileContent getContent() {
		return content;
	}

	public BuildChart getBuildChart() {
		return buildChart;
	}

}
