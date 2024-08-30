package net.lliira.game;

import net.lliira.game.gol.GameOption;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

  private static final int GAME_WIDTH = 250;
  private static final int GAME_HEIGHT = 250;

  private final GamePanel gamePanel;
  private final JTextField densityField;
  private final JTextField borderField;
  private final JTextField intervalField;

  public MainFrame() {
    gamePanel = new GamePanel(GAME_WIDTH, GAME_HEIGHT);
    densityField = new JTextField("0.5");
    borderField = new JTextField("100");
    intervalField = new JTextField("50");

    JPanel contentPane = new JPanel(new BorderLayout());
    contentPane.add(createToolBar(), BorderLayout.NORTH);
    contentPane.add(gamePanel, BorderLayout.CENTER);
    setContentPane(contentPane);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
  }

  private JToolBar createToolBar() {
    JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
    toolBar.add(new JLabel("Density:"));
    densityField.setColumns(2);
    toolBar.add(densityField);
    toolBar.add(new JLabel("Border size:"));
    toolBar.add(borderField);
    toolBar.add(new JLabel("Iteration Interval:"));
    toolBar.add(intervalField);

    JButton resetButton = new JButton("Reset");
    resetButton.addActionListener(e -> gamePanel.resetGame(getOption()));
    toolBar.add(resetButton);
    return toolBar;
  }

  public void start() {
    gamePanel.start();
  }

  private GameOption getOption() {
    return new GameOption().density(Float.valueOf(densityField.getText()))
            .border(Integer.valueOf(borderField.getText()))
            .interval(Long.valueOf(intervalField.getText()));
  }
}
