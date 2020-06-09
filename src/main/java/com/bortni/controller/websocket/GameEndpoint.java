package com.bortni.controller.websocket;

import com.bortni.service.GameService;

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

@ServerEndpoint(value = "/game/{game}")
public class GameEndpoint {

    private static GameSessionHandler gameSessionHandler = new GameSessionHandler();
    private static GameService gameService = new GameService();

    @OnOpen
    public void onOpen(Session session, @PathParam("game") String gameId) {

        gameSessionHandler.addSession(session, gameId);
        gameSessionHandler.initGame(gameId);
    }


    @OnMessage
    public void onMessage(String message, Session session) throws IOException, EncodeException {

        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();
            processAction(session, jsonMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void processAction(Session session, JsonObject jsonMessage) {
        switch (jsonMessage.getString("action")) {
            case "connect":
                gameSessionHandler.addUser(jsonMessage);
                break;
            case "disconnect":
                gameSessionHandler.processDisconnect(session, jsonMessage);
                break;
            case "send":
                gameSessionHandler.sendToAllConnectedSessions(jsonMessage);
                break;
            case "ask":
                gameSessionHandler.processQuestion(jsonMessage);
                break;
            case "answer":
                gameSessionHandler.processAnswer(jsonMessage);
                break;
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("gameId") String gameId) throws IOException, EncodeException {
        gameSessionHandler.removeAllSession(gameId);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    private void init() {

    }

}
