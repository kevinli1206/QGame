package Common.data;

import Common.IMap;
import Common.map;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

/**
 * To define public data about the state of this game that needs to be sent to players.
 */
public class PublicData {

  private final IMap map;

  private final int tilesLeft;

  private final Queue<Entry<String, Integer>> publicPlayerInfo;

  /**
   * PublicData constructor
   *
   * @param map              the map of the Q game
   * @param tilesLeft        the number of tiles the referee has left in their hand
   * @param publicPlayerInfo the list representing the names of the players in turn
   *                         order and their points
   */
  public PublicData(IMap map, int tilesLeft,
      Queue<Entry<String, Integer>> publicPlayerInfo) {
    this.map = map;
    this.tilesLeft = tilesLeft;
    this.publicPlayerInfo = publicPlayerInfo;
  }

  public IMap getBoard() {
    return this.map;
  }

  public IMap getBoardCopy() {
    return new map(this.map.getMapState());
  }

  public int getTilesLeft() {
    return this.tilesLeft;
  }

  public Queue<Entry<String, Integer>> getPublicPlayerInfo() {
    return new LinkedList<>(this.publicPlayerInfo);
  }

  /**
   * @param name the name of the player
   * @return the score of the player with the given name
   */
  public int getPlayerScore(String name) {
    int score = 0;
    for (Entry<String, Integer> playerNameAndScore : this.publicPlayerInfo) {
      if (playerNameAndScore.getKey().equals(name)) {
        score = playerNameAndScore.getValue();
      }
    }
    return score;
  }


}
