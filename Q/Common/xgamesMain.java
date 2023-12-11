package Common;

import Referee.observer;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import Player.IPlayer;
import Referee.IReferee;
import Referee.referee;


public class xgamesMain {

  /**
   * Parses the given JSON String and prints its output to the terminal
   * @param args a valid json string
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

      JsonNode stateNode = jp.readValueAsTree();
      IGameState gameState = JSONParsingUtils.parseState(stateNode);
      jp.nextToken();
      JsonNode jActorsNode = jp.readValueAsTree();
      List<IPlayer> players = JSONParsingUtils.parseJActors(jActorsNode);
      IReferee ref = new referee();
      Entry<List<String>, List<String>> winnersAndKickedPlayers
              = ref.runExistingGame(players, gameState);
      Collections.sort(winnersAndKickedPlayers.getKey());
      System.out.println(JSONParsingUtils.endGameOutputToArrayNode(
              winnersAndKickedPlayers));
    } catch (JsonProcessingException e) {
      System.out.println("Malformed JSON string with exception: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("Error reading input with exception: " + e.getMessage());
    }
  }

}
