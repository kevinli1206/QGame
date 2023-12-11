package playerStrategy;

import Player.ldasg;

import java.util.LinkedList;
import java.util.Map.Entry;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

import Common.IMap;
import Common.data.PrivateData;
import Common.data.PublicData;
import Common.map;
import Common.place.Place;
import Common.tiles.TileColor;
import Common.tiles.TileObject;
import Common.tiles.TileShape;
import Player.MoveType.ExchangeMove;
import Player.MoveType.IMove;
import Player.MoveType.PassMove;
import Player.MoveType.PlaceMove;
import Player.dag;
import Player.strategy;

public class strategyTest {

  Place place1;

  Place place2;

  Place place3;

  Place place4;

  TileObject greenDiamond;

  TileObject orangeDiamond;

  TileObject orangeSquare;

  TileObject yellowStar;

  TileObject blue8star;

  TileObject orangeCircle;
  TileObject orangeStar;
  TileObject orange8star;
  TileObject orangeClover;
  TileObject redCircle;

  IMap map1;
  IMap map2;
  IMap map3;
  IMap map4;

  List<TileObject> playerHand1;
  List<TileObject> playerHand2;
  List<TileObject> playerHand3;
  List<TileObject> playerHand4;

  Map<Place, TileObject> map1Board;
  Map<Place, TileObject> map2Board;
  Map<Place, TileObject> map3Board;
  Map<Place, TileObject> map4Board;

  PublicData pubdata1;
  PublicData pubdata2;
  PublicData pubdata3;
  PublicData pubdata4;
  PrivateData privdata1;
  PrivateData privdata2;
  PrivateData privdata3;
  PrivateData privdata4;

  strategy dag1;
  strategy dag2;

  strategy dag3;
  strategy dag4;
  strategy ldasg1;
  strategy ldasg2;
  strategy ldasg3;
  strategy ldasg4;

  IMove move1;
  IMove move2;
  IMove move3;
  IMove move4;

  @Before
  public void initData() {
    place1 = new Place(0, 1);
    place2 = new Place(0, 2);
    place3 = new Place(1, 2);
    place4 = new Place(1, 0);
    greenDiamond = new TileObject(TileShape.DIAMOND, TileColor.GREEN);
    yellowStar = new TileObject(TileShape.STAR, TileColor.YELLOW);
    orangeDiamond = new TileObject(TileShape.DIAMOND, TileColor.ORANGE);
    orangeSquare = new TileObject(TileShape.SQUARE, TileColor.ORANGE);
    orangeCircle = new TileObject(TileShape.CIRCLE, TileColor.ORANGE);
    orange8star = new TileObject(TileShape.EIGHT_STAR, TileColor.ORANGE);
    orangeClover = new TileObject(TileShape.CLOVER, TileColor.ORANGE);
    orangeStar = new TileObject(TileShape.STAR, TileColor.ORANGE);
    blue8star = new TileObject(TileShape.EIGHT_STAR, TileColor.BLUE);
    redCircle = new TileObject(TileShape.CIRCLE, TileColor.RED);

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

    playerHand2 = new ArrayList<>();
    playerHand2.add(blue8star);
    playerHand2.add(orangeStar);
    playerHand2.add(yellowStar);
    playerHand2.add(orange8star);
    map2Board = new HashMap<>();
    map2Board.put(new Place(-100, -5), yellowStar);

    playerHand3 = new ArrayList<>();
    playerHand3.add(blue8star);
    playerHand3.add(orangeStar);
    playerHand3.add(yellowStar);
    playerHand3.add(orange8star);
    map3Board = new HashMap<>();

    playerHand4 = new ArrayList<>();
    playerHand4.add(blue8star);
    playerHand4.add(orangeClover);
    playerHand4.add(orangeSquare);
    playerHand4.add(redCircle);
    map4Board = new HashMap<>();
    map4Board.put(new Place(0, 0), orangeSquare);
    map4Board.put(new Place(1, 0), blue8star);
    map4Board.put(new Place(1, 1), yellowStar);
    map4Board.put(new Place(2, 1), orange8star);
    map4Board.put(new Place(-1, 0), orange8star);
    map4Board.put(new Place(-1, 1), greenDiamond);
  }

