package com.bortni.controller.websocket;


import com.bortni.model.entity.Game;
import com.bortni.model.entity.question.Question;
import com.bortni.service.GameService;
import com.bortni.service.QuestionService;

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
import java.util.List;

@ServerEndpoint(value = "/game/{game}")
public class GameEndpoint {

    private static GameSessionHandler gameSessionHandler = new GameSessionHandler();
    private static GameService gameService = new GameService();

    @OnOpen
    public void onOpen(Session session, @PathParam("game") String gameId){

        gameSessionHandler.addSession(session, gameId);
        initGame(gameId);
    }


    @OnMessage
    public void onMessage(String message, Session session) throws IOException, EncodeException {

        try(JsonReader reader = Json.createReader(new StringReader(message))){
            JsonObject jsonMessage = reader.readObject();
            processAction(session, jsonMessage);
        }

    }

    private void processAction(Session session, JsonObject jsonMessage) {
        System.out.println(jsonMessage);
        String gameId = jsonMessage.getString("gameId");
        String username = jsonMessage.getString("username");
        switch (jsonMessage.getString("action")){
            case "connect":
                gameSessionHandler.addUser(username, gameId);
                break;
            case "disconnect":
                gameSessionHandler.processDisconnect(session, username, gameId);
                break;
            case "send":
                gameSessionHandler.sendToAllConnectedSessions(jsonMessage, gameId);
                break;
            case "start":
                gameSessionHandler.sendQuestion(jsonMessage, gameId);
                break;
        }
    }

    private void initGame(String gameId) {
        Game game = gameService.findByIdent(gameId);
        int playersNumber = game.getConfiguration().getPlayersNumber();
        int actualPlayersNumber = gameSessionHandler.getSessions(gameId).size();
        if(playersNumber == actualPlayersNumber){
            int questionsNumber = gameSessionHandler.addToQuestionMapIfNotNull(gameId);
            gameSessionHandler.sendStartMessage(gameId, questionsNumber);
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

    private void init(){

    }

}
