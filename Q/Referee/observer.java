package Referee;

import Common.IGameState;
import Common.JSONParsingUtils;
import Referee.ActionListeners.NextButtonActionListener;
import Referee.ActionListeners.PrevButtonActionListener;
import Referee.ActionListeners.SaveButtonActionListener;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Represents an observer for a Q game whose only function is viewing a game.
 * It cannot make any changes/moves to the game.
 * The observer can see all gamestates for the current game.
 * Clicking next goes to the next state
 * Clicking previous goes to the previous state.
 * Clicking save will save the current game state to the specified file.
 */
public class observer implements IObserver {
  /**
   * represents all of the game states that the observer receives
   * TODO: consider alternative representation that hides the gamestates functionality
   */
  private final List<IGameState> gamestates;

  /**
   * Represents the current state that the observer is displaying
   */
  private int currentState;

  /**
   * JPanel for the gamestate
   */
  private JLabel statePanel;

  private JPanel entireDisplayPanel;

  /**
   * overall frame of the GUI
   */
  private JFrame jFrame;

  private JScrollPane jScrollPane;

  /**
   * Where the user wants to store their file on save
   */
  private JTextField saveDestination;

  /**
   * True if the game is over
   */
  private boolean hasGameEnded;

  private static final int DISPLAY_WIDTH = 1000;
  private static final int DISPLAY_HEIGHT = 800;

  /**
   * Observer constructor
   */
  public observer() {
    this.gamestates = new ArrayList<>();
    this.currentState = 0;
    this.hasGameEnded = false;
    this.jFrame = new JFrame();
    this.statePanel = new JLabel();
    this.saveDestination = new JTextField();
    this.jScrollPane = new JScrollPane();
  }

  /**
   * Observer constructor with given states
   */
  public observer(List<IGameState> gamestates) {
    this.gamestates = gamestates;
    this.currentState = 0;
    this.hasGameEnded = false;
    this.jFrame = new JFrame();
    this.statePanel = new JLabel();
    this.saveDestination = new JTextField();
    this.jScrollPane = new JScrollPane();
  }

  /**
   * Observer is notified that the game is now over
   */
  public void setGameOver() {
    this.hasGameEnded = true;
  }

  /**
   * Adds the given game state to this observer's list of game states
   * and also saves each game state to the testing directory.
   * @param gameState the game state to be added
   */
  public void receiveState(IGameState gameState) {
    if (this.hasGameEnded) {
      return;
    }
    this.gamestates.add(gameState);
    if (this.gamestates.size() == 1) {
      this.displayImage();
    }
    BufferedImage gameStateImage = gameState.getGameStateImage();
    try {
      ImageIO.write(gameStateImage, "png",
          new File( "8/Tmp/" + (gamestates.size() - 1) + ".png"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * displays the image from when there is one game state and ends the display when the game is over.
   */
  private void displayImage() {
    this.entireDisplayPanel = new JPanel(new FlowLayout());
    this.statePanel = this.convertStateToJLabel();
    entireDisplayPanel.add(optionsPanel());
    entireDisplayPanel.add(statePanel);
    this.jScrollPane = new JScrollPane(entireDisplayPanel);
    this.jFrame = this.imageFrame(jScrollPane);
    jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }

  /**
   * returns the options JPanel with the next, previous, and save button
   */
  private JPanel optionsPanel() {
    JPanel optionsPanel = new JPanel();
    optionsPanel.setSize(DISPLAY_WIDTH/4, DISPLAY_HEIGHT);
    optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
    JButton nextButton = new JButton("next");
    nextButton.addActionListener(new NextButtonActionListener(this));
    JButton prevButton = new JButton("previous");
    prevButton.addActionListener(new PrevButtonActionListener(this));
    JButton saveButton = new JButton("save to:");
    saveButton.addActionListener(new SaveButtonActionListener(this));
    optionsPanel.add(nextButton);
    optionsPanel.add(prevButton);
    optionsPanel.add(saveButton);
    optionsPanel.add(saveDestination);
    return optionsPanel;
  }

  /**
   * @return the JFrame for this observer's gui
   */
  private JFrame imageFrame(JScrollPane scrollPane) {
    JFrame jFrame = new JFrame("Observer GUI");
    jFrame.setContentPane(scrollPane);
    jFrame.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
    jFrame.setVisible(true);
    return jFrame;
  }

  /**
   * Set the current state to the next one
   */
  public void setNext() {
    if (this.currentState < gamestates.size() - 1) {
      this.currentState++;
    }

  }

  /**
   * Set the current state to the previous one
   */
  public void setPrevious() {
    if (this.currentState > 0) {
      this.currentState--;
    }
  }

  /**
   * Save the current game state as a JState to a file
   */
  public void saveCurrentState() {
    IGameState currentState = this.gamestates.get(this.currentState);
    try {
      FileWriter jState = new FileWriter(this.saveDestination.getText());
      String jStateJson = JSONParsingUtils.gameStateToObjectNode(currentState).toString();
      jState.write(jStateJson);
      jState.flush();
      jState.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * updates the GUI to render the current game state
   */
  public void updateGUI() {
    entireDisplayPanel.remove(statePanel);
    this.statePanel = convertStateToJLabel();
    entireDisplayPanel.add(statePanel);
    entireDisplayPanel.revalidate();
    this.jFrame.revalidate();
    this.jFrame.repaint();
  }

  /**
   * converts the current game state of this observer to JLabel
   */
  private JLabel convertStateToJLabel() {
    BufferedImage bufferedImage = this.gamestates.get(currentState).getGameStateImage();
    ImageIcon component = new ImageIcon(bufferedImage);
    return new JLabel(component);
  }

  public List<IGameState> getGameStates() {
    return this.gamestates;
  }

  public boolean getGameOver() {
    return this.hasGameEnded;
  }

  public int getCurrentState() {
    return this.currentState;
  }

}
