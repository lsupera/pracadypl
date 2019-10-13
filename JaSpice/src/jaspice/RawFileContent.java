/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaspice;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author jstar
 */
public class RawFileContent {

	Map<String, String> head;
    private List<Map.Entry<String, String>> vars;
    private double[][] series;
    
    private int noVars;
    private int noPoints;

    public RawFileContent(Map<String, String> head, List<Map.Entry<String, String>> vars, double[][] series) {
        this.head = head;
        this.vars = vars;
        this.series = series;
        noVars = series.length;
        noPoints = series[0].length;
    }

  
    
    public XYDataset createDataset(int[] variables) {

		int np = getNoPoints();
		XYSeriesCollection dataset = new XYSeriesCollection();
		for (Integer v : variables) {
			XYSeries s = new XYSeries(getVarName(v));
			double[][] cdata = getSeries();
			for (int i = 0; i < np; i++) {
				s.add(cdata[0][i], cdata[v][i]);
			}

			dataset.addSeries(s);
		}

		return dataset;
	}
    
    
	@Override
    public String toString() {
        return head.get("title") + " : " + getNoVars() + " series of " + getNoPoints() + " points each";
    }
    
    public String getTitle() {
        return head.get("title");
    }

    /**
     * @return the vars
     */
    public String getVarName(int i) {
        return getVars().get(i).getKey();
    }

    /*public String getVarUnit(int i) {
        return getVars().get(i).getValue();
    }*/

    /**
     * @return the series
     */
    public double[][] getSeries() {
        return series;
    }

    /**
     * @return the noVars
     */
    public int getNoVars() {
        return noVars;
    }

    /**
     * @return the noPoints
     */
    public int getNoPoints() {
        return noPoints;
    }

    public List<Map.Entry<String, String>> getVars() {
        return vars;
    }


}
