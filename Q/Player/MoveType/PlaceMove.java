package Player.MoveType;

import java.util.List;
import java.util.Map.Entry;

import Common.place.Place;
import Common.tiles.TileObject;

/**
 * To define a place move type
 */
public class PlaceMove implements IMove {

  private List<Entry<Place, TileObject>> placements;

  /**
   * Place move constructor.
   * @param placements the placements the player wants to make
   */
  public PlaceMove(List<Entry<Place, TileObject>> placements) {
    this.placements = placements;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof IMove)) {
      return false;
    }
    if (!(o instanceof PlaceMove)) {
      return false;
    }
    PlaceMove other = (PlaceMove) o;
    return this.placements.equals(other.placements);
  }

  @Override
  public List<Entry<Place, TileObject>> getPlacements() {
    return placements;
  }

}
