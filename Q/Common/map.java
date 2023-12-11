package Common;

import Common.place.Place;
import Common.tiles.TileObject;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;
import java.util.Set;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * To represent a simple map for The Q Game.
 */
public class map implements IMap {

  private final Map<Place, TileObject> map;

  /**
   * Default Constructor for the map
   */
  public map() {
    map = new HashMap<>();
  }

  /**
   * Constructor for map that initializes the given map
   *
   * @param existingMap the map to initialize
   */
  public map(Map<Place, TileObject> existingMap) {
    map = new HashMap<>(existingMap);
  }

  @Override
  public void initializeFirstTile(TileObject tile, Place place) throws IllegalArgumentException {
    if (!map.isEmpty()) {
      throw new IllegalArgumentException("Map already has a tile");
    }
    this.insertTile(tile, place);
  }

  @Override
  public void placeTile(TileObject tile, Place place) throws IllegalArgumentException {
    if (!this.isConnected(place)) {
      throw new IllegalArgumentException("Tile not connected");
    }

    this.insertTile(tile, place);
  }

  /**
   * Put a tile on this map at the given place
   *
   * @param tile the tile to be placed
   * @param place the coordinate on this map to place the tile at
   * @throws IllegalArgumentException if a tile already exists at the place on the map
   */
  private void insertTile(TileObject tile, Place place) throws IllegalArgumentException {
    if (tileExists(place)) {
      throw new IllegalArgumentException("Tile already exists at place");
    }
    map.put(place, tile);
  }

