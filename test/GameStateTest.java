import Common.IGameState;
import Common.data.PrivateData;
import Common.data.PublicData;
import Common.game_state;
import Common.map;
import org.junit.Before;
import org.junit.Test;
import Common.place.Place;
import Common.players.Player;
import Common.players.PlayersInfo;
import Common.tiles.TileColor;
import Common.tiles.TileObject;
import Common.tiles.TileShape;
import java.util.*;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;
import java.util.AbstractMap.SimpleEntry;

public class GameStateTest {

  IGameState gameState1;

  IGameState gameState2;

  IGameState gameState3;

  IGameState gameState4;
  IGameState gameState6;
  IGameState gameState7;
  IGameState gameState9;

  String player1Name;
  String player2Name;
  String player3Name;
  String player4Name;

  String player5Name;

  Place place1;

  Place place2;

  Place place3;

  Place place4;

  TileObject greenDiamond;

  TileObject orangeDiamond;

  TileObject orangeSquare;

  TileObject yellowStar;

  TileObject orangeCircle;
  TileObject orangeStar;
  TileObject orange8star;
  TileObject orangeClover;
  map map5;
  map map6;
  map map7;
  map map9;
  Player p1;
  Player p2;
  Player p3;
  Player p4;
  IGameState gameState5;

  @Before
  public void initData() {
    player1Name = "a";
    player2Name = "b";
    player3Name = "c";
    player4Name = "d";
    player5Name = "e";
    gameState1 = new game_state(100);
    gameState2 = new game_state();
    gameState3 = new game_state();
    gameState4 = new game_state();
    gameState1.initializeGame(
        new ArrayList<>(Arrays.asList(player1Name, player2Name, player3Name, player4Name)));
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
    Map<Place, TileObject> map5Board = new HashMap<>();
    map5Board.put(new Place(0, 0), orangeSquare);
    map5Board.put(new Place(1, 0), orangeClover);
    map5Board.put(new Place(2, 0), orangeStar);
    map5Board.put(new Place(3, 0), orangeCircle);
    map5Board.put(new Place(4, 0), orange8star);
    map5 = new map(map5Board);
    p1 = new Player("a", 0,
            new ArrayList<>(Arrays.asList(
                    orangeSquare, orangeCircle, orangeCircle, orange8star, orangeDiamond, orangeStar)));
    p2 = new Player("b", 0, new ArrayList<>(List.of(orangeSquare)));
    p3 = new Player("c");
    p4 = new Player("d");
    PlayersInfo info = new PlayersInfo(new LinkedList<>(Arrays.asList(p1, p2, p3, p4)));
    gameState5 = new game_state(map5, info, 10, 1);

    Map<Place, TileObject> map7Board = new HashMap<>();
    map7Board.put(new Place(0, 0), orangeSquare);
    map7Board.put(new Place(0, 1), orangeClover);
    map7Board.put(new Place(0, 2), orangeStar);
    map7Board.put(new Place(0, 3), orangeCircle);
    map7Board.put(new Place(0, 4), orange8star);
    map7 = new map(map7Board);
    gameState7 = new game_state(map7, info, 10, 1);

    Map<Place, TileObject> map6Board = new HashMap<>();
    map6Board.put(new Place(0, 0), orangeCircle);
    map6Board.put(new Place(1, 0), orangeSquare);
    map6Board.put(new Place(1, 1), orangeCircle);
    map6 = new map(map6Board);
    gameState6 = new game_state(map6, info, 10, 1);

    Map<Place, TileObject> map9Board = new HashMap<>();
    map9Board.put(new Place(0, 0), orangeCircle);
    map9 = new map(map9Board);
    PlayersInfo info9 = new PlayersInfo(new LinkedList<>(Arrays.asList(p1, p2, p3, p4)));
    gameState9 = new game_state(map9, info9, 10, 1);
  }

