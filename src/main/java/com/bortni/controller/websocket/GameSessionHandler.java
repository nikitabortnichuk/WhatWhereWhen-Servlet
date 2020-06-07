package com.bortni.controller.websocket;

import com.bortni.model.entity.Game;
import com.bortni.model.entity.User;
import com.bortni.model.entity.question.Question;
import com.bortni.service.GameService;
import com.bortni.service.QuestionService;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameSessionHandler {

    private final Map<String, Set<Session>> sessionMap = new HashMap<>();
    private final Map<String, Set<String>> userMap = new HashMap<>();
    private final Map<String, List<Question>> questionMap = new HashMap<>();
    private final Map<String, JsonObject> gameStartMessageMap = new HashMap<>();
    private static QuestionService questionService = new QuestionService();
    private static GameService gameService = new GameService();


    public void addSession(Session session, String gameId) {
        addToSessionMap(session, gameId);

        Set<String> users = userMap.getOrDefault(gameId, new HashSet<>());
        for (String user : users) {
            JsonObject connectMessage = createMessage("connect", gameId, user);
            sendToSession(session, connectMessage, gameId);
        }
    }

    private void addToSessionMap(Session session, String gameId) {
        Set<Session> sessions = sessionMap.getOrDefault(gameId, new HashSet<>());
        if (sessions.isEmpty()) {
            sessions.add(session);
            sessionMap.put(gameId, sessions);
        } else {
            sessions.add(session);
        }
    }

    public void processDisconnect(Session session, String user, String gameId){
        removeSession(session, gameId);
        removeUser(user, gameId);
    }

    private void removeSession(Session session, String gameId) {
        Set<Session> sessions = sessionMap.get(gameId);
        if (sessions != null)
            sessions.remove(session);
    }

    private void removeUser(String user, String gameId) {
        Set<String> users = userMap.get(gameId);
        users.remove(user);
        JsonObject disconnectMessage = createMessage("disconnect", gameId, user);
        sendToAllConnectedSessions(disconnectMessage, gameId);
    }

    public void removeAllSession(String gameId) {
        sessionMap.remove(gameId);
    }

    public void addUser(String user, String gameId) {
        Set<String> users = userMap.getOrDefault(gameId, new HashSet<>());
        if (users.isEmpty()) {
            users.add(user);
            userMap.put(gameId, users);
        } else {
            users.add(user);
        }
        JsonObject connectMessage = createMessage("connect", gameId, user);
        sendToAllConnectedSessions(connectMessage, gameId);
    }

    private JsonObject createMessage(String action, String gameId, String user) {
        return JsonProvider.provider().createObjectBuilder()
                .add("action", action)
                .add("gameId", gameId)
                .add("username", user)
                .build();
    }

    public void sendStartMessage(String gameId, int roundsNumber){
        JsonObject message = gameStartMessageMap.get(gameId);
        if(message == null) {
            message = createStartMessage(gameId, roundsNumber);
            gameStartMessageMap.put(gameId, message);
            sendToAllConnectedSessions(message, gameId);
        }
    }

    private JsonObject createStartMessage(String gameId, int roundsNumber){
        return JsonProvider.provider().createObjectBuilder()
                .add("action", "start")
                .add("gameId", gameId)
                .add("roundsNumber", String.valueOf(roundsNumber))
                .build();
    }

    public int addToQuestionMapIfNotNull(String gameId){
        List<Question> questions = questionMap.get(gameId);
        if(questions == null) {
            Game game = gameService.findByIdent(gameId);
            int roundsNumber = game.getConfiguration().getRoundsNumber();
            questions = questionService.getNRandomQuestions(roundsNumber);
            questionMap.put(gameId, questions);
        }
        return questions.size();
    }

    public void sendQuestion(JsonObject jsonObject, String gameId) {
        List<Question> questions = questionMap.get(gameId);
        int numberOfQuestion = jsonObject.getInt("numberOfQuestion");
        Question question = questions.get(numberOfQuestion);

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder(jsonObject);
        JsonObject questionJson = jsonObjectBuilder
                .add("questionText", question.getQuestionText())
                .build();
        sendToAllConnectedSessions(questionJson, gameId);
    }

    public void sendToAllConnectedSessions(JsonObject message, String gameId) {
        Set<Session> sessions = sessionMap.get(gameId);
        Set<String> users = userMap.get(gameId);
        sessions.forEach(session -> sendToSession(session, message, gameId));
    }

    private void sendToSession(Session session, JsonObject message, String gameId) {
        Set<Session> sessions = sessionMap.get(gameId);
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException e) {
            e.printStackTrace();
            sessions.remove(session);
        }
    }

    public Set<Session> getSessions(String gameId) {
        return sessionMap.get(gameId);
    }

    public Set<String> getUsers(String gameId){
        return userMap.get(gameId);
    }
}
