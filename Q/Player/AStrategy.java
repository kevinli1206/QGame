package Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.Set;

import Common.data.PrivateData;
import Common.data.PublicData;
import Common.place.Place;
import Common.tiles.TileObject;
import Player.MoveType.ExchangeMove;
import Player.MoveType.IMove;
import Player.MoveType.PassMove;
import Player.MoveType.PlaceMove;

/**
 * To implement abstract methods for the strategy
 */
public abstract class AStrategy implements strategy {
  private List<Entry<Place, TileObject>> seenPlacements;

  /**
   * Abstract Strategy constructor that initializes the placements we have
   * already seen for this strategy to a new empty list
   *
   */
  public AStrategy() {
    this.seenPlacements = new ArrayList<>();
  }

  @Override
  public IMove applyStrategy(PublicData gameStateData, PrivateData playerData) {
    if (!this.canPlace(playerData, gameStateData)) {
      return this.exchangeOrPass(gameStateData, playerData);
    } else {
      return executePlaceTurn(gameStateData, playerData);
    }
  }

  /**
   * resets a strategy's seen placements.
   */
  public void reset() {
    this.seenPlacements = new ArrayList<>();
  }

  /**
   * @return the placement move that this strategy comes up with
   * EFFECT:
   * 1. add to the seenPlacements field
   * 2. mutates gameStateData and playerData inside placeOnBoard
   */
  private IMove executePlaceTurn(PublicData gameStateData, PrivateData playerData) {
    TileObject to = this.getSmallestTileCanPlace(playerData, gameStateData);
    List<Place> validPlacements = this.validPlacesAsList(to, gameStateData);
    List<Place> possiblePlaces = listPossiblePlaces(validPlacements, gameStateData);
    Place p = getSmallestPlaceByRowColumn(possiblePlaces);
    this.placeOnBoard(to, p, playerData, gameStateData);
    List<Entry<Place, TileObject>> placements = new ArrayList<>();
    placements.add(new SimpleEntry<>(p, to));
    seenPlacements.addAll(placements);
    return new PlaceMove(placements);
  }

  @Override
  public IMove applyStrategyIterator(PublicData gameStateData, PrivateData playerData) {
    IMove strategyTurn = this.applyStrategy(gameStateData, playerData);
    while (this.canPlace(playerData, gameStateData)) {
      IMove currentMove = this.applyStrategy(gameStateData, playerData);
      if (!gameStateData.getBoard().sameRowOrColumn(seenPlacements, currentMove.getPlacements().get(0).getKey())) {
        break;
      }
      strategyTurn.getPlacements().addAll(currentMove.getPlacements());
    }
    //seenPlacements.clear();
    return strategyTurn;
  }

  /**
   * @param validPlacements are all the valid places to place on the map.
   * @return the list of possible placements based on this strategy's type.
   */
  protected abstract List<Place> listPossiblePlaces(List<Place> validPlacements, PublicData gameStateData);

  /**
   * @return true for this strategy if the referee has enough tiles to exchange with the player
   */
  protected boolean canExchange(PrivateData playerData, PublicData gameStateData) {
    return gameStateData.getTilesLeft() >= playerData.getPlayerHand().size();
  }

  /**
   * @return true for this strategy if the player can place a tile on the board.
   */
  protected boolean canPlace(PrivateData playerData, PublicData gameStateData) {
    for (TileObject to : playerData.getPlayerHand()) {
      if (!gameStateData.getBoard().getValidPlacements(to).isEmpty()) {
        return true;
      }
    }
    return false;
  }

  /**
   * @return get the smallest tile that can be placed on the map for this strategy assuming that
   * there is a tile that can be placed.
   */
  protected TileObject getSmallestTileCanPlace(PrivateData playerData, PublicData gameStateData) {
    playerData.sortHand();
    TileObject tile = playerData.getPlayerHand().get(0);
    for (TileObject to : playerData.getPlayerHand()) {
      if (!gameStateData.getBoard().getValidPlacements(to).isEmpty()) {
        tile = to;
        break;
      }
    }
    return tile;
  }

  /**
   * @param playerTile is the tile for which to find all its valid placements on the map.
   * @return the valid placements for the given tile chosen by this strategy as a list.
   */
  protected List<Place> validPlacesAsList(TileObject playerTile, PublicData gameStateData) {
    Set<Place> validPlaces = gameStateData.getBoard().getValidPlacements(playerTile);
    return new ArrayList<>(validPlaces);
  }

  /**
   * @param placements the placements to get the smallest from
   * @return the smallest place in the given valid placements for this strategy
   */
  protected Place getSmallestPlaceByRowColumn(List<Place> placements) {
    placements.sort(Comparator.comparing(Place::getY).thenComparing(Place::getX));
    return placements.get(0);
  }

  /**
   * @return an exchange move for this strategy if the ref has enough tiles to exchange,
   * if not, then a pass move for this strategy.
   */
  protected IMove exchangeOrPass(PublicData gameStateData, PrivateData playerData) {
    if (this.canExchange(playerData, gameStateData)) {
      return new ExchangeMove();
    } else {
      return new PassMove();
    }
  }

  /**
   * Place the given tile on the board at the given place chosen by this strategy
   * and remove it from the player's hand
   *
   * @param to the tile to be placed
   * @param p  the place to put the tile
   */
  protected void placeOnBoard(TileObject to, Place p, PrivateData playerData, PublicData gameStateData) {
    playerData.removeFromHand(to);
    gameStateData.getBoard().placeTile(to, p);
  }

}
