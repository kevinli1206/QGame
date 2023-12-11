package Common;

import static Common.JSONParsingUtils.getClientOrServerConfigFromJSON;
import static Common.JSONParsingUtils.getJsonParserFromInputStream;
import static Common.JSONParsingUtils.getServerTriesFromServerConfig;
import static Common.JSONParsingUtils.getServerWaitFromServerConfig;
import static Common.JSONParsingUtils.getServerWaitForSignupFromServerConfig;
import static Common.JSONParsingUtils.getQuietFromServerConfig;
import static Common.JSONParsingUtils.getRefConfigFromServerConfig;

import Referee.RefereeConfig;
import Server.ServerConfig;
import Server.server;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

/**
 * Script to run our server
 */
public class xserverMain {

  public static void main(String[] args) {
    try {
      JsonParser jsonParser = getJsonParserFromInputStream(System.in);
      int portNumber = Integer.parseInt(args[0]);
      JsonNode serverConfigJSON = getClientOrServerConfigFromJSON(jsonParser);
      int serverTries = getServerTriesFromServerConfig(serverConfigJSON);
      int serverWait = getServerWaitFromServerConfig(serverConfigJSON);
      int waitForSignup = getServerWaitForSignupFromServerConfig(serverConfigJSON);
      boolean quiet = getQuietFromServerConfig(serverConfigJSON);
      RefereeConfig refereeConfig = getRefConfigFromServerConfig(serverConfigJSON);
      ServerConfig serverConfig = new ServerConfig(portNumber, serverTries, serverWait, waitForSignup, quiet, refereeConfig);
      server s = serverConfig.buildServer();
      Entry<List<String>, List<String>> winnersAndKickedPlayers
          = s.runFromExistingState();
      Collections.sort(winnersAndKickedPlayers.getKey());
      System.out.println(JSONParsingUtils.endGameOutputToArrayNode(winnersAndKickedPlayers));
      s.closeServer();
    } catch (IOException e) {
      System.err.println("server exited");
    }

  }
}