  @Test
  public void testApplyDagStrategy() {
    // first state
    map1 = new map(map1Board);
    pubdata1 = new PublicData(map1, 100, new LinkedList<>());
    privdata1 = new PrivateData(playerHand1);
    dag1 = new dag();
    Entry<Place, TileObject> entry11 = new SimpleEntry<>(new Place(2, -1), yellowStar);
    move1 = new PlaceMove(List.of(entry11));
    assertEquals(dag1.applyStrategy(pubdata1, privdata1), move1);

    // second state
    map2 = new map(map2Board);
    pubdata2 = new PublicData(map2, 5, new LinkedList<>());
    privdata2 = new PrivateData(playerHand2);
    dag2 = new dag();
    Entry<Place, TileObject> entry21 = new SimpleEntry<>(new Place(-100, -6), yellowStar);
    move2 = new PlaceMove(List.of(entry21));
    assertEquals(dag2.applyStrategy(pubdata2, privdata2), move2);

    // third state, no tiles available to place so return exchangeMove
    map3 = new map(map3Board);
    pubdata3 = new PublicData(map3, 4, new LinkedList<>());
    privdata3 = new PrivateData(playerHand3);
    dag3 = new dag();
    move3 = new ExchangeMove();
    assertEquals(dag3.applyStrategy(pubdata3, privdata3), move3);

    // third state, no tiles available to place and not enough referee tiles
    map3 = new map(map3Board);
    pubdata3 = new PublicData(map3, 3, new LinkedList<>());
    privdata3 = new PrivateData(playerHand3);
    dag3 = new dag();
    move3 = new PassMove();
    assertEquals(dag3.applyStrategy(pubdata3, privdata3), move3);

    // fourth state
    map4 = new map(map4Board);
    pubdata4 = new PublicData(map4, 0, new LinkedList<>());
    privdata4 = new PrivateData(playerHand4);
    dag4 = new dag();
    Entry<Place, TileObject> entry41 = new SimpleEntry<>(new Place(-1, -1), blue8star);
    move4 = new PlaceMove(List.of(entry41));
    assertEquals(dag4.applyStrategy(pubdata4, privdata4), move4);
  }

  @Test
  public void testApplyLdasgStrategy() {
    // first state
    map1 = new map(map1Board);
    pubdata1 = new PublicData(map1, 100, new LinkedList<>());
    privdata1 = new PrivateData(playerHand1);
    ldasg1 = new ldasg();
    Entry<Place, TileObject> entry11 = new SimpleEntry<>(new Place(2, -1), yellowStar);
    move1 = new PlaceMove(List.of(entry11));
    assertEquals(ldasg1.applyStrategy(pubdata1, privdata1), move1);

    // second state
    map2 = new map(map2Board);
    pubdata2 = new PublicData(map2, 5, new LinkedList<>());
    privdata2 = new PrivateData(playerHand2);
    ldasg2 = new ldasg();
    Entry<Place, TileObject> entry21 = new SimpleEntry<>(new Place(-100, -6), yellowStar);
    move2 = new PlaceMove(List.of(entry21));
    assertEquals(ldasg2.applyStrategy(pubdata2, privdata2), move2);

    // third state, no tiles available to place so return exchangeMove
    map3 = new map(map3Board);
    pubdata3 = new PublicData(map3, 4, new LinkedList<>());
    privdata3 = new PrivateData(playerHand3);
    ldasg3 = new ldasg();
    move3 = new ExchangeMove();
    assertEquals(ldasg3.applyStrategy(pubdata3, privdata3), move3);

    // third state, no tiles available to place and not enough referee tiles
    map3 = new map(map3Board);
    pubdata3 = new PublicData(map3, 3, new LinkedList<>());
    privdata3 = new PrivateData(playerHand3);
    ldasg3 = new ldasg();
    assertEquals(ldasg3.applyStrategy(pubdata3, privdata3), new PassMove());

    // fourth state
    map4 = new map(map4Board);
    pubdata4 = new PublicData(map4, 0, new LinkedList<>());
    privdata4 = new PrivateData(playerHand4);
    ldasg4 = new ldasg();
    Entry<Place, TileObject> entry41 = new SimpleEntry<>(new Place(2, 0), blue8star);
    move4 = new PlaceMove(List.of(entry41));
    assertEquals(ldasg4.applyStrategy(pubdata4, privdata4), move4);
  }