  /**
   * @param place the coordinate to check for
   * @return true if the place on this map has at least 1 neighboring tile
   */
  private boolean isConnected(Place place) {
    for (Place pl : place.getCardinalNeighbors()) {
      if (tileExists(pl)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param place the coordinate to check for
   * @return true if a tile exists on this map at the given location
   */
  private boolean tileExists(Place place) {
    return map.containsKey(place);
  }

  @Override
  public Set<Place> getValidPlacements(TileObject tile) {
    Set<Place> valid = new HashSet<>();
    Set<Place> freeNeighbors = this.getAllNeighbors();
    for (Place pl : freeNeighbors) {
      if (!tileExists(pl) && isValidPlacement(tile, pl)) {
        valid.add(pl);
      }
    }
    return valid;
  }

  @Override
  public Set<Place> getAllAdjacentPlaces() {
    Set<Place> valid = new HashSet<>();
    Set<Place> freeNeighbors = this.getAllNeighbors();
    for (Place pl : freeNeighbors) {
      if (!tileExists(pl)) {
        valid.add(pl);
      }
    }
    return valid;
  }

  @Override
  public Set<Place> getValidPlacementsInLine(TileObject tile,
                                      List<Entry<Place, TileObject>> seenPlacements) {
    List<Place> valid = new ArrayList<>(this.getValidPlacements(tile));
    for (int i = valid.size() - 1; i >= 0; i--) {
      Place currentPlace = valid.get(i);
      if (!this.sameRowOrColumn(seenPlacements, currentPlace)) {
        valid.remove(currentPlace);
      }
    }
    return new HashSet<>(valid);
  }

  @Override
  public boolean sameRowOrColumn(List<Entry<Place, TileObject>> placements, Place firstPlace) {
    boolean sameRow = true;
    boolean sameColumn = true;
    for (Entry<Place, TileObject> entry : placements) {
      Place currPlace = entry.getKey();
      if (currPlace.getX() != firstPlace.getX()) {
        sameColumn = false;
      }
      if (currPlace.getY() != firstPlace.getY()) {
        sameRow = false;
      }
    }
    return sameRow || sameColumn;
  }

  @Override
  public int getNumberOfExistingNeighbors(Place placement) {
    int numExistingNeighbors = 0;
    for (Place p : placement.getCardinalNeighbors()) {
      if (tileExists(p)) {
        numExistingNeighbors++;
      }
    }
    return numExistingNeighbors;
  }


  /**
   * @return a set of all free neighbor tiles on this map
   */
  public Set<Place> getAllNeighbors() {
    Set<Place> neighbors = new HashSet<>();
    for (Place pl : map.keySet()) {
      for (Place newPlacement : pl.getCardinalNeighbors()) {
        neighbors.add(newPlacement);
      }
    }
    return neighbors;
  }

  /**
   * @param tile is the tile to be inserted
   * @param place is the coordinate to insert at
   * @return true if the tile can be inserted on this map according to the rules of The Q Game
   */
  private boolean isValidPlacement(TileObject tile, Place place) {
    return isMatchingLineNeighbors(tile, place.getColumnNeighbors()) &&
        isMatchingLineNeighbors(tile, place.getRowNeighbors());
  }

  /**
   * @param tile is the tile to check for
   * @param lineNeighbors the array of places to check against
   * @return true if the tile matches in the same way for all places given on this map
   */
  private boolean isMatchingLineNeighbors(TileObject tile, Place[] lineNeighbors) {
    boolean matchColor = true;
    boolean matchShape = true;
    for (Place neighbor : lineNeighbors) {
      if (tileExists(neighbor)) {
        TileObject neighborTile = map.get(neighbor);
        if (!neighborTile.isSameShape(tile)) {
          matchShape = false;
        }
        if (!neighborTile.isSameColor(tile)) {
          matchColor = false;
        }
      }
    }
    return matchShape || matchColor;
  }

  @Override
  public Map<Place, TileObject> getMapState() {
    return new HashMap<>(map);
  }

  @Override
  public Set<Entry<Place, TileObject>> getContiguousRowSequence(Entry<Place, TileObject> tile) {
    Set<Entry<Place, TileObject>> row = new HashSet<>();
    Place currentPlaceLeft = tile.getKey();
    Place currentPlaceRight = tile.getKey();
    while (tileExists(currentPlaceLeft)) {
      row.add(new SimpleEntry<>(currentPlaceLeft, map.get(currentPlaceLeft)));
      currentPlaceLeft = currentPlaceLeft.getNeighborLeft();
    }
    while (tileExists(currentPlaceRight)) {
      row.add(new SimpleEntry<>(currentPlaceRight, map.get(currentPlaceRight)));
      currentPlaceRight = currentPlaceRight.getNeighborRight();
    }
    return row;
  }

  @Override
  public Set<Entry<Place, TileObject>> getContiguousColumnSequence(Entry<Place, TileObject> tile) {
    Set<Entry<Place, TileObject>> column = new HashSet<>();
    Place currentPlaceUp = tile.getKey();
    Place currentPlaceDown = tile.getKey();
    while (tileExists(currentPlaceUp)) {
      column.add(new SimpleEntry<>(currentPlaceUp, map.get(currentPlaceUp)));
      currentPlaceUp = currentPlaceUp.getNeighborUp();
    }
    while (tileExists(currentPlaceDown)) {
      column.add(new SimpleEntry<>(currentPlaceDown, map.get(currentPlaceDown)));
      currentPlaceDown = currentPlaceDown.getNeighborDown();
    }
    return column;
  }

  @Override
  public int getMinXCoordinateOnMap() {
    int minX = Integer.MAX_VALUE;
    for (Map.Entry<Place, TileObject> entry : this.map.entrySet()) {
      minX = Math.min(minX, entry.getKey().getX());
    }
    return minX;
  }

  @Override
  public int getMinYCoordinateOnMap() {
    int minY = Integer.MAX_VALUE;
    for (Map.Entry<Place, TileObject> entry : this.map.entrySet()) {
      minY = Math.min(minY, entry.getKey().getY());
    }
    return minY;
  }

  @Override
  public int getMaxXCoordinateOnMap() {
    int maxX = Integer.MIN_VALUE;
    for (Map.Entry<Place, TileObject> entry : this.map.entrySet()) {
      maxX = Math.max(maxX, entry.getKey().getX());
    }
    return maxX;
  }

  @Override
  public int getMaxYCoordinateOnMap() {
    int maxY = Integer.MIN_VALUE;
    for (Map.Entry<Place, TileObject> entry : this.map.entrySet()) {
      maxY = Math.max(maxY, entry.getKey().getY());
    }
    return maxY;
  }

  /**
   * @return the graphical representation of this map
   */
  @Override
  public BufferedImage renderMap() {
    int tileSize = 125;
    int mapWidth = (this.getMaxXCoordinateOnMap() - this.getMinXCoordinateOnMap() + 1) * (tileSize + 2);
    int mapHeight = (this.getMaxYCoordinateOnMap() - this.getMinYCoordinateOnMap() + 1) * (tileSize + 2);
    BufferedImage mapCanvas = new BufferedImage(mapWidth, mapHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D mapGraphics = mapCanvas.createGraphics();

    for (Map.Entry<Place, TileObject> mapTile : this.map.entrySet()) {
      try {
        BufferedImage objectImage = ImageIO.read(getClass().getResourceAsStream(
                "./images/" + mapTile.getValue().getImageFileName()));
//        BufferedImage objectImage = ImageIO.read(new File("../Q/Common/images/"
//            + mapTile.getValue().getImageFileName()));
        BufferedImage placeImage = mapTile.getKey().renderPlace();
        int xShiftBy = -this.getMinXCoordinateOnMap();
        int yShiftBy = -this.getMinYCoordinateOnMap();
        mapGraphics.drawImage(objectImage,
            (mapTile.getKey().getX() + xShiftBy) * tileSize - 2,
            (mapTile.getKey().getY() + yShiftBy) * tileSize - 2, null);
        mapGraphics.drawImage(placeImage,
            (mapTile.getKey().getX() + xShiftBy) * tileSize + 55,
            (mapTile.getKey().getY() + yShiftBy) * tileSize + 10, null);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    mapGraphics.dispose();
    return mapCanvas;
  }

}