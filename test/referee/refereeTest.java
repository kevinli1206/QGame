package referee;

import Common.JSONParsingUtils;
import Player.MoveType.ExchangeMove;
import Player.MoveType.PassMove;
import Player.MoveType.PlaceMove;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.Before;
import org.junit.Test;

import static Common.JSONParsingUtils.parseBooleanFromParser;
import static Common.JSONParsingUtils.parseHandFromParser;
import static Common.JSONParsingUtils.parseMethodName;
import static Common.JSONParsingUtils.parseMove;
import static Common.JSONParsingUtils.parsePublicDataFromParser;
import static org.junit.Assert.*;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import Common.IGameState;
import Common.IMap;
import Common.game_state;
import Common.map;
import Common.place.Place;
import Common.players.Player;
import Common.players.PlayersInfo;
import Common.tiles.TileColor;
import Common.tiles.TileObject;
import Common.tiles.TileShape;
import Player.IPlayer;
import Player.player;
import Player.*;
import Player.ExceptionPlayer;
import Referee.referee;

/**
 * To test methods in the referee class.
 */
public class refereeTest {

  IPlayer p1;
  IPlayer p2;
  IPlayer p3;
  IPlayer p4;
  IPlayer p5;
  IPlayer p6;
  IPlayer p7;

  IMap map1;
  IMap map2;
  IMap map3;
  IMap map4;

  IGameState gameState1;
  IGameState gameState2;
  IGameState gameState3;
  IGameState gameState4;
  IGameState gameState5;

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
  List<TileObject> playerHand6;
  List<TileObject> playerHand7;

  Player gameStatePlayer1;
  Player gameStatePlayer2;
  Player gameStatePlayer3;
  Player gameStatePlayer4;
  Player gameStatePlayer5;
  Player gameStatePlayer6;
  Player gameStatePlayer7;

  referee ref;

  @Before
  public void initData() {
    p1 = new player("a", new dag());
    p2 = new player("b", new dag());
    p3 = new player("c", new ldasg());
    p4 = new ExceptionPlayer("setup", "f", new dag());
    p5 = new ExceptionPlayer("new-tiles", "g", new ldasg());
    p6 = new player("d", new ldasg());
    p7 = new player("e", new ldasg());
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
    playerHand6 = new ArrayList<>();
    playerHand6.add(orangeClover);
    playerHand7 = new ArrayList<>();
    playerHand7.add(orangeClover);
    map4Board = new HashMap<>();
    map4Board.put(new Place(0, 0), orangeSquare);
    map4Board.put(new Place(1, 1), yellowStar);
    map4Board.put(new Place(2, 1), orange8star);
    map4Board.put(new Place(-1, 0), orange8star);
    map4Board.put(new Place(-1, 1), greenDiamond);
    map4 = new map(map4Board);
    gameStatePlayer1 = new Player("a", 0, new ArrayList<>(playerHand1));
    gameStatePlayer2 = new Player("b", 0, new ArrayList<>(playerHand2));
    gameStatePlayer3 = new Player("c", 0, new ArrayList<>(playerHand3));
    gameStatePlayer4 = new Player("f", 0, new ArrayList<>(playerHand4));
    gameStatePlayer5 = new Player("g", 0, new ArrayList<>(playerHand4));
    gameStatePlayer6 = new Player("d", 0, new ArrayList<>(playerHand6));
    gameStatePlayer7 = new Player("e", 0, new ArrayList<>(playerHand7));
    Queue<Player> gamePlayers1 = new LinkedList<>();
    gamePlayers1.add(gameStatePlayer4);
    gamePlayers1.add(gameStatePlayer1);
    gamePlayers1.add(gameStatePlayer2);
    gamePlayers1.add(gameStatePlayer3);
    gameState1 = new game_state(map1, new PlayersInfo(gamePlayers1), 10);
    Queue<Player> gamePlayers2 = new LinkedList<>();
    gamePlayers2.add(gameStatePlayer4);
    gamePlayers2.add(gameStatePlayer5);
    gameState2 = new game_state(map2, new PlayersInfo(gamePlayers2), 2);
    Queue<Player> gamePlayers3 = new LinkedList<>();
    gamePlayers3.add(gameStatePlayer6);
    gamePlayers3.add(gameStatePlayer7);
    gameState3 = new game_state(map3, new PlayersInfo(gamePlayers3), 5);
    gameState4 = new game_state(map3, new PlayersInfo(gamePlayers3), 0);
    Queue<Player> gamePlayers4 = new LinkedList<>();
    gamePlayers4.add(gameStatePlayer1);
    gamePlayers4.add(gameStatePlayer2);
    gamePlayers4.add(gameStatePlayer6);
    gamePlayers4.add(gameStatePlayer7);
    gameState5 = new game_state(map1, new PlayersInfo(gamePlayers4), 100);
    ref = new referee();
  }

