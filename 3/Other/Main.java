import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jsonutils.JSONParsingUtils;
import place.Place;
import tiles.TileObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

  /**
   * Parses the given JSON string and prints its values to the terminal
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
      Map<Place, TileObject> board = JSONParsingUtils.parseBoard(mapNode);
      IMap gameMap = new map(board);

      jp.nextToken();
      JsonNode tileNode = jp.readValueAsTree();
      TileObject tile = JSONParsingUtils.parseTileObject(tileNode);
      Set<Place> validPlacements = gameMap.getValidPlacements(tile);

      List<Place> sortedPlacements = new ArrayList<>(validPlacements);

      sortedPlacements.sort(Comparator.comparingInt(Place::getX).thenComparingInt(Place::getY));

      System.out.println(JSONParsingUtils.placesToArrayNode(sortedPlacements).toString());


    } catch (JsonProcessingException e) {
      System.out.println("Malformed JSON string with exception: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("Error reading input with exception: " + e.getMessage());
    }


  }

}
