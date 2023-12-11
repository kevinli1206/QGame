package Common;

import Player.IPlayer;
import Referee.IReferee;
import Referee.observer;
import Referee.referee;
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

import static Common.JSONParsingUtils.*;

/**
 * xgames-with-observer-with-observer testing script
 */
public class xgamesWithObserverMain {
  /**
   * Parses the given JSON String and prints its output to the terminal
   * @param args a valid json string
   */
  public static void main(String[] args) {
    try {
      JsonParser jsonParser = getJsonParserFromInputStream(System.in);
      IGameState gameState = getStateFromJState(jsonParser);
      List<IPlayer> players = getPlayersFromJActorSpec(jsonParser);

      IReferee ref;
      if (args.length > 0 && args[0].equals("-show")) {
        ref = new referee(List.of(new observer()));
      }
      else {
        ref = new referee();
      }
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
