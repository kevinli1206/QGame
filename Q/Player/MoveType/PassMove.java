package Player.MoveType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import Common.place.Place;
import Common.tiles.TileObject;

/**
 * To define a pass move type.
 */
public class PassMove implements IMove {

  @Override
  public List<Entry<Place, TileObject>> getPlacements() {
    return new ArrayList<>();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof IMove)) {
      return false;
    }
    if (!(o instanceof PassMove)) {
      return false;
    }
    return true;
  }
}
