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

import Player.MoveType.ExchangeMove;
import Player.MoveType.IMove;
import Player.MoveType.PassMove;
import Player.MoveType.PlaceMove;
import Player.dag;
import Player.ldasg;
import Player.strategy;

public class xstrategyMain {

  /**
   * Parses the given JSON string and prints its values to the terminal for this script
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

      JsonNode strategyNode = jp.readValueAsTree();
      String strat = JSONParsingUtils.parseStrategy(strategyNode);
      jp.nextToken();
      JsonNode pubNode = jp.readValueAsTree();
      IGameState gamestate = JSONParsingUtils.parseGameState(pubNode);
      if (strat.equals("dag")) {
        strategy dagStrategy = new dag();
        printStrategyOutput(dagStrategy, gamestate);
      }
      else {
        strategy ldasgStrategy = new ldasg();
        printStrategyOutput(ldasgStrategy, gamestate);
      }
    } catch (JsonProcessingException e) {
      System.out.println("Malformed JSON string with exception: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("Error reading input with exception: " + e.getMessage());
    }
  }

  /**
   * Print the output of the given strategy for this script
   */
  public static void printStrategyOutput(strategy s, IGameState gamestate) {
    IMove move = s.applyStrategy(gamestate.getPublicData(), gamestate.getPrivateData(""));
    if (move.equals(new PassMove())) {
      System.out.println("\"pass\"");
    }
    else if (move.equals(new ExchangeMove())) {
      System.out.println("\"replace\"");
    }
    else {
      System.out.println(JSONParsingUtils.placeMoveToObjectNode(move));
    }
  }
}
