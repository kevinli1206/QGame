package Common;
import static Common.JSONParsingUtils.getJsonParserFromInputStream;
import static Common.JSONParsingUtils.getStateFromJState;
import static Common.JSONParsingUtils.getPlayersFromJActorSpecB;

import Player.IPlayer;
import Referee.IReferee;
import Referee.observer;
import Referee.referee;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

public class xbaddiesMain {

  public static void main(String[] args) {
    try {
      JsonParser jsonParser = getJsonParserFromInputStream(System.in);
      IGameState gameState = getStateFromJState(jsonParser);
      List<IPlayer> players = getPlayersFromJActorSpecB(jsonParser);

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
