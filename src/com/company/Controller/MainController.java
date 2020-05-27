package com.company.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;


public class MainController {
    TableController tableController = new TableController();
    private boolean stop = false;
    private JButton createGraphABt = new JButton("      Draw A function ->");
    private JButton createGraphBBt = new JButton("      Draw B function ->");
    private JButton stopAllBt = new JButton("Stop all");
    private JLabel functionTaskB = new JLabel("Functions:");
    private JLabel functionLabel = new JLabel(new ImageIcon(MainController.class.getResource("/Functions/Functions.png")));
    private Thread threadB = new Thread();
    private Thread threadA = new Thread();
    private JLabel labelMin = new JLabel("minX");
    private JLabel labelMax = new JLabel("maxX");
    private JLabel labelA = new JLabel("a");
    private JPanel tableBPanel;
    static JPanel functionAPanel;
    private JTable funcBTable;
    private JScrollPane scrollPane;
    private JTextField minX = new JTextField(5);
    private JTextField maxX = new JTextField(5);
    private JTextField a = new JTextField(5);


    public JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(stopAllBt)
                        .addComponent(createGraphABt)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(functionTaskB)
                                .addComponent(functionLabel))
                        .addComponent(createGraphBBt)
                        .addComponent(labelMin)
                        .addComponent(minX)
                        .addComponent(labelMax)
                        .addComponent(maxX)
                        .addComponent(labelA)
                        .addComponent(a))

        );

        layout.linkSize(SwingConstants.HORIZONTAL, functionTaskB, functionLabel);

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(stopAllBt)
                .addComponent(createGraphABt)
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(functionTaskB)
                        .addComponent(functionLabel))
                .addComponent(createGraphBBt)
                .addComponent(labelMin)
                .addComponent(minX)
                .addComponent(labelMax)
                .addComponent(maxX)
                .addComponent(labelA)
                .addComponent(a)

        );
        return panel;
    }

    public JPanel createTableBPanel(JScrollPane scrollPane) {
        JPanel panel = new JPanel();
        scrollPane.setMinimumSize(new Dimension(100, 180));
        panel.setLayout(new BorderLayout());

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    public void action(JFrame mainFrame, JSplitPane splitPaneTableGraph, ChartDrawController controller) {
        createGraphABt.addActionListener(e -> {

            stop = false;
            if (threadA.isAlive()) {
                threadA.stop();
            }
            controller.clearPointList("A");
            threadA = new Thread(new Runnable() {
                @Override
                public void run() {
                    int x = 3;
                    while (x <= 10 && !stop) {
                        int F = functionA(x);
                        Point point = new Point(x, F);
                        controller.addPointToList(point, "A");
                        x += 1;
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException en) {
                            JOptionPane.showMessageDialog(mainFrame, "Ошибка!\nЧто-то пошло не так!");
                        }
                    }
                    Thread.interrupted();
                }

                int functionA(int x) {
                    return 2 * x - 2;
                }
            });
            threadA.start();
            splitPaneTableGraph.setLeftComponent(functionAPanel);
        });

        createGraphBBt.addActionListener(e -> {
            if (splitPaneTableGraph.getLeftComponent() != null) {
                tableBPanel.remove(scrollPane);
            }
            funcBTable = tableController.createTable();
            scrollPane = new JScrollPane(funcBTable);
            tableBPanel = createTableBPanel(scrollPane);
            splitPaneTableGraph.setLeftComponent(tableBPanel);
            stop = false;
            if (threadB.isAlive()) {
                threadB.stop();
            }
            controller.clearPointList("B");
            threadB = new Thread(new Runnable() {
                @Override
                public void run() {
                    double x = Double.parseDouble(minX.getText());
                    while (x <= Double.parseDouble(maxX.getText()) && !stop) {
                        double aN = Double.parseDouble(a.getText()),
                                F = functionB(x, aN);
                        Point2D point2D = new Point2D.Double(x, F);
                        controller.addPointToList(point2D, "B");
                        funcBTable = tableController.addRow(funcBTable, String.format("%.4f", (x)), String.format("%.4f", (F)));
                        x += 0.1;
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException en) {
                            JOptionPane.showMessageDialog(mainFrame, "Ошибка!\nЧто-то пошло не так!");
                        }
                    }
                    Thread.interrupted();

                }

                private double functionB(double x, double a) {
                    double F = 0;
                    double value = 1;
                    double k = 0;

                    while (value >= 0.01) {
                        value = (pow(x * Math.log(a), k)) / (factorial(k));
                        F += value;
                        k++;
                    }
                    return abs(F);
                }

                private double factorial(double n) {
                    int result = 1;
                    for (int i = 1; i <= n; i++) {
                        result = result * i;
                    }
                    return result;
                }
            });

            threadB.start();
        });

        stopAllBt.addActionListener(e -> stop = !stop);
    }
}