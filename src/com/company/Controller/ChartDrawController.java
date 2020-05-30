package com.company.Controller;


import com.company.Entity.PointListA;
import com.company.Entity.PointListB;
import com.company.View.ChartOptionsView;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.List;

public class ChartDrawController {
    private PointListA pointListA;
    private PointListB pointListB;
    private ChartOptionsView chartOptionsView;

    public ChartDrawController(JSplitPane splitPaneTableGraph) {
        pointListA = new PointListA();
        pointListB = new PointListB();
        chartOptionsView = new ChartOptionsView(this);
        splitPaneTableGraph.setRightComponent(chartOptionsView);
    }

    public void addPointToList(Point2D point, String graphName) {
        if (graphName.equals("A")) pointListA.addPoint(point);
        else pointListB.addPoint(point);
        getChartOptionsView().getChartDrawView().repaint();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clearPointList(String graphName) {
        if (graphName.equals("A")) pointListA.clear();
        else pointListB.clear();

    }

    public List<Point2D> getPointList(String graphName) {
        if (graphName.equals("A")) return pointListA.getPointList();
        else return pointListB.getPointList();
    }

    public ChartOptionsView getChartOptionsView() {
        return chartOptionsView;
    }
}
