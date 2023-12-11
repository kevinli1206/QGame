package Referee;

import java.util.List;
import java.util.Map.Entry;

import Common.IGameState;
import Player.IPlayer;

/**
 * A function to run Q games.
 * Referee Interaction Handling:
 * - Catches invalid inputs from its players and kicks them out of the game (safety bug)
 * - Catches player inputs that break rule logic and kicks them out of the game (business logic)
 * - Catches if a player takes too long to give their move and kicks them out of the game (DoS)
 * Remote Communication Handling:
 * - Player disconnect from their socket that they're using to communicate with the referee
 */
public interface IReferee {

  /**
   * @param players the players playing the Q game
   * @return the list of winning and list of losing players from this referee running a Q
   * game to completion with the given players.
   */
  Entry<List<String>, List<String>> runGame(List<IPlayer> players);

  /**
   * @param players the players playing the Q game
   * @param gamestate the existing game state to start with
   * @return the list of winning and list of losing players from this referee running a Q
   * game to completion with the given players starting from the given game state.
   */
  Entry<List<String>, List<String>> runExistingGame(List<IPlayer> players, IGameState gamestate);
}