  @Test
  public void testApplyDagStrategyIterator() {
    // first state
    map1 = new map(map1Board);
    pubdata1 = new PublicData(map1, 100, new LinkedList<>());
    privdata1 = new PrivateData(playerHand1);
    dag1 = new dag();
    Entry<Place, TileObject> entry11 = new SimpleEntry<>(new Place(2, -1), yellowStar);
    Entry<Place, TileObject> entry12 = new SimpleEntry<>(new Place(0, -1), orangeCircle);
    assertEquals(dag1.applyStrategyIterator(pubdata1, privdata1),
            new PlaceMove(List.of(entry11, entry12)));

    initData();
    // second state
    map2 = new map(map2Board);
    pubdata2 = new PublicData(map2, 5, new LinkedList<>());
    privdata2 = new PrivateData(playerHand2);
    dag2 = new dag();
    Entry<Place, TileObject> entry21 = new SimpleEntry<>(new Place(-100, -6), yellowStar);
    Entry<Place, TileObject> entry22 = new SimpleEntry<>(new Place(-100, -7), orangeStar);
    Entry<Place, TileObject> entry23 = new SimpleEntry<>(new Place(-100, -8), orange8star);
    Entry<Place, TileObject> entry24 = new SimpleEntry<>(new Place(-100, -9), blue8star);
    assertEquals(dag2.applyStrategyIterator(pubdata2, privdata2),
            new PlaceMove(List.of(entry21, entry22, entry23, entry24)));

    initData();
    // third state, no tiles available to place so return exchangeMove
    map3 = new map(map3Board);
    pubdata3 = new PublicData(map3, 4, new LinkedList<>());
    privdata3 = new PrivateData(playerHand3);
    dag3 = new dag();
    move3 = new ExchangeMove();
    assertEquals(dag3.applyStrategyIterator(pubdata3, privdata3), move3);

    initData();
    // third state, no tiles available to place and not enough referee tiles
    map3 = new map(map3Board);
    pubdata3 = new PublicData(map3, 3, new LinkedList<>());
    privdata3 = new PrivateData(playerHand3);
    dag3 = new dag();
    move3 = new PassMove();
    assertEquals(dag3.applyStrategyIterator(pubdata3, privdata3), move3);

    initData();
    // fourth state
    map4 = new map(map4Board);
    pubdata4 = new PublicData(map4, 0, new LinkedList<>());
    privdata4 = new PrivateData(playerHand4);
    dag4 = new dag();
    Entry<Place, TileObject> entry41 = new SimpleEntry<>(new Place(-1, -1), blue8star);
    assertEquals(dag4.applyStrategyIterator(pubdata4, privdata4), new PlaceMove(List.of(entry41)));
  }

  @Test
  public void testApplyLdasgStrategyIterator() {
    // first state
    map1 = new map(map1Board);
    pubdata1 = new PublicData(map1, 100, new LinkedList<>());
    privdata1 = new PrivateData(playerHand1);
    ldasg1 = new ldasg();
    Entry<Place, TileObject> entry11 = new SimpleEntry<>(new Place(2, -1), yellowStar);
    Entry<Place, TileObject> entry12 = new SimpleEntry<>(new Place(0, -1), orangeCircle);
    assertEquals(ldasg1.applyStrategyIterator(pubdata1, privdata1),
            new PlaceMove(List.of(entry11, entry12)));

    // second state
    initData();
    map2 = new map(map2Board);
    pubdata2 = new PublicData(map2, 5, new LinkedList<>());
    privdata2 = new PrivateData(playerHand2);
    ldasg2 = new ldasg();
    Entry<Place, TileObject> entry21 = new SimpleEntry<>(new Place(-100, -6), yellowStar);
    Entry<Place, TileObject> entry22 = new SimpleEntry<>(new Place(-100, -7), orangeStar);
    Entry<Place, TileObject> entry23 = new SimpleEntry<>(new Place(-100, -8), orange8star);
    Entry<Place, TileObject> entry24 = new SimpleEntry<>(new Place(-100, -9), blue8star);
    assertEquals(ldasg2.applyStrategyIterator(pubdata2, privdata2), new PlaceMove(List.of(entry21, entry22, entry23, entry24)));

    initData();
    // third state, no tiles available to place so return exchangeMove
    map3 = new map(map3Board);
    pubdata3 = new PublicData(map3, 4, new LinkedList<>());
    privdata3 = new PrivateData(playerHand3);
    ldasg3 = new ldasg();
    assertEquals(ldasg3.applyStrategyIterator(pubdata3, privdata3), new ExchangeMove());

    initData();
    // third state, no tiles available to place and not enough referee tiles
    map3 = new map(map3Board);
    pubdata3 = new PublicData(map3, 3, new LinkedList<>());
    privdata3 = new PrivateData(playerHand3);
    ldasg3 = new ldasg();
    assertEquals(ldasg3.applyStrategyIterator(pubdata3, privdata3), new PassMove());

    initData();
    // fourth state
    map4 = new map(map4Board);
    pubdata4 = new PublicData(map4, 0, new LinkedList<>());
    privdata4 = new PrivateData(playerHand4);
    ldasg4 = new ldasg();
    Entry<Place, TileObject> entry41 = new SimpleEntry<>(new Place(2, 0), blue8star);
    assertEquals(ldasg4.applyStrategyIterator(pubdata4, privdata4), new PlaceMove(List.of(entry41)));
  }

}
