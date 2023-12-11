package Server;

import static Common.JSONParsingUtils.*;

import Common.IMap;
import Common.JSONParsingUtils;
import Common.data.PublicData;
import Common.tiles.TileObject;
import Player.IPlayer;
import Player.MoveType.IMove;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import Common.map;

/**
 * Represents a proxy player in this server
 */
public class player implements IPlayer {

  private final String name;
  private final IMap map;
  private List<TileObject> hand;
  private final PrintWriter sendToClient;
  private final BufferedReader fromClient;
  private final JsonParser parser;
  private final ObjectMapper om;

  /**
   * Player constructor with all parameters
   * @param name the player's name
   * @param map the player's map
   * @param hand the player's hand
   * @param out the output stream
   * @param in the input stream
   */
  public player(String name, IMap map, List<TileObject> hand,
      OutputStream out, InputStreamReader in) throws IOException {
    this.name = name;
    this.map = map;
    this.hand = hand;
    this.sendToClient = new PrintWriter(out);
    this.fromClient = new BufferedReader(in);
    this.parser = getJsonParserFromBufferedReader(fromClient);
    JsonFactory jsonFactory = new JsonFactory();
    jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
    jsonFactory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
    this.om = new ObjectMapper(jsonFactory);
  }

  /**
   * Player constructor with name and port
   * @param name the player's name
   * @param out the output stream
   * @param in the input stream
   */
  public player(String name, OutputStream out, InputStreamReader in) throws IOException {
    this.name = name;
    this.map = new map();
    this.hand = new ArrayList<>();
    this.sendToClient = new PrintWriter(out, true);
    this.fromClient = new BufferedReader(in);
    this.parser = getJsonParserFromBufferedReader(fromClient);
    JsonFactory jsonFactory = new JsonFactory();
    jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
    jsonFactory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
    this.om = new ObjectMapper(jsonFactory);
  }


  @Override
  public String name() {
    return this.name;
  }

  @Override
  public void setUp(PublicData publicData, List<TileObject> hand) {
    ArrayNode parseSetUp = JSONParsingUtils.parseSetUp(publicData, hand);
    this.hand = hand;
    try {
      this.writeAndReceiveVoid(parseSetUp);
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  /**
   * Write a message to this player's writer and expect "void" back from the reader
   * @param messageToWrite the json message to write as a string
   * @throws IOException if writing or reading failed
   */
  private void writeAndReceiveVoid(JsonNode messageToWrite) throws IOException {
    this.om.writeValue(sendToClient, messageToWrite);
    String result = parseVoid(this.parser);
    if (!result.equals("void")) {
      throw new RuntimeException();
    }
  }

  @Override
  public IMove takeTurn(PublicData publicData) {
    ArrayNode parseTakeTurn = JSONParsingUtils.parseTakeTurn(publicData, this.hand);
    try {
      this.om.writeValue(sendToClient, parseTakeTurn);
      return parseJChoice(this.parser);
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  @Override
  public void newTiles(List<TileObject> newHand) {
    JsonNode parseNewTilesArgs = JSONParsingUtils.parseNewTiles(newHand);
    this.hand = newHand;
    try {
      this.writeAndReceiveVoid(parseNewTilesArgs);
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  @Override
  public void win(boolean w) {
    JsonNode parseWinArgs = JSONParsingUtils.parseWin(w);
    try {
      this.writeAndReceiveVoid(parseWinArgs);
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  @Override
  public IMap getMap() {
    return this.map;
  }

  @Override
  public List<TileObject> getHand() {
    return this.hand;
  }
}
