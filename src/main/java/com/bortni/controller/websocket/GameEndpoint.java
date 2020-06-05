package com.bortni.controller.websocket;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/game/{game}")
public class GameEndpoint {

    private static final Set<GameEndpoint> gameEndpoints = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();

    private static GameSessionHandler gameSessionHandler = new GameSessionHandler();

    @OnOpen
    public void onOpen(Session session, @PathParam("game") String gameId){
        gameSessionHandler.addSession(session, gameId);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, EncodeException {

        try(JsonReader reader = Json.createReader(new StringReader(message))){
            JsonObject jsonMessage = reader.readObject();

            String gameId = jsonMessage.getString("gameId");
            String username = jsonMessage.getString("username");

            if("connect".equals(jsonMessage.getString("action"))){
                gameSessionHandler.addUser(username, gameId);
            }
            if("disconnect".equals(jsonMessage.getString("action"))){
                gameSessionHandler.removeUser(username, gameId);
            }
            if("send".equals(jsonMessage.getString("action"))){
                gameSessionHandler.sendToAllConnectedSessions(jsonMessage, gameId);
            }
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("gameId") String gameId) throws IOException, EncodeException {
        gameSessionHandler.removeSession(session, gameId);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

}