  @Test
  public void testInitializeGame() {
    gameState2.initializeGame(new ArrayList<>(Arrays.asList(player1Name, player2Name)));
    PublicData data1 = gameState2.getPublicData();
    PrivateData privData1 = gameState2.getPrivateData("a");
    assertEquals(data1.getTilesLeft(), 1067);
    assertEquals(data1.getPublicPlayerInfo().size(), 2);
    assertEquals(data1.getPublicPlayerInfo().peek().getKey(), "a");
    assertEquals(data1.getBoard().getMapState().size(), 1);
    assertEquals(privData1.getPlayerHand().size(), 6);

    gameState3.initializeGame(
        new ArrayList<>(Arrays.asList(player4Name, player3Name, player2Name, player1Name)));
    PublicData data2 = gameState3.getPublicData();
    PrivateData privData2 = gameState3.getPrivateData("c");
    assertEquals(data2.getTilesLeft(), 1055);
    assertEquals(data2.getPublicPlayerInfo().size(), 4);
    assertEquals(data2.getPublicPlayerInfo().peek().getKey(), "d");
    assertEquals(data2.getBoard().getMapState().size(), 1);
    assertEquals(privData2.getPlayerHand().size(), 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInitializeGameEmptyList() {
    gameState2.initializeGame(new ArrayList<>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInitializeGameTooManyPlayers() {
    gameState2.initializeGame(
        new ArrayList<>(Arrays.asList(player4Name, player3Name, player2Name, player1Name, player5Name)));
  }

  @Test
  public void testGetPublicData() {
    PublicData data = gameState1.getPublicData();
    assertEquals(data.getTilesLeft(), 1055);
    assertEquals(data.getPublicPlayerInfo().size(), 4);
    assertEquals(data.getPublicPlayerInfo().peek().getKey(), "a");
    assertEquals(data.getBoard().getMapState().size(), 1);
  }

  @Test
  public void testGetPrivateData() {
    PrivateData data = gameState1.getPrivateData("a");
    assertEquals(data.getPlayerHand().size(), 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPrivateDataNotInitialized() {
    gameState2.getPrivateData("a");
  }

  @Test
  public void testRunPassTurn() {
    assertEquals(gameState1.getPublicData().getPublicPlayerInfo().peek().getKey(), "a");
    gameState1.runPassTurn("a");
    gameState1.setNextTurn();
    assertEquals(gameState1.getPublicData().getPublicPlayerInfo().peek().getKey(), "b");
    gameState1.runPassTurn("b");
    gameState1.setNextTurn();
    assertEquals(gameState1.getPublicData().getPublicPlayerInfo().peek().getKey(), "c");
    gameState1.runPassTurn("c");
    gameState1.setNextTurn();
    assertEquals(gameState1.getPublicData().getPublicPlayerInfo().peek().getKey(), "d");
    gameState1.runPassTurn("d");
    gameState1.setNextTurn();
    assertEquals(gameState1.getPublicData().getPublicPlayerInfo().peek().getKey(), "a");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRunPassTurnOutOfTurn() {
    gameState1.runPassTurn("b");
  }

  @Test
  public void testRunPlaceTurn() {
    assertEquals(gameState1.getPublicData().getTilesLeft(), 1055);
    List<Entry<Place, TileObject>> firstPlacement = new ArrayList<>();
    firstPlacement.add(Map.entry(place1, greenDiamond));
    firstPlacement.add(Map.entry(place2, greenDiamond));
    gameState1.runPlaceTurn("a", firstPlacement);
    gameState1.addNTilesToHand("a", 2);
    gameState1.setNextTurn();

    PublicData firstData = gameState1.getPublicData();
    Map<Place, TileObject> firstBoard = firstData.getBoard().getMapState();
    assertEquals(firstData.getPublicPlayerInfo().peek().getKey(), "b");
    assertEquals(firstData.getTilesLeft(), 1053);
    assertEquals(firstBoard.get(place1), greenDiamond);
    assertEquals(firstBoard.get(place2), greenDiamond);
    assertFalse(gameState1.getPrivateData("a").getPlayerHand().contains(greenDiamond));

    List<Entry<Place, TileObject>> secondPlacement = new ArrayList<>();
    secondPlacement.add(Map.entry(place3, orangeDiamond));

    gameState1.runPlaceTurn("b", secondPlacement);
    gameState1.addNTilesToHand("b", 1);
    gameState1.setNextTurn();

    PublicData secondData = gameState1.getPublicData();
    Map<Place, TileObject> secondBoard = secondData.getBoard().getMapState();
    assertEquals(secondData.getTilesLeft(), 1052);
    assertEquals(secondBoard.get(place3), orangeDiamond);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testRunPlaceTurnOutOfTurn() {
    List<Entry<Place, TileObject>> placements = new ArrayList<>();
    placements.add(Map.entry(new Place(0, 1),
            new TileObject(TileShape.DIAMOND, TileColor.ORANGE)));
    gameState1.runPlaceTurn("d", placements);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRunPlaceTurnMismatchRowColumn() {
    List<Entry<Place, TileObject>> firstPlacement = new ArrayList<>();
    firstPlacement.add(Map.entry(place1, greenDiamond));
    firstPlacement.add(Map.entry(place4, greenDiamond));
    gameState1.runPlaceTurn("a", firstPlacement);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRunPlaceTurnNoPlacements() {
    List<Entry<Place, TileObject>> firstPlacement = new ArrayList<>();
    gameState1.runPlaceTurn("a", firstPlacement);
  }

  @Test
  public void testRunPlaceTurnRecover() {
    try {
      List<Entry<Place, TileObject>> firstPlacement = new ArrayList<>();
      firstPlacement.add(Map.entry(place1, greenDiamond));
      firstPlacement.add(Map.entry(place2, yellowStar));
      gameState1.runPlaceTurn("a", firstPlacement);
      fail("Exception was not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid Placement");
      PublicData firstData = gameState1.getPublicData();
      Map<Place, TileObject> firstBoard = firstData.getBoard().getMapState();
      assertEquals(firstData.getTilesLeft(), 1055);
      assertFalse(firstBoard.containsKey(place1));
      assertFalse(firstBoard.containsKey(place2));
      assertTrue(gameState1.getPrivateData("a").getPlayerHand().contains(greenDiamond));
    }
  }

  @Test
  public void testRunExchangeTurn() {
    PublicData initialDate = gameState1.getPublicData();
    assertEquals(initialDate.getTilesLeft(), 1055);
    List<TileObject> prevHand = gameState1.getPrivateData("a").getPlayerHand();

    gameState1.runExchangeTurn("a");
    gameState1.setNextTurn();
    assertNotEquals(prevHand, gameState1.getPrivateData("a").getPlayerHand());
    PublicData newData = gameState1.getPublicData();
    assertEquals(newData.getTilesLeft(), 1055);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRunExchangeTurnOutOfTurn() {
    gameState1.runExchangeTurn("b");
  }

  @Test
  public void testIsPlacementValid() {
    assertTrue(gameState1.isPlacementValid(greenDiamond, place1));
    assertTrue(gameState1.isPlacementValid(orangeSquare, place4));
    assertFalse(gameState1.isPlacementValid(yellowStar, place2));
    assertFalse(gameState1.isPlacementValid(greenDiamond, place3));
    assertFalse(gameState1.isPlacementValid(orangeSquare, place3));
  }

  @Test
  public void testScoreTurn() {
    // score one placement
    int score1 = gameState1.getPublicData().getPublicPlayerInfo().peek().getValue();
    assertEquals(score1, 0);
    List<Entry<Place, TileObject>> placements =
            new ArrayList<>(List.of(new SimpleEntry<>(place1, orangeSquare)));
    gameState1.runPlaceTurn("a", placements);
    gameState1.scoreTurn("a", placements);
    score1 = gameState1.getPublicData().getPublicPlayerInfo().peek().getValue();
    assertEquals(score1, 3);

    // contiguous seq in a column
    initData();
    int score2 = gameState1.getPublicData().getPublicPlayerInfo().peek().getValue();
    assertEquals(score2, 0);
    List<Entry<Place, TileObject>> placements2 =
            new ArrayList<>(Arrays.asList(new SimpleEntry<>(place1, orangeSquare),
                    new SimpleEntry<>(new Place(0, -1), greenDiamond)));
    gameState1.runPlaceTurn("a", placements2);
    gameState1.scoreTurn("a", placements2);
    score2 = gameState1.getPublicData().getPublicPlayerInfo().peek().getValue();
    assertEquals(score2, 5);

    // contiguous seq in a row
    initData();
    int score3 = gameState1.getPublicData().getPublicPlayerInfo().peek().getValue();
    assertEquals(score3, 0);
    List<Entry<Place, TileObject>> placements3 =
            new ArrayList<>(Arrays.asList(new SimpleEntry<>(new Place(1, 0), orangeSquare),
                    new SimpleEntry<>(new Place(-1, 0), greenDiamond)));
    gameState1.runPlaceTurn("a", placements3);
    gameState1.scoreTurn("a", placements3);
    score3 = gameState1.getPublicData().getPublicPlayerInfo().peek().getValue();
    assertEquals(score3, 5);

    // contiguous seq in both col and row
    initData();
    int score6 = gameState6.getPublicData().getPublicPlayerInfo().peek().getValue();
    assertEquals(score6, 0);
    List<Entry<Place, TileObject>> placements6 =
            new ArrayList<>(List.of(new SimpleEntry<>(new Place(0, 1), orangeCircle)));
    gameState6.runPlaceTurn("a", placements6);
    gameState6.scoreTurn("a", placements6);
    score6 = gameState6.getPublicData().getPublicPlayerInfo().peek().getValue();
    assertEquals(score6, 5);

    // row Q
    initData();
    int score5 = gameState5.getPublicData().getPublicPlayerInfo().peek().getValue();
    assertEquals(score5, 0);
    List<Entry<Place, TileObject>> placements5 =
            new ArrayList<>(List.of(new SimpleEntry<>(new Place(5, 0), orangeDiamond)));
    gameState5.runPlaceTurn("a", placements5);
    gameState5.scoreTurn("a", placements5);
    score5 = gameState5.getPublicData().getPublicPlayerInfo().peek().getValue();
    assertEquals(score5, 15);

    // col Q
    initData();
    int score7 = gameState7.getPublicData().getPublicPlayerInfo().peek().getValue();
    assertEquals(score7, 0);
    List<Entry<Place, TileObject>> placements7 =
            new ArrayList<>(List.of(new SimpleEntry<>(new Place(0, 5), orangeDiamond)));
    gameState7.runPlaceTurn("a", placements7);
    gameState7.scoreTurn("a", placements7);
    score7 = gameState7.getPublicData().getPublicPlayerInfo().peek().getValue();
    assertEquals(score7, 15);

    // player placing all tiles - and when player has less than 6 tiles in their hand
    initData();
    int score8 = gameState7.getPublicData().getPublicPlayerInfo().peek().getValue();
    assertEquals(score8, 0);
    List<Entry<Place, TileObject>> placements8 =
            new ArrayList<>(Arrays.asList(
                    new SimpleEntry<>(new Place(1, 0), orangeDiamond),
                    new SimpleEntry<>(new Place(1, 1), orangeStar),
                    new SimpleEntry<>(new Place(1, 2), orange8star),
                    new SimpleEntry<>(new Place(1, 3), orangeCircle),
                    new SimpleEntry<>(new Place(1, 4), orangeCircle),
                    new SimpleEntry<>(new Place(1, 5), orangeSquare)));
    gameState7.runPlaceTurn("a", placements8);
    gameState7.scoreTurn("a", placements8);
    score8 = gameState7.getPublicData().getPublicPlayerInfo().peek().getValue();
    assertEquals(score8, 26);

    // player has less than 6 tiles in their hand and places all of them in one turn
    initData();
    gameState9.setNextTurn();
    int score9 = gameState9.getPublicData().getPublicPlayerInfo().peek().getValue();
    assertEquals(score9, 0);
    List<Entry<Place, TileObject>> placements9 =
            new ArrayList<>(List.of(
                new SimpleEntry<>(new Place(0, 1), orangeSquare)));
    gameState9.runPlaceTurn("b", placements9);
    gameState9.scoreTurn("b", placements9);
    score9 = gameState9.getPublicData().getPublicPlayerInfo().peek().getValue();
    assertEquals(score9, 7);
  }

}
