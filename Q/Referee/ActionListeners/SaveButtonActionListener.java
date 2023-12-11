package Referee.ActionListeners;

import Referee.observer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * An action listener to save the current game state
 */
public class SaveButtonActionListener implements ActionListener {
  private final observer obs;

  /**
   * SaveButtonActionListener constructor
   * @param o the observer
   */
  public SaveButtonActionListener(observer o) {
    this.obs = o;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.obs.saveCurrentState();
  }

}
