package controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.DispatchAdapter;
import model.user.User;
import model.utils.Message;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create a web socket for the server.
 */
@WebSocket
public class ChatApiWebSocket {
    private static final Map<Session, List<String>> sessionUserMap = new ConcurrentHashMap<>();

    /**
     * Open user's session.
     * @param session The user whose session is opened.
     */
    @OnWebSocketConnect
    public void onConnect(Session session) {

        Map<String, List<String>> params = session.getUpgradeRequest().getParameterMap();
        if (params != null && params.containsKey("username") && params.containsKey("roomName")) {
            String username = params.get("username").get(0);
            String roomName = params.get("roomName").get(0);

            System.out.println("OPENING CONNECTION FOR " + username + " " + roomName);

            sessionUserMap.put(session, List.of(username, roomName));
        }
    }

    /**
     * Close the user's session.
     * @param session The use whose session is closed.
     */
    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        DispatchAdapter adapter = DispatchAdapter.getOnly();

        String username = sessionUserMap.get(session).get(0);
        String roomName = sessionUserMap.get(session).get(1);

        System.out.println("CLOSING CONNECTION FOR " + username + " " + roomName);

        // adapter.createMessage(username, roomName, "User's connection closed.");

        sessionUserMap.remove(session);
    }

    /**
     * Send a message.
     */
    public static void sendMessage(String roomName, Message m) {
        DispatchAdapter adapter = DispatchAdapter.getOnly();

        for (Map.Entry<Session, List<String>> item: sessionUserMap.entrySet()) {
            boolean userBlocked = m.getSender() != null && adapter.getBlockList(item.getValue().get(0)).contains(m.getSender().getUsername());
            boolean myMessage = m.getReceiver() == null || m.getReceiver().getUsername().equals(item.getValue().get(0));

            if (item.getValue().get(1).equals(roomName) && !userBlocked && myMessage) {

                JsonObject message = new JsonObject();
                message.addProperty("msgID", m.getId());
                message.addProperty("senderName", m.getSender().getUsername());
                // message.addProperty("receiverName", "receiver");
                message.addProperty("content", m.getContent());
                message.addProperty("expressedHeartCount", m.getReactions().values().stream().filter(reaction -> reaction.equals("heart")).toList().size());
                message.addProperty("avatar", m.getSender().getAvatar());
                message.addProperty("timestamp", m.getTimestamp().toString());
                message.addProperty("status", m.getStatus());

                JsonArray hearts = new JsonArray();
                JsonArray likes = new JsonArray();
                JsonArray dislikes = new JsonArray();

                m.getReactions().forEach((key, type) -> {
                    JsonObject reactUser = new JsonObject();
                    reactUser.addProperty("username", key.getUsername());
                    reactUser.addProperty("avatar", key.getAvatar());
                    if (type.equals("heart")) {
                        hearts.add(reactUser);
                    } else if (type.equals("like")) {
                        likes.add(reactUser);
                    } else if (type.equals("dislike")) {
                        dislikes.add(reactUser);
                    }
                });

                message.add("hearts", hearts);
                message.add("likes", likes);
                message.add("dislikes", dislikes);


                try {
                    item.getKey().getRemote().sendString(String.valueOf(message));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Send a message.
     * @param session The session user sending the message.
     * @param message The message to be sent.
     */
    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
    }
}