  @Test
  public void testRunGamePlaceAllTiles() {
    // player placed all tiles and some players were kicked
    List<String> winners1 = new ArrayList<>(Collections.singletonList(p2.name()));
    List<String> losers1 = new ArrayList<>(Collections.singletonList(p4.name()));
    Entry<List<String>, List<String>> gameResults1 = new SimpleEntry<>(winners1, losers1);
    assertEquals(ref.runExistingGame(new ArrayList<>(List.of(p4, p1, p2, p3)), gameState1), gameResults1);
  }

  @Test
  public void testRunGameAllPlayersKicked() {
    // all players get kicked out
    List<String> winners2 = new ArrayList<>();
    List<String> losers2 = new ArrayList<>(Arrays.asList(p4.name(), p5.name()));
    Entry<List<String>, List<String>> gameResults2 = new SimpleEntry<>(winners2, losers2);
    assertEquals(ref.runExistingGame(new ArrayList<>(List.of(p4, p5)), gameState2), gameResults2);
  }

  @Test
  public void testRunGameAllPlayersExchanged() {
    // all players exchanged in a round
    List<String> winners3 = new ArrayList<>(Arrays.asList(p6.name(), p7.name()));
    List<String> losers3 = new ArrayList<>();
    Entry<List<String>, List<String>> gameResults3 = new SimpleEntry<>(winners3, losers3);
    assertEquals(ref.runExistingGame(new ArrayList<>(List.of(p6, p7)), gameState3), gameResults3);
  }

  @Test
  public void testRunGameAllPlayersPassed() {
    // all players passed in a round
    List<String> winners4 = new ArrayList<>(Arrays.asList(p6.name(), p7.name()));
    List<String> losers4 = new ArrayList<>();
    Entry<List<String>, List<String>> gameResults4 = new SimpleEntry<>(winners4, losers4);
    assertEquals(ref.runExistingGame(new ArrayList<>(List.of(p6, p7)), gameState4), gameResults4);
  }

  @Test
  public void testRunGameNoPlayersKicked() {
    // no players get kicked out
    List<String> winners5 = new ArrayList<>(Collections.singletonList(p2.name()));
    List<String> losers5 = new ArrayList<>();
    Entry<List<String>, List<String>> gameResults5 = new SimpleEntry<>(winners5, losers5);
    assertEquals(ref.runExistingGame(new ArrayList<>(List.of(p1, p2, p6, p7)), gameState5), gameResults5);
  }

//  @Test
//  public void randomTest() {
//    System.out.println(parsePublicDataFromParser(JSONParsingUtils.parseSetUp(gameState1.getPublicData(), playerHand1).get(1).get(0)));
//    System.out.println(parsePublicDataFromParser(JSONParsingUtils.parseTakeTurn(gameState1.getPublicData(), playerHand1).get(1).get(0)));
//    System.out.println(parseHandFromParser(JSONParsingUtils.parseNewTiles(playerHand1).get(1).get(0)));
//    System.out.println(parseBooleanFromParser(JSONParsingUtils.parseWin(true).get(1).get(0)));
//    System.out.println(parseHandFromParser(JSONParsingUtils.parseNewTiles(playerHand1).get(1).get(0)));
//    JsonNode node = new TextNode("void");
//    System.out.println(node.asText().equals("void"));
//    System.out.println(parseMove(new PassMove()));
//    System.out.println(parseMove(new ExchangeMove()));
//    System.out.println(parseMove(new PlaceMove(List.of(new SimpleEntry<>(new Place(0, 0), yellowStar)))));
//  }
}
