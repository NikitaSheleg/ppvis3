package com.company.Entity;

import java.awt.geom.Point2D;
import java.util.Comparator;

public class XComparator implements Comparator<Point2D> {
    @Override
    public int compare(Point2D p1, Point2D p2) {
        return p1.getX() >= p2.getX() ? 1 : 0;
    }
}
