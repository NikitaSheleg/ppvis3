package com.company.Entity;

import java.awt.geom.Point2D;
import java.util.Comparator;

public class YComparator implements Comparator<Point2D> {
    @Override
    public int compare(Point2D p1, Point2D p2) {
        return p1.getY() >= p2.getY() ? 1 : 0;
    }
}
