package Referee.ActionListeners;

import Referee.observer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * An action listener to set the next game state
 */
public class NextButtonActionListener implements ActionListener {
  private final observer obs;

  /**
   * NextButtonActionListener constructor
   * @param o the observer
   */
  public NextButtonActionListener(observer o) {
    this.obs = o;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.obs.setNext();
    this.obs.updateGUI();
  }


}
