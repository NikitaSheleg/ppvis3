package com.company.View;


import com.company.Controller.ChartDrawController;
import com.company.Entity.XComparator;
import com.company.Entity.YComparator;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChartDrawView extends JPanel {
    private final Color graphColorB = Color.blue;
    private Color graphPointColor = Color.black;
    private Stroke graphStroke = new BasicStroke(3f);
    private double zoom;
    private List<Point2D> pointsA;
    private List<Point2D> pointsB;


    public ChartDrawView(ChartDrawController controller) {
        this.pointsA = controller.getPointList("A");
        this.pointsA.sort(new XComparator());
        this.pointsB = controller.getPointList("B");
        this.pointsB.sort(new XComparator());
        this.zoom = 0.5;
        this.setVisible(true);
    }


    public void setZoom(double zoom) {
        if (zoom < 0.5) {
            this.zoom = 0.5;
        } else this.zoom = Math.min(zoom, 1.5);
    }

    public double getZoom() {
        return zoom;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        {
            double hatchNumber = 0,
                    maxXB = findStep((int) Collections.max(pointsB, new XComparator()).getX()),
                    maxYB = findStep((int) Collections.max(pointsB, new YComparator()).getY()),
                    maxXA = findStep((int) Collections.max(pointsA, new XComparator()).getX()),
                    maxYA = findStep((int) Collections.max(pointsA, new YComparator()).getY()),
                    maxX = Math.max(maxXB, maxXA),
                    maxY = Math.max(maxYB, maxYA);
            int borderGap = 55;
            int graphPointWidth = 10;
            int border_gap = (int) (borderGap * zoom),
                    hatchLength = (int) (graphPointWidth * zoom);
            double xScale = (((double) getWidth() - 2 * border_gap) / maxX),
                    yScale = (((double) getHeight() - 2 * border_gap) / maxY);
            int fontSize;
            int fontSize1 = 10;
            if (zoom < 3) {
                fontSize = (int) (fontSize1 * zoom);
            } else {
                fontSize = fontSize1 * 3;
            }
            List<Point> zoomedPointsListA = new ArrayList<>();
            List<Point> zoomedPointsListB = new ArrayList<>();

            addPoints(border_gap, xScale, yScale, zoomedPointsListA, pointsA);

            addPoints(border_gap, xScale, yScale, zoomedPointsListB, pointsB);

            g2.drawLine(border_gap, getHeight() - border_gap, border_gap, border_gap);
            g2.drawLine(border_gap, getHeight() - border_gap, border_gap, border_gap);
            g2.drawLine(border_gap, getHeight() - border_gap, getWidth() - border_gap, getHeight() - border_gap);


            hatchNumber = maxY / 10;

            int yHatchCnt = 10;
            for (int index = 0; index < yHatchCnt; index++) {
                int x0 = border_gap - hatchLength / 2,
                        x1 = x0 + hatchLength,
                        y0 = getHeight() - (((index + 1) * (getHeight() - border_gap * 2)) / yHatchCnt + border_gap);
                g2.drawLine(x0, y0, x1, y0);
                g2.drawString(String.format("%.4f", hatchNumber * (index + 1)), 0, y0 + fontSize / 2);
            }

            hatchNumber = maxX / 10;

            for (int index = 0; index < yHatchCnt; index++) {
                int x0 = (index + 1) * (getWidth() - border_gap * 2) / yHatchCnt + border_gap,
                        y0 = getHeight() - border_gap + hatchLength / 2,
                        y1 = y0 - hatchLength;
                g2.drawLine(x0, y0, x0, y1);
                g2.drawString(String.format("%.4f", hatchNumber * (index + 1)), x0 - fontSize / 2, y0 + border_gap / 2);
            }
            g2.drawString("0", border_gap, getHeight() - border_gap / 2);
            Stroke oldStroke = g2.getStroke();
            setColor(g2, zoomedPointsListA, oldStroke, Color.LIGHT_GRAY);
            for (Point point : zoomedPointsListA) {
                int x = (point.x - graphPointWidth / 2),
                        y = (point.y - graphPointWidth / 2);
                g2.fillOval(x, y, graphPointWidth, graphPointWidth);
            }
            setColor(g2, zoomedPointsListB, oldStroke, Color.MAGENTA);
            for (Point point : zoomedPointsListB) {
                int x = point.x - graphPointWidth / 2,
                        y = point.y - graphPointWidth / 2;
                g2.fillOval(x, y, graphPointWidth, graphPointWidth);
            }
        }
    }

    private void setColor(Graphics2D g2, List<Point> zoomedPointsListA, Stroke oldStroke, Color graphColorA) {
        g2.setColor(graphColorA);
        g2.setStroke(graphStroke);
        for (int i = 0; i < zoomedPointsListA.size() - 1; i++) {
            Point point = zoomedPointsListA.get(i);
            int x1 = point.x,
                    y1 = point.y;
            point = zoomedPointsListA.get(i + 1);
            int x2 = point.x,
                    y2 = point.y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke);
        g2.setColor(graphPointColor);
    }

    private void addPoints(int border_gap, double xScale, double yScale, List<Point> zoomedPointsListB, List<Point2D> pointsB) {
        for (Point2D point : pointsB) {
            Point newPoint = new Point();
            newPoint.x = (int) (border_gap + point.getX() * xScale);
            newPoint.y = (int) (getHeight() - point.getY() * yScale - border_gap);
            zoomedPointsListB.add(newPoint);
        }
    }

    public int findStep(int number) {
        int step = 10;
        while (step < number) {
            step = step + 10;
        }
        return step;
    }

    @Override
    public Dimension getPreferredSize() {
        int prefW = 1100;
        int prefH = 900;
        return new Dimension((int) (prefW * zoom), (int) (prefH * zoom));
    }
}