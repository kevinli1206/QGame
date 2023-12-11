package playerState;

import static org.junit.Assert.assertEquals;

import Common.players.Player;
import Common.players.PlayersInfo;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;
import Common.tiles.TileColor;
import Common.tiles.TileObject;
import Common.tiles.TileShape;

/**
 * Test the PlayersInfo class.
 */
public class PlayersInfoTest {

  Player init1;
  Player init2;
  Player setFields1;
  Player setFields2;
  TileObject redStar;
  TileObject blueCircle;
  TileObject yellowClover;
  List<TileObject> tileList;
  Queue<Player> initPlayers;
  PlayersInfo defaultPlayersInfo;
  PlayersInfo setPlayersInfo;

  @Before
  public void initData() {
    init1 = new Player("hi");
    init2 = new Player("bye");
    setFields1 = new Player("hello", 100, new ArrayList<>());
    redStar = new TileObject(TileShape.STAR, TileColor.RED);
    blueCircle = new TileObject(TileShape.CIRCLE, TileColor.BLUE);
    yellowClover = new TileObject(TileShape.CLOVER, TileColor.YELLOW);
    tileList = new ArrayList<>();
    tileList.add(redStar);
    tileList.add(blueCircle);
    tileList.add(yellowClover);
    setFields2 = new Player("goodbye", 20, tileList);
    initPlayers = new LinkedList<>();
    initPlayers.add(init1);
    initPlayers.add(init2);
    initPlayers.add(setFields1);
    initPlayers.add(setFields2);
    defaultPlayersInfo = new PlayersInfo();
    setPlayersInfo = new PlayersInfo(initPlayers);
  }

  @Test
  public void testGetTurnOrder() {
    assertEquals(defaultPlayersInfo.getTurnOrder(), new LinkedList<>());
    Queue<Player> initPlayersCopy = new LinkedList<>(initPlayers);
    assertEquals(setPlayersInfo.getTurnOrder(), initPlayersCopy);
  }

  @Test
  public void testGettersAndSetters() {
    assertEquals(setPlayersInfo.getActivePlayer(), init1);
    setPlayersInfo.setNextTurn();
    assertEquals(setPlayersInfo.getActivePlayer(), init2);
    assertEquals(defaultPlayersInfo.getPublicPlayerInfo().size(), 0);
    defaultPlayersInfo.setPlayers(new ArrayList<>(initPlayers));
    assertEquals(defaultPlayersInfo.getPublicPlayerInfo().size(), 4);
    assertEquals(defaultPlayersInfo.getActivePlayer(), init2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetEmptyActivePlayer() {
    defaultPlayersInfo.getActivePlayer();
  }

  @Test
  public void testGetPlayerHand() {
    assertEquals(setPlayersInfo.getPlayerHand("hi"), init1.getHand());
    assertEquals(setPlayersInfo.getPlayerHand("bye"), init2.getHand());
    assertEquals(setPlayersInfo.getPlayerHand("hello"), setFields1.getHand());
    assertEquals(setPlayersInfo.getPlayerHand("goodbye"), setFields2.getHand());
    defaultPlayersInfo.setPlayers(new ArrayList<>(initPlayers));
    assertEquals(defaultPlayersInfo.getPlayerHand("hi"), init1.getHand());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPlayerHandWithInvalidId() {
    defaultPlayersInfo.getPlayerHand("hey");
  }

  @Test
  public void testGetPublicDataInfo() {
    Queue<Entry<String, Integer>> empty = new LinkedList<>();
    assertEquals(defaultPlayersInfo.getPublicPlayerInfo(), empty);
//    Map<String, Integer> map = new HashMap<>();
//    map.put("hi", 0);
//    map.put("bye", 0);
//    map.put("hello", 100);
//    map.put("goodbye", 20);
    Queue<Entry<String, Integer>> publicData = new LinkedList<>();
    publicData.add(new SimpleEntry<>("hi", 0));
    publicData.add(new SimpleEntry<>("bye", 0));
    publicData.add(new SimpleEntry<>("hello", 100));
    publicData.add(new SimpleEntry<>("goodbye", 20));
    assertEquals(setPlayersInfo.getPublicPlayerInfo(), publicData);
  }

  @Test
  public void testAddTile() {
    assertEquals(setPlayersInfo.getPlayerHand("hi").size(), 0);
    setPlayersInfo.addTile("hi", redStar);
    assertEquals(setPlayersInfo.getPlayerHand("hi").size(), 1);
    assertEquals(setPlayersInfo.getPlayerHand("hi").get(0), redStar);

    assertEquals(setPlayersInfo.getPlayerHand("goodbye").size(), 3);
    setPlayersInfo.addTile("goodbye", yellowClover);
    assertEquals(setPlayersInfo.getPlayerHand("goodbye").size(), 4);
    assertEquals(setPlayersInfo.getPlayerHand("goodbye").get(3), yellowClover);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddTileException() {
    defaultPlayersInfo.addTile("hi", redStar);
  }

  @Test
  public void testRemoveTile() {
    assertEquals(setPlayersInfo.getPlayerHand("goodbye").size(), 3);
    assertEquals(setPlayersInfo.getPlayerHand("goodbye").get(1), blueCircle);
    setPlayersInfo.removeTile("goodbye", blueCircle);
    assertEquals(setPlayersInfo.getPlayerHand("goodbye").size(), 2);
    assertEquals(setPlayersInfo.getPlayerHand("goodbye").get(1), yellowClover);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveTileNoPlayerFoundException() {
    setPlayersInfo.removeTile("hey", blueCircle);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveTileEmptyHandException() {
    setPlayersInfo.removeTile("goodbye", blueCircle);
    setPlayersInfo.removeTile("goodbye", blueCircle);
  }

  @Test
  public void testRemoveAll() {
    assertEquals(setPlayersInfo.removeAll("hi"), new ArrayList<>());
    List<TileObject> initTileList = new ArrayList<>(tileList);
    assertEquals(setPlayersInfo.getPlayerHand("goodbye"), initTileList);
    assertEquals(setPlayersInfo.getPlayerHand("goodbye").size(), 3);
    assertEquals(setPlayersInfo.removeAll("goodbye"), initTileList);
    assertEquals(setPlayersInfo.getPlayerHand("goodbye").size(), 0);
    assertEquals(setPlayersInfo.getPlayerHand("goodbye"), new ArrayList<>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveAllNoPlayerFoundException() {
    defaultPlayersInfo.removeAll("hi");
  }
}
