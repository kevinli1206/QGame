package Player;

import Common.IMap;
import Common.data.PublicData;
import Common.place.Place;
import Common.tiles.TileColor;
import Common.tiles.TileObject;
import Common.tiles.TileShape;
import Common.*;
import Player.MoveType.ExchangeMove;
import Player.MoveType.IMove;

import java.util.*;

import Player.MoveType.PlaceMove;

import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

/**
 * Represents a player who attempts to cheat during the game
 */
public class CheatingPlayer extends APlayer {

  private final String cheatingType;

  /**
   * ExceptionPlayer constructor with all parameters
   *
   * @param cheatingType the type of cheat this player will attempt to use
   * @param name         the name of the player
   * @param map          the map the player knows about
   * @param hand         the player's current hand
   * @param strategy     the strategy the player wants to use
   */
  public CheatingPlayer(String cheatingType, String name, IMap map,
      List<TileObject> hand, strategy strategy) {
    super(name, map, hand, strategy);
    switch (cheatingType) {
      case "non-adjacent-coordinate":
      case "tile-not-owned":
      case "not-a-line":
      case "bad-ask-for-tiles":
      case "no-fit":
        break;
      default:
        throw new IllegalArgumentException("Invalid cheat");
    }
    this.cheatingType = cheatingType;
  }

  /**
   * Default CheatingPlayer constructor
   *
   * @param name         the name of the player
   * @param strategy     the strategy the player wants to use
   * @param cheatingType the cheat the player wants to use
   */
  public CheatingPlayer(String cheatingType, String name, strategy strategy) {
    super(name, strategy);
    switch (cheatingType) {
      case "non-adjacent-coordinate":
      case "tile-not-owned":
      case "not-a-line":
      case "bad-ask-for-tiles":
      case "no-fit":
        break;
      default:
        throw new IllegalArgumentException("Invalid cheat");
    }
    this.cheatingType = cheatingType;
  }

  /**
   * A player playing a turn who attempts to cheat
   */
  @Override
  public IMove takeTurn(PublicData publicData) {
    this.strategy.reset();
    IMove move = new PlaceMove(new ArrayList<>());
    switch (this.cheatingType) {
      case "non-adjacent-coordinate":
        move = this.findNonAdjacentCoordinate(publicData, move);
        break;
      case "tile-not-owned":
        move = this.findNotOwnedTiles(publicData, move);
        break;
      case "not-a-line":
        move = this.findNotALine(publicData);
        break;
      case "bad-ask-for-tiles":
        move = this.getBadAskForTiles(publicData, move);
        break;
      case "no-fit":
        move = this.findNoFit(publicData, move);
        break;
      default:
        break;
    }
    if (move instanceof PlaceMove && move.getPlacements().isEmpty()) {
      return super.takeTurn(publicData);
    }
    return move;
  }

  /**
   * @param publicData public information about the game state
   * @param move       the current move
   * @return a bad exchangeMove if the player tries to cheat during exchange, otherwise the given move
   */
  private IMove getBadAskForTiles(PublicData publicData, IMove move) {
    if (this.cheatingType.equals("bad-ask-for-tiles")
        && this.hand.size() > publicData.getTilesLeft()) {
      move = new ExchangeMove();
    }
    return move;
  }

  /**
   * @param publicData public information about the game state
   * @param move       the computed move
   * @return a move containing the placement of a tile that is not adjacent to a tile on the map
   */
  private IMove findNonAdjacentCoordinate(PublicData publicData, IMove move) {
    if (!this.hand.isEmpty()) {
      move.getPlacements().add(
          new SimpleEntry<>(
              this.getANonValidPlacement(publicData), this.hand.get(0)));
    }
    return move;
  }

  /**
   * @param publicData the public information about the gamestate
   * @return a place that is outside the current map
   */
  private Place getANonValidPlacement(PublicData publicData) {
    int row = publicData.getBoard().getMaxYCoordinateOnMap();
    int col = publicData.getBoard().getMaxXCoordinateOnMap();
    return new Place(col + 1, row + 1);
  }


