package com.company.View;

import com.company.Controller.ChartDrawController;
import com.company.Controller.MainController;

import javax.swing.*;
import java.awt.*;

public class MainView {
    MainController mainController = new MainController();
    private JFrame mainFrame = new JFrame("Functions");
    private JPanel backgroundPanel = new JPanel();
    private JSplitPane splitPaneTableGraph = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private ChartDrawController controller = new ChartDrawController(splitPaneTableGraph);


    public void initialize() {
        backgroundPanel.setLayout(new BorderLayout());

        JPanel infoPanel = mainController.createInfoPanel();
        infoPanel.setBorder(BorderFactory.createEtchedBorder());

        JSplitPane splitPaneH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        backgroundPanel.add(splitPaneH, BorderLayout.CENTER);
        splitPaneH.setLeftComponent(infoPanel);
        splitPaneH.setRightComponent(splitPaneTableGraph);
        mainFrame.add(backgroundPanel);
        mainFrame.setSize(1500, 900);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainController.action(mainFrame, splitPaneTableGraph, controller);
        mainFrame.setVisible(true);
    }
    public static void main(String[] args) {
        MainView mainView = new MainView();
        mainView.initialize();
    }
}
