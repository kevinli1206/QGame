package Common;

import Client.IClient;
import Client.client;
import Player.IPlayer;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import static Common.JSONParsingUtils.*;


/**
 * Script to run our client
 */
public class xclientMain {

  public static void main(String[] args) {
    try {
      JsonParser jsonParser = getJsonParserFromInputStream(System.in);
      int portNumber =  Integer.parseInt(args[0]);
      JsonNode clientConfig = getClientOrServerConfigFromJSON(jsonParser);
      String host = getHostFromClientConfig(clientConfig);
      int waitTime = getWaitFromClientConfig(clientConfig);
      boolean quiet = getQuietFromClientConfig(clientConfig);
      List<IPlayer> players = JSONParsingUtils.parseJActorsB(clientConfig.get("players"));
      ExecutorService executorService = Executors.newFixedThreadPool(players.size());
      for (int i = 0; i < players.size(); i++) {
        IPlayer player = players.get(i);
        IClient client = new client(portNumber, host, quiet, player);
        executorService.execute(client);
        if (i != players.size() - 1) {
          Thread.currentThread().sleep(waitTime * 1000L);
        }
      }
      executorService.shutdown();
    }

    catch (JsonProcessingException e) {
      System.err.println("Malformed JSON string with exception: " + e.getMessage());
    } catch (IOException e) {
      System.err.println("connecting to client failed");
    } catch (InterruptedException e) {
      System.err.println("Thread interrupted");
    }
  }

}
