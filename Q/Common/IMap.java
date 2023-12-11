package Common;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.Set;
import Common.place.Place;
import Common.tiles.TileObject;
import java.util.Map.Entry;

/**
 * To represent the board for a Q game
 */
public interface IMap {

  /**
   * Create this game map with the referee's tile
   *
   * @param tile is the first tile to be placed down on the map
   * @param place the coordinate to put the tile down at
   * @throws IllegalArgumentException if a tile has already been placed on the map
   */
  void initializeFirstTile(TileObject tile, Place place) throws IllegalArgumentException;


  /**
   * Put a tile down on this map
   *
   * @param tile  the tile to be placed
   * @param place the coordinate to put the tile down at
   * @throws IllegalArgumentException if a tile already exists at the given place or if the place
   *                                  has no connecting tiles
   */
  void placeTile(TileObject tile, Place place) throws IllegalArgumentException;

  /**
   * @param tile the tile to insert on this map
   * @return a set of places where the tile can be placed on this map according to the rules of
   * The Q Game
   */
  Set<Place> getValidPlacements(TileObject tile);

  /**
   * @return a list of all places that another tile can be placed on the map regardless
   * of what that tile is
   */
  Set<Place> getAllAdjacentPlaces();

  /**
   * @param tile the tile to insert on this map
   * @param seenPlacements a list of places we have already seen
   * @return a set of places where the tile can be placed on this map according to the rules
   * of the Q game such that the tile matches the row or column of every tile
   * in the list of seen placements
   */
  Set<Place> getValidPlacementsInLine(TileObject tile,
                                      List<Entry<Place, TileObject>> seenPlacements);

  /**
   * @return a copy of the map.
   */
  Map<Place, TileObject> getMapState();

  /**
   * @param tile is the tile whose row sequence is returned.
   * @return the row sequence of tiles that contains the given tile on this map.
   */
  Set<Entry<Place, TileObject>> getContiguousRowSequence(Entry<Place, TileObject> tile);

  /**
   * @param tile is the tile whose row sequence is returned.
   * @return the column sequence of tiles that contains the given tile on this map.
   */
  Set<Entry<Place, TileObject>> getContiguousColumnSequence(Entry<Place, TileObject> tile);

  /**
   * Render the game map as an image.
   */
  BufferedImage renderMap();

  int getMinXCoordinateOnMap();

  int getMinYCoordinateOnMap();

  int getMaxXCoordinateOnMap();

  int getMaxYCoordinateOnMap();

  /**
   * @param placement the place to check for
   * @return the total number of neighbors where there is a tile for the given place on this map
   */
  int getNumberOfExistingNeighbors(Place placement);

  /**
   * @param placements the list of placements to check for
   * @param firstPlace the entry to check against
   * @return true if all placements are in the same row or same column on this map
   */
  boolean sameRowOrColumn(List<Entry<Place, TileObject>> placements, Place firstPlace);
}
