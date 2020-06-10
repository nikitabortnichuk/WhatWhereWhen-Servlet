package com.bortni.controller.websocket;

import com.bortni.model.entity.Game;
import com.bortni.model.entity.User;
import com.bortni.model.entity.Variant;
import com.bortni.model.entity.question.Question;
import com.bortni.service.GameService;
import com.bortni.service.QuestionService;
import com.bortni.service.UserService;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
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
    private final Map<String, JsonObject> gameEndMessageMap = new HashMap<>();
    private final Map<String, Game> gameMap = new HashMap<>();
    private final Map<String, List<Question>> actualQuestionMap = new HashMap<>();
    private final Map<String, List<Boolean>> answerMap = new HashMap<>();

    private static QuestionService questionService = new QuestionService();
    private static GameService gameService = new GameService();
    private static UserService userService = new UserService();

    public void addSession(Session session, String gameId) {
        addToSessionMap(session, gameId);

        Set<String> users = userMap.getOrDefault(gameId, new HashSet<>());
        for (String user : users) {
            JsonObject connectMessage = createMessage("connect", gameId, user);
            sendToSession(session, connectMessage);
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

    public void initGame(String gameId) {
        Game game = gameService.findByIdent(gameId);
        int playersNumber = game.getConfiguration().getPlayersNumber();
        int actualPlayersNumber = getSessions(gameId).size();
        if (playersNumber == actualPlayersNumber) {
            addToQuestionMapIfNotNull(gameId);
            sendStartMessage(gameId, game);
        }
    }

    public void sendStartMessage(String gameId, Game game) {
        JsonObject message = gameStartMessageMap.get(gameId);
        if (message == null) {
            gameMap.put(gameId, game);
            message = createStartMessage(gameId, game);
            gameStartMessageMap.put(gameId, message);
            sendToAllConnectedSessions(message);
        }
    }

    public void processDisconnect(Session session, JsonObject jsonMessage) {
        String username = jsonMessage.getString("username");
        String gameId = jsonMessage.getString("gameId");
        removeSession(session, gameId);
        removeUser(username, gameId);
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
        sendToAllConnectedSessions(disconnectMessage);
    }

    public void removeAllSession(String gameId) {
        sessionMap.remove(gameId);
    }

    public void addUser(JsonObject jsonMessage) {
        String username = jsonMessage.getString("username");
        String gameId = jsonMessage.getString("gameId");
        Set<String> users = userMap.getOrDefault(gameId, new HashSet<>());
        if (users.isEmpty()) {
            users.add(username);
            userMap.put(gameId, users);
        } else {
            users.add(username);
        }

        JsonObject connectMessage = createMessage("connect", gameId, username);
        sendToAllConnectedSessions(connectMessage);
    }

    private JsonObject createMessage(String action, String gameId, String user) {
        return JsonProvider.provider().createObjectBuilder()
                .add("action", action)
                .add("gameId", gameId)
                .add("username", user)
                .build();
    }

    public void addToQuestionMapIfNotNull(String gameId) {
        List<Question> questions = questionMap.get(gameId);
        if (questions == null) {
            Game game = gameService.findByIdent(gameId);
            int roundsNumber = game.getConfiguration().getRoundsNumber();
            questions = questionService.getNRandomQuestions(roundsNumber);
            questionMap.put(gameId, questions);
        }
    }

    private JsonObject createStartMessage(String gameId, Game game) {
        int roundsNumber = game.getConfiguration().getRoundsNumber();
        int roundTime = game.getConfiguration().getRoundTime();
        return Json.createObjectBuilder()
                .add("action", "start")
                .add("gameId", gameId)
                .add("roundsNumber", String.valueOf(roundsNumber))
                .add("roundTime", String.valueOf(roundTime))
                .build();
    }

    public void processQuestion(JsonObject jsonObject) {
        String gameId = jsonObject.getString("gameId");
        int questionNumber = jsonObject.getInt("roundNumber");

        List<Question> actualQuestionList = actualQuestionMap.get(gameId);

        if (actualQuestionList == null) {
            actualQuestionList = new ArrayList<>();
            actualQuestionMap.put(gameId, actualQuestionList);
        } else if (actualQuestionList.isEmpty()) {
            sendQuestion(gameId, 0);
        } else if (actualQuestionList.size() == questionNumber) {
            sendQuestion(gameId, questionNumber);
        }

    }

    private void sendQuestion(String gameId, int questionNumber) {
        Game game = gameMap.get(gameId);
        int roundsNumber = game.getConfiguration().getRoundsNumber();

        if (questionNumber < roundsNumber) {
            Question question = questionMap.get(gameId).get(questionNumber);
            List<Question> questionList = actualQuestionMap.get(gameId);
            questionList.add(question);
            JsonObject jsonObject = createSendQuestion(gameId, questionNumber);
            sendToAllConnectedSessions(jsonObject);
        } else {
            sendEndMessage(gameId);
        }

    }

    private JsonObject createSendQuestion(String gameId, int questionNumber) {
        Game game = gameMap.get(gameId);
        Question question = questionMap.get(gameId).get(questionNumber);

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        return jsonObjectBuilder
                .add("action", "ask")
                .add("gameId", gameId)
                .add("numberOfQuestion", questionNumber)
                .add("questionText", question.getQuestionText())
                .add("variantList", getVariantListJson(question))
                .add("roundTime", game.getConfiguration().getRoundTime())
                .build();

    }

    private JsonArrayBuilder getVariantListJson(Question question) {
        List<Variant> variantList = question.getVariantList();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        if (variantList != null) {
            variantList.forEach(variant ->
                    jsonArrayBuilder.add(
                            Json.createObjectBuilder()
                                    .add("id", variant.getId())
                                    .add("text", variant.getText()))
            );
        }
        return jsonArrayBuilder;
    }

    public void processAnswer(JsonObject jsonMessage) {
        String gameId = jsonMessage.getString("gameId");
        int numberOfQuestion = jsonMessage.getInt("numberOfQuestion");
        String actualAnswer = jsonMessage.getString("answer");

        String correctAnswer = getAnswer(gameId, numberOfQuestion);
        sendAnswerMessage(gameId, actualAnswer, correctAnswer, numberOfQuestion);

    }

    private String getAnswer(String gameId, int numberOfQuestion) {
        Question question = questionMap.get(gameId).get(numberOfQuestion);
        String correctAnswer;
        switch (question.getQuestionType()) {
            case NO_VARIANTS:
                correctAnswer = question.getAnswer();
                break;
            case WITH_VARIANTS:
                correctAnswer = question.getVariantList().stream()
                        .filter(Variant::isCorrect)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("There is no correct answer"))
                        .getText();
                break;
            default:
                correctAnswer = "";
        }
        return correctAnswer;
    }

    private void sendAnswerMessage(String gameId, String actualAnswer, String correctAnswer, int questionNumber) {
        String message;
        boolean isCorrect;

        if (correctAnswer.equals(actualAnswer)) {
            message = "Answer is correct";
            isCorrect = true;
        } else {
            message = "Answer is not correct";
            isCorrect = false;
        }

        List<Boolean> isCorrectAnswers = answerMap.computeIfAbsent(gameId, k -> new ArrayList<>());
        isCorrectAnswers.add(isCorrect);
        sendAnswerIsCorrect(message, isCorrect, gameId, questionNumber);
    }

    private void sendAnswerIsCorrect(String message, boolean isCorrect, String gameId, int questionNumber) {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("action", "answer")
                .add("gameId", gameId)
                .add("message", message)
                .add("isCorrect", isCorrect)
                .add("numberOfQuestion", questionNumber)
                .build();
        sendToAllConnectedSessions(jsonObject);
    }

    private synchronized void sendEndMessage(String gameId) {
        JsonObject message = gameEndMessageMap.get(gameId);
        long correctAnswersNumber = getResult(gameId)[0];
        long incorrectAnswersNumber = getResult(gameId)[1];
        if (message == null) {
            message = Json.createObjectBuilder()
                    .add("action", "end")
                    .add("gameId", gameId)
                    .add("corrects", correctAnswersNumber)
                    .add("incorrects", incorrectAnswersNumber)
                    .build();
            gameEndMessageMap.put(gameId, message);

            processGameEnding(gameId, correctAnswersNumber, incorrectAnswersNumber);

            sendToAllConnectedSessions(message);
        }
    }

    private long [] getResult(String gameId){
        List<Boolean> isCorrectAnswers = answerMap.get(gameId);
        long correctAnswersNumber = isCorrectAnswers.stream().filter(b -> b).count();
        long incorrectAnswersNumber = isCorrectAnswers.stream().filter(b -> !b).count();

        return new long[] {correctAnswersNumber, incorrectAnswersNumber};
    }

    public void processGameEnding(String gameId, long correctAnswersNumber, long incorrectAnswersNumber) {
        Game game = gameMap.get(gameId);

        game.setAvailable(false);
        game.getStatistics().setExpertScore((int)correctAnswersNumber);
        game.getStatistics().setOpponentScore((int)incorrectAnswersNumber);
        gameService.update(game);

        saveUsersToGame(gameId);
    }

    private void saveUsersToGame(String gameId){
        userMap.get(gameId).forEach(username -> {
            Game game = gameService.findByIdent(gameId);
            User user = userService.findByUsername(username);
            gameService.saveUserToGame(user, game);
        });
    }

    public void sendToAllConnectedSessions(JsonObject message) {
        String gameId = message.getString("gameId");
        Set<Session> sessions = sessionMap.get(gameId);
        sessions.forEach(session -> {
                    synchronized (session) {
                        sendToSession(session, message);
                    }
                }
        );
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<Session> getSessions(String gameId) {
        return sessionMap.get(gameId);
    }

    public Set<String> getUsers(String gameId) {
        return userMap.get(gameId);
    }

    public List<Question> getQuestionMap(String gameId) {
        return questionMap.get(gameId);
    }
}
