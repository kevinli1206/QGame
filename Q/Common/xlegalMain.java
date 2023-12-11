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
import java.util.List;
import java.util.Map.Entry;
import Common.place.Place;
import Common.tiles.TileObject;

public class xlegalMain {

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

      JsonNode pubNode = jp.readValueAsTree();
      IGameState gamestate = JSONParsingUtils.parseGameState(pubNode);

      jp.nextToken();
      JsonNode placementsNode = jp.readValueAsTree();
      List<Entry<Place, TileObject>> placements = JSONParsingUtils.parsePlacements(placementsNode);

      try {
        gamestate.runPlaceTurn("", placements);
        System.out.println(JSONParsingUtils.mapToArrayNode(
                gamestate.getPublicData().getBoard().getMapState()).toString());
      }
      catch (Exception e) {
        System.out.println("false");
      }

    } catch (JsonProcessingException e) {
      System.out.println("Malformed JSON string with exception: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("Error reading input with exception: " + e.getMessage());
    }

  }

}
