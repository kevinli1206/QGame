package player;
import Common.*;
import Common.data.PublicData;
import Common.place.Place;
import Common.tiles.TileColor;
import Common.tiles.TileObject;
import Common.tiles.TileShape;
import Player.CheatingPlayer;
import Player.MoveType.ExchangeMove;
import Player.MoveType.IMove;
import Player.MoveType.PassMove;
import Player.ldasg;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NotALineUnitTests {
    Map<Place, TileObject> tileMap = new HashMap<>();
    IMap map;
    CheatingPlayer cheatingPlayer;
    List<TileObject> playersHand;
    PublicData publicData;
    Map.Entry<String, Integer> nameToScoreMap;
    Queue<Map.Entry<String, Integer>> queue;
    @Before
    public void init() {
        tileMap.put(new Place(0,0), new TileObject(TileShape.CIRCLE, TileColor.RED));
        map = new map(tileMap);
        playersHand = new ArrayList<>();
        playersHand.add(new TileObject(TileShape.STAR, TileColor.RED));
        playersHand.add(new TileObject(TileShape.DIAMOND, TileColor.PURPLE));
        playersHand.add(new TileObject(TileShape.DIAMOND, TileColor.PURPLE));
        cheatingPlayer = new CheatingPlayer("not-a-line", "player1", new ldasg());
        cheatingPlayer.newTiles(playersHand);
        nameToScoreMap = new AbstractMap.SimpleEntry<>("player1", 0);
        queue = new ArrayDeque<>();
        queue.add(nameToScoreMap);
        publicData = new PublicData(map, 0,queue);
    }

//     //isn't able to cheat so calls super
    @Test
    public void test1() {
        List<TileObject> playersHandCopy = new ArrayList<>(playersHand);
    }

    // is able to cheat
    @Test
    public void test2() {
        List<TileObject> playersHandCopy = new ArrayList<>();
        playersHandCopy.add(new TileObject(TileShape.DIAMOND, TileColor.PURPLE));
        playersHandCopy.add(new TileObject(TileShape.STAR, TileColor.PURPLE));
        playersHandCopy.add(new TileObject(TileShape.STAR, TileColor.RED));
        cheatingPlayer.newTiles(playersHandCopy);
        IMove move = cheatingPlayer.findNotALine(publicData);
    }

    @Test
    // scenario in 6-in.json
    public void test3() {
        tileMap = new HashMap<>();
        tileMap.put(new Place(0,0), new TileObject(TileShape.CLOVER, TileColor.RED));
        tileMap.put(new Place(0,1), new TileObject(TileShape.DIAMOND, TileColor.RED));
        tileMap.put(new Place(0,2), new TileObject(TileShape.CIRCLE, TileColor.RED));
        tileMap.put(new Place(0,-1), new TileObject(TileShape.CLOVER, TileColor.PURPLE));
        tileMap.put(new Place(0,-2), new TileObject(TileShape.CLOVER, TileColor.PURPLE));
        List<TileObject> playersHandCopy = new ArrayList<>();
        playersHandCopy.add(new TileObject(TileShape.EIGHT_STAR, TileColor.GREEN));
        playersHandCopy.add(new TileObject(TileShape.SQUARE, TileColor.BLUE));
        playersHandCopy.add(new TileObject(TileShape.DIAMOND, TileColor.YELLOW));
        playersHandCopy.add(new TileObject(TileShape.CIRCLE, TileColor.ORANGE));
        playersHandCopy.add(new TileObject(TileShape.CIRCLE, TileColor.YELLOW));
        playersHandCopy.add(new TileObject(TileShape.CLOVER, TileColor.PURPLE));
        cheatingPlayer.newTiles(playersHandCopy);
        publicData = new PublicData(new map(tileMap), 0,queue);
        IMove move = cheatingPlayer.findNotALine(publicData);
        for (Map.Entry<Place, TileObject> entry: move.getPlacements()) {
            System.out.print(entry.getValue().getShape());
            System.out.print(entry.getValue().getColor() + "\n");
            System.out.print(entry.getKey().getX() + ", ");
            System.out.print(entry.getKey().getY() + "\n");
        }
    }

    @Test
    public void test_REVAMP_1() {
        tileMap = new HashMap<>();
        tileMap.put(new Place(0,0), new TileObject(TileShape.EIGHT_STAR, TileColor.GREEN));
        tileMap.put(new Place(1,0), new TileObject(TileShape.CLOVER, TileColor.PURPLE));
        tileMap.put(new Place(0,-1), new TileObject(TileShape.CIRCLE, TileColor.RED));
        tileMap.put(new Place(0,-2), new TileObject(TileShape.SQUARE, TileColor.BLUE));
        tileMap.put(new Place(1,-2), new TileObject(TileShape.CIRCLE, TileColor.PURPLE));
        List<TileObject> playersHandCopy = new ArrayList<>();
        playersHandCopy.add(new TileObject(TileShape.SQUARE, TileColor.RED));
        playersHandCopy.add(new TileObject(TileShape.DIAMOND, TileColor.PURPLE));
        playersHandCopy.add(new TileObject(TileShape.STAR, TileColor.BLUE));
        playersHandCopy.add(new TileObject(TileShape.STAR, TileColor.GREEN));
        playersHandCopy.add(new TileObject(TileShape.STAR, TileColor.PURPLE));
        cheatingPlayer.newTiles(playersHandCopy);
        publicData = new PublicData(new map(tileMap), 0,queue);
        IMove move = cheatingPlayer.findNotALine(publicData);
        for (Map.Entry<Place, TileObject> entry: move.getPlacements()) {
            System.out.print(entry.getValue().getShape());
            System.out.print(entry.getValue().getColor() + "\n");
            System.out.print(entry.getKey().getX() + ", ");
            System.out.print(entry.getKey().getY() + "\n");
        }
        assertTrue(move.getPlacements().size()==4);
    }

    @Test
    public void test_REVAMP_2() {
        tileMap = new HashMap<>();
        tileMap.put(new Place(0,0), new TileObject(TileShape.EIGHT_STAR, TileColor.GREEN));
        tileMap.put(new Place(1,0), new TileObject(TileShape.CLOVER, TileColor.PURPLE));
        tileMap.put(new Place(0,-1), new TileObject(TileShape.CIRCLE, TileColor.RED));
        tileMap.put(new Place(0,-2), new TileObject(TileShape.SQUARE, TileColor.BLUE));
        tileMap.put(new Place(1,-2), new TileObject(TileShape.CIRCLE, TileColor.PURPLE));
        List<TileObject> playersHandCopy = new ArrayList<>();
        playersHandCopy.add(new TileObject(TileShape.STAR, TileColor.BLUE));
        playersHandCopy.add(new TileObject(TileShape.STAR, TileColor.GREEN));
        playersHandCopy.add(new TileObject(TileShape.STAR, TileColor.ORANGE));
        cheatingPlayer.newTiles(playersHandCopy);
        publicData = new PublicData(new map(tileMap), 0,queue);
        IMove move = cheatingPlayer.findNotALine(publicData);
        for (Map.Entry<Place, TileObject> entry: move.getPlacements()) {
            System.out.print(entry.getValue().getShape());
            System.out.print(entry.getValue().getColor() + "\n");
            System.out.print(entry.getKey().getX() + ", ");
            System.out.print(entry.getKey().getY() + "\n");
        }
        assertTrue(move.getPlacements().size()==3);
    }

    @Test
    public void test_REVAMP_3() {
        tileMap = new HashMap<>();
        tileMap.put(new Place(0,0), new TileObject(TileShape.EIGHT_STAR, TileColor.GREEN));
        tileMap.put(new Place(1,0), new TileObject(TileShape.CLOVER, TileColor.PURPLE));
        tileMap.put(new Place(0,1), new TileObject(TileShape.CIRCLE, TileColor.RED));
        tileMap.put(new Place(0,2), new TileObject(TileShape.SQUARE, TileColor.BLUE));
        tileMap.put(new Place(1,2), new TileObject(TileShape.CIRCLE, TileColor.PURPLE));
        List<TileObject> playersHandCopy = new ArrayList<>();
        playersHandCopy.add(new TileObject(TileShape.SQUARE, TileColor.RED));
        playersHandCopy.add(new TileObject(TileShape.DIAMOND, TileColor.PURPLE));
        playersHandCopy.add(new TileObject(TileShape.STAR, TileColor.PURPLE));
        List<TileObject> copyOfPlayersHandsCopy = new ArrayList<>(playersHandCopy);

        cheatingPlayer.newTiles(playersHandCopy);
        publicData = new PublicData(new map(tileMap), 0,queue);
        IMove move = cheatingPlayer.findNotALine(publicData);
        for (Map.Entry<Place, TileObject> entry: move.getPlacements()) {
            System.out.print(entry.getValue().getShape());
            System.out.print(entry.getValue().getColor() + "\n");
            System.out.print(entry.getKey().getX() + ", ");
            System.out.print(entry.getKey().getY() + "\n");
        }
        // oml... super removes the players tiles from the hand!!
        Player.player nonCheatingPlayer = new Player.player("player1", new ldasg());
        nonCheatingPlayer.newTiles(new ArrayList<>(copyOfPlayersHandsCopy));
        // new public data here because super mutates public data!!!!
        IMove noCheatMove = nonCheatingPlayer.takeTurn(new PublicData(new map(tileMap), 0, queue));
        assertEquals(move, noCheatMove);
    }

    @Test
    public void test_REVAMP_4() {
        tileMap = new HashMap<>();
        tileMap.put(new Place(0,0), new TileObject(TileShape.EIGHT_STAR, TileColor.GREEN));
        tileMap.put(new Place(1,0), new TileObject(TileShape.CLOVER, TileColor.PURPLE));
        tileMap.put(new Place(0,1), new TileObject(TileShape.CIRCLE, TileColor.RED));
        tileMap.put(new Place(1,2), new TileObject(TileShape.CIRCLE, TileColor.PURPLE));
        List<TileObject> playersHandCopy = new ArrayList<>();
        playersHandCopy.add(new TileObject(TileShape.STAR, TileColor.GREEN));
        playersHandCopy.add(new TileObject(TileShape.DIAMOND, TileColor.PURPLE));
        playersHandCopy.add(new TileObject(TileShape.CLOVER, TileColor.PURPLE));
        List<TileObject> copyOfPlayersHandsCopy = new ArrayList<>(playersHandCopy);

        cheatingPlayer.newTiles(playersHandCopy);
        publicData = new PublicData(new map(tileMap), 0,queue);
        IMove move = cheatingPlayer.findNotALine(publicData);
        for (Map.Entry<Place, TileObject> entry: move.getPlacements()) {
            System.out.print(entry.getValue().getShape());
            System.out.print(entry.getValue().getColor() + "\n");
            System.out.print(entry.getKey().getX() + ", ");
            System.out.print(entry.getKey().getY() + "\n");
        }
        // oml... super removes the players tiles from the hand!!
        Player.player nonCheatingPlayer = new Player.player("player1", new ldasg());
        nonCheatingPlayer.newTiles(new ArrayList<>(copyOfPlayersHandsCopy));
        // new public data here because super mutates public data!!!!
        IMove noCheatMove = nonCheatingPlayer.takeTurn(new PublicData(new map(tileMap), 0, queue));
        assertEquals(move, noCheatMove);
    }
    @Test
    public void test_REVAMP_5() {
        tileMap = new HashMap<>();
        tileMap.put(new Place(0,0), new TileObject(TileShape.EIGHT_STAR, TileColor.GREEN));
        tileMap.put(new Place(1,0), new TileObject(TileShape.SQUARE, TileColor.PURPLE));
        tileMap.put(new Place(0,1), new TileObject(TileShape.CIRCLE, TileColor.RED));
        tileMap.put(new Place(1,2), new TileObject(TileShape.SQUARE, TileColor.PURPLE));
        List<TileObject> playersHandCopy = new ArrayList<>();
        playersHandCopy.add(new TileObject(TileShape.SQUARE, TileColor.RED));
        playersHandCopy.add(new TileObject(TileShape.SQUARE, TileColor.BLUE));
        playersHandCopy.add(new TileObject(TileShape.DIAMOND, TileColor.BLUE));
        List<TileObject> copyOfPlayersHandsCopy = new ArrayList<>(playersHandCopy);

        cheatingPlayer.newTiles(playersHandCopy);
        publicData = new PublicData(new map(tileMap), 0,queue);
        IMove move = cheatingPlayer.findNotALine(publicData);
        for (Map.Entry<Place, TileObject> entry: move.getPlacements()) {
            System.out.print(entry.getValue().getShape());
            System.out.print(entry.getValue().getColor() + "\n");
            System.out.print(entry.getKey().getX() + ", ");
            System.out.print(entry.getKey().getY() + "\n");
        }
        assertTrue(move.getPlacements().size()==3);
    }
}
