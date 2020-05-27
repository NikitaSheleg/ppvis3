package com.company.Entity;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class PointListB {
    private List<Point2D> pointList;

    public PointListB() {
        pointList = new ArrayList<>();
        pointList.add(new Point2D.Double(0, 0));
    }

    public void addPoint(Point2D point) {
        pointList.add(point);
    }

    public void clear() {
        pointList.clear();
    }

    public List<Point2D> getPointList() {
        return pointList;
    }

}
