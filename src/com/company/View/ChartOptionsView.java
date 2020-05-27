package com.company.View;

import com.company.Controller.ChartDrawController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class ChartOptionsView extends JPanel {
    private ChartDrawView chartDrawView;
    private JSlider slider;

    public ChartOptionsView(ChartDrawController controller) {
        super();
        this.setLayout(new BorderLayout());

        JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
        slider = new JSlider(JSlider.HORIZONTAL, 50, 150, 100);
        slider.setMajorTickSpacing(25);
        slider.setMinorTickSpacing(5);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);

        chartDrawView = new ChartDrawView(controller);
        JLabel scaleTextLabel = new JLabel(chartDrawView.getZoom() * 100 + "%");

        toolBar.setFloatable(false);
        toolBar.setLayout(new FlowLayout());


        JScrollPane scrollPanel = new JScrollPane(chartDrawView);

        scrollPanel.setAutoscrolls(true);
        MouseAdapter mouseAdapter = new MouseAdapter() {
            private Point origin;

            @Override
            public void mousePressed(MouseEvent e) {
                origin = e.getPoint();
            }


            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (origin != null) {
                    JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, chartDrawView);
                    if (viewPort != null) {
                        int deltaX = origin.x - e.getX();
                        int deltaY = origin.y - e.getY();

                        Rectangle view = viewPort.getViewRect();
                        view.x += deltaX * 0.3;
                        view.y += deltaY * 0.2;
                        chartDrawView.scrollRectToVisible(view);
                    }
                }
            }
        };
        chartDrawView.addMouseListener(mouseAdapter);
        chartDrawView.addMouseMotionListener(mouseAdapter);
        scrollPanel.setPreferredSize(new Dimension(800, 600));
        scrollPanel.setVisible(true);

        JLabel graphALabel = new JLabel("Graph A");
        graphALabel.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 20));
        graphALabel.setForeground(new Color(255, 12, 0));
        JLabel graphBLabel = new JLabel("Graph B");
        graphBLabel.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 20));
        graphBLabel.setForeground(new Color(0x0000C3));

        toolBar.add(graphALabel);
        toolBar.add(slider);
        toolBar.add(graphBLabel);
        this.add(scrollPanel, BorderLayout.WEST);
        this.add(toolBar, BorderLayout.SOUTH);
        this.setVisible(true);
        scrollPanel.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.isControlDown()) {
                    if (e.getWheelRotation() < 0) {
                        chartDrawView.setZoom(chartDrawView.getZoom() + 0.1);

                    } else {
                        chartDrawView.setZoom(chartDrawView.getZoom() - 0.1);
                    }
                    slider.setValue((int) (chartDrawView.getZoom() * 100));
                    chartDrawView.revalidate();
                }
            }
        });
    }

    public ChartDrawView getChartDrawView() {
        return chartDrawView;
    }
}
