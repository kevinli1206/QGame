package Common;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;

import Common.place.Place;
import Common.players.Player;
import Common.players.PlayersInfo;
import Common.tiles.TileColor;
import Common.tiles.TileObject;
import Common.tiles.TileShape;

public class xscoreMain {

  /**
   * Parses the given JSON string and prints its values to the terminal for this script
   *
   * @param args a valid JSON string
   */
  public static void main(String[] args) {
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      JsonFactory jsonFactory = new JsonFactory();
      jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
      jsonFactory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
      ObjectMapper om = new ObjectMapper(jsonFactory);

      JsonParser jp = jsonFactory.createParser(reader);
      jp.setCodec(om);
      jp.nextToken();

      JsonNode mapNode = jp.readValueAsTree();
      IMap map = new map(JSONParsingUtils.parseBoard(mapNode));
      // dummy player and dummy hand
      TileObject to = new TileObject(TileShape.CIRCLE, TileColor.BLUE);
      Player p = new Player(0, new ArrayList<>(List.of(to)));
      PlayersInfo playersInfo = new PlayersInfo(new LinkedList<>(List.of(p)));
      IGameState gamestate = new game_state(map, playersInfo, 100);

      jp.nextToken();
      JsonNode placementsNode = jp.readValueAsTree();
      List<Entry<Place, TileObject>> placements = JSONParsingUtils.parsePlacements(placementsNode);

      gamestate.scoreTurn("", placements);
      System.out.println(gamestate.getPublicData().getPublicPlayerInfo().peek().getValue());

    } catch (JsonProcessingException e) {
      System.out.println("Malformed JSON string with exception: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("Error reading input with exception: " + e.getMessage());
    }
  }
}
