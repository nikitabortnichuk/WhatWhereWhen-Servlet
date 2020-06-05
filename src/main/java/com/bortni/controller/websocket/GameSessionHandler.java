package com.bortni.controller.websocket;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameSessionHandler {

    private final Set<Session> sessions = new HashSet<>();
    private final Set<String> users = new HashSet<>();

    public void addSession(Session session){
        sessions.add(session);
        for(String user: users){
            JsonObject connectMessage = createMessage(user, "connect");
            sendToSession(session, connectMessage);
        }
    }

    public void removeSession(Session session){
        sessions.remove(session);
    }

    public List<String> getUsers() {
        return new ArrayList<>(users);
    }

    public void addUser(String user){
        users.add(user);
        JsonObject connectMessage = createMessage(user, "connect");
        sendToAllConnectedSessions(connectMessage);
    }

    public void removeUser(String user){
        users.remove(user);
        JsonObject disconnectMessage = createMessage(user, "disconnect");
        sendToAllConnectedSessions(disconnectMessage);
    }

    private JsonObject createMessage(String user, String action){
        return JsonProvider.provider().createObjectBuilder()
                .add("action", action)
                .add("username", user)
                .build();
    }

    private void sendToAllConnectedSessions(JsonObject connectMessage) {
        for(Session session : sessions){
            sendToSession(session, connectMessage);
        }
    }

    private void sendToSession(Session session, JsonObject connectMessage) {
        try {
            session.getBasicRemote().sendText(connectMessage.toString());
        } catch (IOException e){
            sessions.remove(session);
        }
    }

    public Set<Session> getSessions() {
        return sessions;
    }
}
