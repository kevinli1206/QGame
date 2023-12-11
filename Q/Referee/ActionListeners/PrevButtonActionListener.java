package Referee.ActionListeners;

import Referee.observer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * An action listener to set the previous game state
 */
public class PrevButtonActionListener implements ActionListener {
  private final observer obs;

  /**
   * PrevButtonActionListener constructor
   * @param o the observer
   */
  public PrevButtonActionListener(observer o) {
    this.obs = o;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.obs.setPrevious();
    this.obs.updateGUI();
  }

}