  /**
   * @param publicData public information about the game state
   * @param move the current move
   * @return a move containing the placement of a tile that does not match its adjacent tiles
   * or the current move if not possible
   */
  private IMove findNoFit(PublicData publicData, IMove move) {
    if (this.hand.isEmpty()) {
      return move;
    }
    for (TileObject to : this.hand) {
      Set<Place> allMapPlacements = publicData.getBoard().getAllAdjacentPlaces();
      Set<Place> validPlacements = publicData.getBoard().getValidPlacements(to);
      allMapPlacements.removeAll(validPlacements);
      if (!allMapPlacements.isEmpty()) {
        move.getPlacements().add(new SimpleEntry<>(allMapPlacements.iterator().next(), to));
        return move;
      }
    }
    return move;
  }

  /**
   * @param publicData public information about the game state
   * @param move the current move
   * @return a move containing the placement of a tile that this player does not own
   */
  private IMove findNotOwnedTiles(PublicData publicData, IMove move) {
    for (TileShape ts : TileShape.values()) {
      for (TileColor tc : TileColor.values()) {
        TileObject to = new TileObject(ts, tc);
        if (!this.hand.contains(to)) {
          Set<Place> validPlacements = publicData.getBoard().getValidPlacements(to);
          if (!validPlacements.isEmpty()) {
            move.getPlacements().add(new SimpleEntry<>(validPlacements.iterator().next(), to));
            return move;
          }
        }
      }
    }
    return move;
  }

  /**
   * can ANY tile not in alreadySeen be placed here?
   *
   */
  public List<Entry<Place, TileObject>> dfs(Place place, Map<TileObject, Integer> playersHand, IMap map, List<Entry<Place, TileObject>> placements) {
    for (TileObject tile: playersHand.keySet()) {
      if (playersHand.get(tile) > 0) {
        // given place doesn't work
        if (map.getValidPlacements(tile).contains(place)) {
          IMap mapCopy = new map(map.getMapState());
          mapCopy.placeTile(tile, place);
          List<Entry<Place, TileObject>> placementCopy = new ArrayList<>(placements);
          placementCopy.add(new SimpleEntry<>(place, tile));
          Map<TileObject, Integer> handCopy = new HashMap<>(playersHand);
          handCopy.put(tile, playersHand.get(tile)-1);
          for (Place neighbor: place.getCardinalNeighbors()) {
            List<Entry<Place, TileObject>> child = dfs(neighbor, handCopy, mapCopy, placementCopy);
            if (!child.isEmpty() && !map.sameRowOrColumn(child, child.get(0).getKey())) {
              return child;
            }
          }
        }
      }
    }
    return placements; // base case
  }

  /**
   * @param publicData public information about the game state
   * @return a move containing placements of tiles that are not all in one line
   */
  public IMove findNotALine(PublicData publicData) {
    if (this.hand.size() < 2) {
      return super.takeTurn(publicData);
    }
    IMap map = new map(publicData.getBoard().getMapState()); // don't want to mutate publicData here

    Map<TileObject, Integer> handInHashMap = new HashMap<>(); // tile to frequency mapping
    for (TileObject tile: hand) {
      if (!handInHashMap.containsKey(tile)) {
        handInHashMap.put(tile, 1);
      }
      else {
        handInHashMap.put(tile, handInHashMap.get(tile)+1);
      }
    }

    for (TileObject tile: hand) {
      for (Place place: map.getValidPlacements(tile)) {
        IMap mapCopy = new map(map.getMapState());
        mapCopy.placeTile(tile, place);
        List<Entry<Place, TileObject>> placements = new ArrayList<>();
        placements.add(new SimpleEntry<>(place, tile));
        Map<TileObject, Integer> handsCopy = new HashMap<>(handInHashMap);
        handsCopy.put(tile, handsCopy.get(tile)-1);
        for (Place neighbor: place.getCardinalNeighbors()) {
          List<Entry<Place, TileObject>> placementsCopy = new ArrayList<>(placements);
          List<Entry<Place, TileObject>> child = dfs(neighbor, handsCopy, mapCopy, placementsCopy);
          if (!child.isEmpty() && !map.sameRowOrColumn(child, child.get(0).getKey())) {
            return new PlaceMove(child);
          }
        }
      }
    }
    System.out.print(this.hand.size());
    return super.takeTurn(publicData);
  }


}
