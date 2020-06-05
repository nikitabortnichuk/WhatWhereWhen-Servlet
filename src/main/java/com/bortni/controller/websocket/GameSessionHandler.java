package com.bortni.controller.websocket;

import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameSessionHandler {

    private final Map<String, Set<Session>> sessionMap = new HashMap<>();
    private final Map<String, Set<String>> userMap = new HashMap<>();

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

    public void removeSession(Session session, String gameId) {
        Set<Session> sessions = sessionMap.get(gameId);
        if (sessions != null)
            sessions.remove(session);
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

    public void removeUser(String user, String gameId) {
        Set<String> users = userMap.get(gameId);
        users.remove(user);
        JsonObject disconnectMessage = createMessage("disconnect", gameId, user);
        sendToAllConnectedSessions(disconnectMessage, gameId);
    }

    private JsonObject createMessage(String action, String gameId, String user) {
        return JsonProvider.provider().createObjectBuilder()
                .add("action", action)
                .add("gameId", gameId)
                .add("username", user)
                .build();
    }

    private void sendToAllConnectedSessions(JsonObject connectMessage, String gameId) {
        Set<Session> sessions = sessionMap.get(gameId);
        for (Session session : sessions) {
            sendToSession(session, connectMessage, gameId);
        }
    }

    private void sendToSession(Session session, JsonObject connectMessage, String gameId) {
        Set<Session> sessions = sessionMap.get(gameId);
        try {
            session.getBasicRemote().sendText(connectMessage.toString());
        } catch (IOException e) {
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
