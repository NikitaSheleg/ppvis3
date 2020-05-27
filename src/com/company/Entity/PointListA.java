package com.company.Entity;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class PointListA {
    private List<Point2D> pointList;

    public PointListA() {
        pointList = new ArrayList<>();
        pointList.add(new Point2D.Double(0, 0));
    }

    public void addPoint(Point2D point) {
        pointList.add(point);
    }

    public List<Point2D> getPointList() {
        return pointList;
    }

    public void clear() {
        pointList.clear();
    }
}
