package player;

import Common.IGameState;
import Common.IMap;
import Common.game_state;
import Common.map;
import Common.place.Place;
import Common.players.PlayersInfo;
import Common.tiles.TileColor;
import Common.tiles.TileObject;
import Common.tiles.TileShape;
import Player.IPlayer;
import Player.MoveType.ExchangeMove;
import Player.MoveType.IMove;
import Player.MoveType.PassMove;
import Player.MoveType.PlaceMove;
import Player.dag;
import Player.ldasg;
import Player.player;
import java.util.AbstractMap.SimpleEntry;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * To test the player class
 */
public class playerTest {

  IPlayer p1;
  IPlayer p2;
  IPlayer p3;
  IPlayer p4;

  IMap map1;
  IMap map2;
  IMap map3;
  IMap map4;

  IGameState gameState1;
  IGameState gameState2;
  IGameState gameState3;
  IGameState gameState4;

  Map<Place, TileObject> map1Board;
  Map<Place, TileObject> map2Board;
  Map<Place, TileObject> map3Board;
  Map<Place, TileObject> map4Board;

  TileObject greenDiamond;
  TileObject orangeDiamond;
  TileObject orangeSquare;
  TileObject yellowStar;
  TileObject orangeCircle;
  TileObject orangeStar;
  TileObject orange8star;
  TileObject orangeClover;

  List<TileObject> playerHand1;
  List<TileObject> playerHand2;
  List<TileObject> playerHand3;
  List<TileObject> playerHand4;

  @Before
  public void initData() {
    p1 = new player("a", new dag());
    p2 = new player("b", new dag());
    p3 = new player("c", new ldasg());
    p4 = new player("d", new ldasg());
    greenDiamond = new TileObject(TileShape.DIAMOND, TileColor.GREEN);
    yellowStar = new TileObject(TileShape.STAR, TileColor.YELLOW);
    orangeDiamond = new TileObject(TileShape.DIAMOND, TileColor.ORANGE);
    orangeSquare = new TileObject(TileShape.SQUARE, TileColor.ORANGE);
    orangeCircle = new TileObject(TileShape.CIRCLE, TileColor.ORANGE);
    orange8star = new TileObject(TileShape.EIGHT_STAR, TileColor.ORANGE);
    orangeClover = new TileObject(TileShape.CLOVER, TileColor.ORANGE);
    orangeStar = new TileObject(TileShape.STAR, TileColor.ORANGE);
    playerHand1 = new ArrayList<>();
    playerHand1.add(orangeDiamond);
    playerHand1.add(greenDiamond);
    playerHand1.add(yellowStar);
    playerHand1.add(orangeCircle);
    map1Board = new HashMap<>();
    map1Board.put(new Place(0, 0), orangeSquare);
    map1Board.put(new Place(1, 0), orangeClover);
    map1Board.put(new Place(2, 0), orangeStar);
    map1Board.put(new Place(3, 0), orangeCircle);
    map1Board.put(new Place(4, 0), orange8star);
    map1 = new map(map1Board);

    playerHand2 = new ArrayList<>();
    playerHand2.add(orangeStar);
    playerHand2.add(yellowStar);
    playerHand2.add(orange8star);
    map2Board = new HashMap<>();
    map2Board.put(new Place(-100, -5), yellowStar);
    map2 = new map(map2Board);

    playerHand3 = new ArrayList<>();
    playerHand3.add(orangeStar);
    playerHand3.add(yellowStar);
    playerHand3.add(orange8star);
    map3Board = new HashMap<>();
    map3 = new map(map3Board);

    playerHand4 = new ArrayList<>();
    playerHand4.add(orangeClover);
    playerHand4.add(orangeSquare);
    map4Board = new HashMap<>();
    map4Board.put(new Place(0, 0), orangeSquare);
    map4Board.put(new Place(1, 1), yellowStar);
    map4Board.put(new Place(2, 1), orange8star);
    map4Board.put(new Place(-1, 0), orange8star);
    map4Board.put(new Place(-1, 1), greenDiamond);
    map4 = new map(map4Board);
    gameState1 = new game_state(map1, new PlayersInfo(), 10);
    gameState2 = new game_state(map2, new PlayersInfo(), 2);
    gameState3 = new game_state(map3, new PlayersInfo(), 0);
    gameState4 = new game_state(map4, new PlayersInfo(), 100);
  }

