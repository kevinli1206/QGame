package Player.MoveType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import Common.place.Place;
import Common.tiles.TileObject;

/**
 * To represent an exchange move
 */
public class ExchangeMove implements IMove {

  @Override
  public List<Entry<Place, TileObject>> getPlacements() {
    return new ArrayList<>();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof IMove)) {
      return false;
    }
    if (!(o instanceof ExchangeMove)) {
      return false;
    }
    return true;
  }

}
