package Client;

import static Common.JSONParsingUtils.getJsonArrayFromJSON;
import static Common.JSONParsingUtils.getJsonParserFromBufferedReader;

import static Common.JSONParsingUtils.parseMethodName;
import static Common.JSONParsingUtils.parsePublicDataFromParser;
import static Common.JSONParsingUtils.parseHandFromParser;
import static Common.JSONParsingUtils.parseBooleanFromParser;
import static Common.JSONParsingUtils.parseMove;

import Player.IPlayer;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;


import Common.data.PublicData;
import Common.tiles.TileObject;
import Player.MoveType.IMove;
import com.fasterxml.jackson.core.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * To represent a proxy referee to handle communication between the client
 * and the server.
 */
public class referee {

  private final IPlayer player;
  private final PrintWriter sendToServer;
  private final BufferedReader fromServer;
  private final JsonParser parser;
  private final ObjectMapper objectMapper;
  private boolean isGameOver;
  private final boolean quiet;

  /**
   * Referee constructor with a player
   * @param player the given player
   * @param out the output stream
   * @param in the input stream
   * @param quiet whether we want the debug to run
   * @throws IOException if reading/writing failed
   */
  public referee(IPlayer player, OutputStream out, InputStreamReader in, boolean quiet) throws IOException {
    this.player = player;
    this.sendToServer = new PrintWriter(out);
    this.fromServer = new BufferedReader(in);
    this.parser = getJsonParserFromBufferedReader(fromServer);
    JsonFactory jsonFactory = new JsonFactory();
    jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
    jsonFactory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
    this.objectMapper = new ObjectMapper(jsonFactory);
    this.isGameOver = false;
    this.quiet = quiet;
  }

  /**
   * Get input from the reader and write out the player's action back
   */
  public void runCommand() throws IOException {
    try {
      JsonNode node = getJsonArrayFromJSON(this.parser);
      String methodName = this.readMethodName(node.get(0));
      this.runMethod(methodName, node.get(1));
    }
    catch (Exception e) {
      this.printDebug("Invalid message received from server");
      this.isGameOver = true;
    }
  }

  /**
   * Send the name of the client to the server
   */
  public void sendNameToServer() throws IOException {
    this.objectMapper.writeValue(sendToServer, player.name());
  }

  /**
   * reads the raw JSON sent from the proxy player and delegates the deserialization to the
   * appropriate method
   */
  private String readMethodName(JsonNode node) {
    return parseMethodName(node);
  }

  /**
   * Have the player run the given method
   * @param name the name of the method
   * @param node the ARGS ARRAY OF THE METHOD NODE
   */
  private void runMethod(String name, JsonNode node) throws IOException {
    switch (name) {
      case "setup":
        this.deserializeSetUp(node);
        break;
      case "new-tiles":
        this.deserializeNewTiles(node);
        break;
      case "win":
        this.deserializeWin(node);
        break;
      case "take-turn":
        this.deserializeTakeTurn(node);
        break;
      default:
        throw new IllegalArgumentException(name);
    }
  }


  /**
   * creates a proxy referee for the client.
   * Referee sends name to server.
   * Referee starts waiting for input
   */
  public void runProxyReferee() {
    try {
      while (!this.isGameOver()) {
        this.runCommand();
      }
      this.printDebug("End of game shutting down");
    }
    catch (IOException e) {
      this.printDebug("Shutting down proxy referee. Unexpected disconnect with the server.");
    }
  }

  /**
   * Convert the take turn arguments into data to send
   * @param node the given node
   */
  private void deserializeTakeTurn(JsonNode node) throws IOException {
    PublicData publicData = parsePublicDataFromParser(node.get(0));
    IMove move = this.player.takeTurn(publicData);
    JsonNode jChoice = parseMove(move);
    this.objectMapper.writeValue(sendToServer, jChoice);
  }

  /**
   * Convert the win arguments into data to send
   */
  private void deserializeWin(JsonNode node) throws IOException {
    boolean win = parseBooleanFromParser(node.get(0));
    this.player.win(win);
    this.objectMapper.writeValue(sendToServer, "void");
    this.isGameOver = true;
  }

  /**
   * Convert the new-tiles arguments into data to send
   * @param node the given node
   */
  private void deserializeNewTiles(JsonNode node) throws IOException {
      List<TileObject> newHand = parseHandFromParser(node.get(0));
      this.player.newTiles(newHand);
      this.objectMapper.writeValue(sendToServer, "void");
  }

  /**
   * Convert the setup arguments into data to send
   * @param node is the ENTIRE ARRAY NODE
   */
  private void deserializeSetUp(JsonNode node) throws IOException {
    PublicData publicData = parsePublicDataFromParser(node.get(0));
    List<TileObject> hand = parseHandFromParser(node.get(1));
    this.player.setUp(publicData, hand);
    this.objectMapper.writeValue(sendToServer, "void");
  }

  /**
   * @return true if the game is over
   */
  public boolean isGameOver() {
    return this.isGameOver;
  }

  /**
   * Send a debug message for this proxy referee
   * @param message the message to send
   */
  private void printDebug(String message) {
    if (!this.quiet) {
      System.err.println(message);
    }
  }
}
