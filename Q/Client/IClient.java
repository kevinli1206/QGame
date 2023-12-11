package Client;

/**
 * To represent real players who wish to play the game
 */
public interface IClient extends Runnable {

  /**
   * Sends the name of this client to the player
   */
  void sendName();

}
