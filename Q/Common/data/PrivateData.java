package Common.data;

import Common.tiles.TileObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * To define private data about a player that needs to be sent to the player.
 */
public class PrivateData {

  private final List<TileObject> playerHand;

  /**
   * PrivateData constructor
   *
   * @param playerHand the list of tiles in this player's hand
   */
  public PrivateData(List<TileObject> playerHand) {
    this.playerHand = playerHand;
  }

  public List<TileObject> getPlayerHand() {
    return new ArrayList<>(playerHand);
  }

  /**
   * Remove the given tile from this player's hand
   * @param to the tile to be removed.
   */
  public void removeFromHand(TileObject to) {
    this.playerHand.remove(to);
  }

  /**
   * Sort this player's hand by shape and then color
   */
  public void sortHand() {
    this.playerHand.sort(Comparator.comparing(TileObject::getShape).thenComparing(TileObject::getColor));
  }

}