  @Test
  public void testName() {
    assertEquals(p1.name(), "a");
    assertEquals(p2.name(), "b");
    assertEquals(p3.name(), "c");
    assertEquals(p4.name(), "d");
  }

  @Test
  public void testSetUp() {
    assertEquals(p1.getMap().getMapState(), new map().getMapState());
    assertEquals(p2.getMap().getMapState(), new map().getMapState());
    assertEquals(p3.getMap().getMapState(), new map().getMapState());
    assertEquals(p4.getMap().getMapState(), new map().getMapState());
    assertEquals(p1.getHand(), new ArrayList<>());
    assertEquals(p2.getHand(), new ArrayList<>());
    assertEquals(p3.getHand(), new ArrayList<>());
    assertEquals(p4.getHand(), new ArrayList<>());
    p1.setUp(gameState1.getPublicData(), playerHand1);
    p2.setUp(gameState2.getPublicData(), playerHand2);
    p3.setUp(gameState3.getPublicData(), playerHand3);
    p4.setUp(gameState4.getPublicData(), playerHand4);
    assertEquals(p1.getMap().getMapState(), map1.getMapState());
    assertEquals(p2.getMap().getMapState(), map2.getMapState());
    assertEquals(p3.getMap().getMapState(), map3.getMapState());
    assertEquals(p4.getMap().getMapState(), map4.getMapState());
    assertEquals(p1.getHand(), playerHand1);
    assertEquals(p2.getHand(), playerHand2);
    assertEquals(p3.getHand(), playerHand3);
    assertEquals(p4.getHand(), playerHand4);
  }

  @Test
  public void testTakeTurn() {
    IMove resultExchangeMove = new ExchangeMove();
    assertEquals(p1.takeTurn(gameState1.getPublicData()), resultExchangeMove);
    assertEquals(p2.takeTurn(gameState2.getPublicData()), resultExchangeMove);
    assertEquals(p3.takeTurn(gameState3.getPublicData()), resultExchangeMove);
    assertEquals(p4.takeTurn(gameState4.getPublicData()), resultExchangeMove);
    p1.setUp(gameState1.getPublicData(), playerHand1);
    p2.setUp(gameState2.getPublicData(), playerHand2);
    p3.setUp(gameState3.getPublicData(), playerHand3);
    p4.setUp(gameState4.getPublicData(), playerHand4);
    IMove p1Move = new PlaceMove(List.of(
            new SimpleEntry<>(new Place(2, -1), yellowStar),
            new SimpleEntry<>(new Place(0, -1), orangeCircle)));
    //IMove p = p1.takeTurn(gameState1.getPublicData());
    assertEquals(p1.takeTurn(gameState1.getPublicData()), p1Move);
    IMove p2Move = new PlaceMove(List.of(new SimpleEntry<>(
            new Place(-100, -6), yellowStar),
            new SimpleEntry<>(new Place(-100, -7), orangeStar),
            new SimpleEntry<>(new Place(-100, -8), orange8star)));
    assertEquals(p2.takeTurn(gameState2.getPublicData()), p2Move);
    IMove p4Move = new PlaceMove(List.of(new SimpleEntry<>(
            new Place(-1, -1), orangeSquare),
            new SimpleEntry<>(new Place(0, -1), orangeClover)));
    assertEquals(p4.takeTurn(gameState4.getPublicData()), p4Move);

    assertEquals(p3.takeTurn(gameState3.getPublicData()), new PassMove());
  }

  @Test
  public void testNewTiles() {
    p4.setUp(gameState4.getPublicData(), playerHand4);
    IMove p4Move = new PlaceMove(List.of(new SimpleEntry<>(
                    new Place(-1, -1), orangeSquare),
            new SimpleEntry<>(new Place(0, -1), orangeClover)));
    assertEquals(p4.takeTurn(gameState4.getPublicData()), p4Move);
    List<TileObject> p4NewHand = new ArrayList<>();
    p4NewHand.add(yellowStar);
    p4.newTiles(p4NewHand);
    IMove p4NewTilesMove = new PlaceMove(List.of(new SimpleEntry<>(new Place(1, 2), yellowStar)));
    assertEquals(p4.takeTurn(gameState4.getPublicData()), p4NewTilesMove);
  }

  @Test
  public void testWin() {
    p1.win(true);
    p2.win(true);
  }

}
