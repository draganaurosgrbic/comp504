package controller;

import com.google.gson.*;
import model.ChatWorldStore;
import model.DispatchAdapter;
import model.chatroom.ChatRoom;
import model.user.User;
import spark.Request;
import model.utils.Message;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class ChatController {

    private static Gson gson;
    private static JsonParser parser;

    /**
     * Entry point into the program.
     */
    public static void main(String[] args) {
        staticFiles.location("/public");
        port(getHerokuAssignedPort());
        webSocket("/chatapp", ChatApiWebSocket.class);
        init();

        gson = new Gson();
        parser = new JsonParser();
        ChatWorldStore store = new ChatWorldStore();
        DispatchAdapter adapter = DispatchAdapter.getOnly();
        adapter.setStore(store);

        post("/register", (request, response) -> {
            JsonObject payload = parsePayload(request);
            String username = payload.get("username").getAsString();
            String password = payload.get("pwd").getAsString();
            LocalDate birthDate = LocalDate.parse(payload.get("birthDate").getAsString());
            String school = payload.get("school").getAsString();
            List<String> interests = new ArrayList<>();
            payload.get("interests").getAsJsonArray().forEach(interest -> interests.add(interest.toString()));

            JsonObject result = createResponse(adapter.register(username, password, birthDate, school, interests));
            result.addProperty("username", username);
            return gson.toJson(result);
        });

        post("/login", (request, response) -> {
            JsonObject payload = parsePayload(request);
            String username = payload.get("username").getAsString();
            String password = payload.get("pwd").getAsString();

            JsonObject result = createResponse(adapter.login(username, password));
            result.addProperty("username", username);
            return gson.toJson(result);
        });

        post("/chatroom/create", (request, response) -> {
            JsonObject payload = parsePayload(request);
            String username = payload.get("username").getAsString();
            String roomName = payload.get("roomName").getAsString();
            int roomSize = payload.get("roomSize").getAsInt();
            boolean isPublic = payload.get("roomType").getAsString().equals("public");

            JsonObject result = createResponse(adapter.createRoom(username, roomName, roomSize, isPublic));
            result.addProperty("roomName", roomName);
            return gson.toJson(result);
        });

        post("/chatroom/join", (request, response) -> {
            JsonObject payload = parsePayload(request);
            String username = payload.get("username").getAsString();
            String roomName = payload.get("roomName").getAsString();

            JsonObject result = createResponse(adapter.joinRoom(username, roomName));
            result.addProperty("roomName", roomName);
            return gson.toJson(result);
        });

        get("/chatrooms", (request, response) -> {
            JsonObject result = createResponse(null);
            JsonArray roomNames = new JsonArray();
            adapter.publicRooms().forEach(roomNames::add);
            result.add("roomNames", roomNames);
            return gson.toJson(result);
        });

        get("/chatrooms/:username", (request, response) -> {
            String username = request.params(":username");

            JsonObject result = createResponse(null);
            JsonArray roomNames = new JsonArray();
            adapter.userRooms(username).forEach(roomNames::add);
            result.add("roomNames", roomNames);
            return gson.toJson(result);
        });

        get("/chatroom/admin/:roomName", (request, response) -> {
            String roomName = request.params(":roomName");

            JsonObject result = createResponse(null);
            result.addProperty("username", adapter.roomAdmin(roomName));
            return gson.toJson(result);
        });

        get("/chatroom/members/:roomName", (request, response) -> {
            String roomName = request.params(":roomName");

            JsonObject result = createResponse(null);
            JsonArray usernames = new JsonArray();
            adapter.roomMembers(roomName).forEach(usernames::add);
            result.add("usernames", usernames);
            return gson.toJson(result);
        });

        get("/list/chatroom/members/:roomName", (request, response) -> {
            String roomName = request.params(":roomName");

            JsonObject result = createResponse(null);
            JsonArray users = new JsonArray();
            adapter.getRoomMembers(roomName).forEach(u -> {
                JsonObject user = new JsonObject();

                user.addProperty("id", u.getId());
                user.addProperty("username", u.getUsername());
                user.addProperty("password", u.getPassword());
                user.addProperty("age", u.getAge());
                user.addProperty("school", u.getSchool());
                JsonArray interests = new JsonArray();
                u.getInterests().forEach(interests::add);
                user.add("interests", interests);
                user.addProperty("avatar", u.getAvatar());

                users.add(user);
            });
            result.add("users", users);
            return gson.toJson(result);
        });

        post("/chatroom/report", (request, response) -> {
            JsonObject payload = parsePayload(request);
            String senderUsername = payload.get("senderUsername").getAsString();
            String username = payload.get("username").getAsString();
            String roomName = payload.get("roomName").getAsString();
            String reason = payload.get("reason").getAsString();

            return gson.toJson(createResponse(adapter.reportUser(senderUsername, username, roomName, reason)));
        });

        get("/chatroom/reported/:roomName", (request, response) -> {
            String roomName = request.params(":roomName");

            JsonObject result = createResponse(null);
            JsonArray usernames = new JsonArray();
            adapter.reportedUsers(roomName).forEach(usernames::add);
            result.add("usernames", usernames);
            return gson.toJson(result);
        });

        get("/list/chatroom/reported/:roomName", (request, response) -> {
            String roomName = request.params(":roomName");

            JsonObject result = createResponse(null);
            JsonArray reportedArray = new JsonArray();
            List<String> reportedUsers = adapter.reportedUsers(roomName);

            for (String username : reportedUsers) {
                JsonObject reportedJson = new JsonObject();
                User reportedUser = adapter.getUser(username);

                reportedJson.addProperty("id", reportedUser.getId());
                reportedJson.addProperty("username", reportedUser.getUsername());
                reportedJson.addProperty("age", reportedUser.getAge());
                reportedJson.addProperty("school", reportedUser.getSchool());

                JsonArray interestsArray = new JsonArray();
                reportedUser.getInterests().forEach(interestsArray::add);
                reportedJson.add("interests", interestsArray);

                reportedJson.addProperty("avatar", reportedUser.getAvatar());
                reportedJson.addProperty("description", reportedUser.getSelfDescription());
                reportedJson.addProperty("active", reportedUser.isActive());

                reportedArray.add(reportedJson);
            }

            result.add("reportedUsers", reportedArray);
            return gson.toJson(result);
        });

        get("/list/chatroom/banned/:roomName", (request, response) -> {
            String roomName = request.params(":roomName");

            JsonObject result = createResponse(null);
            JsonArray bannedArray = new JsonArray();
            List<String> bannedUsers = adapter.bannedUsers(roomName);

            for (String username : bannedUsers) {
                JsonObject bannedJson = new JsonObject();
                User reportedUser = adapter.getUser(username);

                bannedJson.addProperty("id", reportedUser.getId());
                bannedJson.addProperty("username", reportedUser.getUsername());
                bannedJson.addProperty("age", reportedUser.getAge());
                bannedJson.addProperty("school", reportedUser.getSchool());

                JsonArray interestsArray = new JsonArray();
                reportedUser.getInterests().forEach(interestsArray::add);
                bannedJson.add("interests", interestsArray);

                bannedJson.addProperty("avatar", reportedUser.getAvatar());
                bannedJson.addProperty("description", reportedUser.getSelfDescription());
                bannedJson.addProperty("active", reportedUser.isActive());

                bannedArray.add(bannedJson);
            }

            result.add("bannedUsers", bannedArray);
            return gson.toJson(result);
        });

        post("/chatroom/ban", (request, response) -> {
            JsonObject payload = parsePayload(request);
            String senderUsername = payload.get("senderUsername").getAsString();
            String username = payload.get("username").getAsString();
            String roomName = payload.get("roomName").getAsString();
            String reason = payload.get("reason").getAsString();

            return gson.toJson(createResponse(adapter.banUser(senderUsername, username, roomName, reason)));
        });

        get("/chatroom/banned/:roomName", (request, response) -> {
            String roomName = request.params(":roomName");

            JsonObject result = createResponse(null);
            JsonArray usernames = new JsonArray();
            adapter.bannedUsers(roomName).forEach(usernames::add);
            result.add("usernames", usernames);
            return gson.toJson(result);
        });

        post("/chatrooms/ban", (request, response) -> {
            JsonObject payload = parsePayload(request);
            String username = payload.get("username").getAsString();

            return gson.toJson(createResponse(adapter.banUser(username)));
        });

        post("/chatrooms/banned", (request, response) -> {
            JsonObject payload = parsePayload(request);
            String username = payload.get("username").getAsString();

            JsonObject result = createResponse(null);
            result.addProperty("result", adapter.userBanned(username));
            return gson.toJson(result);
        });

        post("/chatroom/leave/:roomName", (request, response) -> {
            String roomName = request.params(":roomName");
            JsonObject payload = parsePayload(request);
            String username = payload.get("username").getAsString();

            return gson.toJson(createResponse(adapter.leaveRoom(username, roomName)));
        });

        post("/chatrooms/leave", (request, response) -> {
            JsonObject payload = parsePayload(request);
            String username = payload.get("username").getAsString();

            return gson.toJson(createResponse(adapter.leaveRooms(username)));
        });

        post("/chatroom/invite/:roomName", (request, response) -> {
            String roomName = request.params(":roomName");
            JsonObject payload = parsePayload(request);
            String senderUsername = payload.get("senderUsername").getAsString();
            String username = payload.get("username").getAsString();

            return gson.toJson(createResponse(adapter.inviteToRoom(senderUsername, username, roomName)));
        });

        get("/chatrooms/invited/:username", (request, response) -> {
            String username = request.params(":username");

            JsonObject result = createResponse(null);
            JsonArray roomNames = new JsonArray();
            adapter.userRoomInvitations(username).forEach(roomNames::add);
            result.add("roomNames", roomNames);
            return gson.toJson(result);
        });

        post("/chatroom/accept/:roomName", (request, response) -> {
            String roomName = request.params(":roomName");
            JsonObject payload = parsePayload(request);
            String username = payload.get("username").getAsString();

            return gson.toJson(createResponse(adapter.acceptRoomInvitation(username, roomName)));
        });

        post("/chatroom/decline/:roomName", (request, response) -> {
            String roomName = request.params(":roomName");
            JsonObject payload = parsePayload(request);
            String username = payload.get("username").getAsString();

            return gson.toJson(createResponse(adapter.declineRoomInvitation(username, roomName)));
        });

        post("/chatroom/message", (request, response) -> {
            JsonObject payload = parsePayload(request);
            String username = payload.get("username").getAsString();
            String roomName = payload.get("roomName").getAsString();
            String content = payload.get("message").getAsString();

            return gson.toJson(createResponse(adapter.createMessage(username, roomName, content)));
        });

        post("/chatroom/message/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            JsonObject payload = parsePayload(request);
            String roomName = payload.get("roomName").getAsString();
            String content = payload.get("message").getAsString();

            return gson.toJson(createResponse(adapter.editMessage(roomName, id, content)));
        });

        post("/chatroom/message/heart/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            JsonObject payload = parsePayload(request);
            String username = payload.get("username").getAsString();
            String roomName = payload.get("roomName").getAsString();

            return gson.toJson(adapter.addHeartToMessage(username, roomName, id));
        });

        post("/chatroom/message/reaction/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            JsonObject payload = parsePayload(request);
            String username = payload.get("username").getAsString();
            String roomName = payload.get("roomName").getAsString();
            String reactionType = payload.get("reactionType").getAsString();

            return gson.toJson(adapter.addReactionToMessage(username, roomName, id, reactionType));
        });

        delete("/chatroom/message/:id", (request, response) -> {
            String[] array = request.params(":id").split("_");
            String roomName = array[0];
            int id = Integer.parseInt(array[1]);

            return gson.toJson(createResponse(adapter.deleteMessage(roomName, id)));
        });

        get("/chatroom/message/:roomName", (request, response) -> {
            String roomName = request.params(":roomName");

            JsonObject result = createResponse(null);
            JsonArray messages = new JsonArray();
            adapter.roomMessages(roomName).forEach(m -> {
                JsonObject message = new JsonObject();
                message.addProperty("msgID", m.getId());
                message.addProperty("senderName", m.getSender().getUsername());
                message.addProperty("receiverName", "receiver");
                message.addProperty("content", m.getContent());
                message.addProperty("expressedHeartCount", m.getReactions().values().stream().filter(reaction -> reaction.equals("heart")).toList().size());
                messages.add(message);
            });
            result.add("messages", messages);

            return gson.toJson(result);
        });

        get("/chatroom/message", (request, response) -> {
            JsonObject result = createResponse(null);
            JsonArray messages = new JsonArray();
            adapter.allMessages().forEach(m -> {
                JsonObject message = new JsonObject();
                message.addProperty("msgID", m.getId());
                message.addProperty("senderName", m.getSender().getUsername());
                message.addProperty("receiverName", "receiver");
                message.addProperty("content", m.getContent());
                message.addProperty("expressedHeartCount", m.getReactions().values().stream().filter(reaction -> reaction.equals("heart")).toList().size());
                messages.add(message);
            });
            result.add("messages", messages);

            return gson.toJson(result);
        });

        get("/user/:username", (request, response) -> {
            String username = request.params(":username");

            User user = adapter.getUser(username);
            if (user == null) {
                return gson.toJson(null);
            }

            JsonObject result = new JsonObject();
            result.addProperty("id", user.getId());
            result.addProperty("username", user.getUsername());
            result.addProperty("password", user.getPassword());
            result.addProperty("age", user.getAge());
            result.addProperty("school", user.getSchool());
            JsonArray interests = new JsonArray();
            user.getInterests().forEach(interests::add);
            result.add("interests", interests);
            result.addProperty("avatar", user.getAvatar());
            result.addProperty("description", user.getSelfDescription());
            result.addProperty("hateSpeechCounter", user.getHateSpeechCounter());

            return gson.toJson(result);
        });

        get("/user/friends/:username", (request, response) -> {
            String username = request.params(":username");

            JsonObject result = createResponse(null);
            JsonArray usernames = new JsonArray();
            adapter.getFriends(username).forEach(usernames::add);
            result.add("usernames", usernames);
            return gson.toJson(result);
        });

        get("/user/not_friends/:username", (request, response) -> {
            String username = request.params(":username");

            JsonObject result = createResponse(null);
            JsonArray usernames = new JsonArray();
            adapter.getNotFriends(username).forEach(usernames::add);
            result.add("usernames", usernames);
            return gson.toJson(result);
        });

        post("/user/friends/invite", (request, response) -> {
            JsonObject payload = parsePayload(request);

            String senderUsername = payload.get("senderUsername").getAsString();
            String username = payload.get("username").getAsString();
            return gson.toJson(createResponse(adapter.inviteFriend(senderUsername, username)));
        });

        post("/user/friends/accept", (request, response) -> {
            JsonObject payload = parsePayload(request);

            String senderUsername = payload.get("senderUsername").getAsString();
            String username = payload.get("username").getAsString();
            return gson.toJson(createResponse(adapter.acceptFriend(senderUsername, username)));
        });

        post("/user/friends/decline", (request, response) -> {
            JsonObject payload = parsePayload(request);

            String senderUsername = payload.get("senderUsername").getAsString();
            String username = payload.get("username").getAsString();
            return gson.toJson(createResponse(adapter.declineFriend(senderUsername, username)));
        });

        get("/invitations/:username", (request, response) -> {
            String username = request.params(":username");
            JsonObject result = createResponse(null);

            JsonArray rooms = new JsonArray();
            adapter.getUserRoomInvitations(username).forEach(r -> {
                JsonObject room = new JsonObject();
                room.addProperty("id", r.getRoom().getId());
                room.addProperty("owner", r.getRoom().getOwner().getUsername());
                room.addProperty("roomName", r.getRoom().getName());
                room.addProperty("roomSize", r.getRoom().getSize());
                room.addProperty("roomType", r.getRoom().getIsPublic() ? "public" : "private");
                room.addProperty("avatar", r.getRoom().getAvatar());
                room.addProperty("capacity", r.getRoom().getCapacity());
                room.addProperty("sender", r.getSender().getUsername());
                rooms.add(room);
            });

            result.add("rooms", rooms);

            JsonArray users = new JsonArray();
            adapter.getUserFriendInvitations(username).forEach(u -> {
                JsonObject user = new JsonObject();
                user.addProperty("id", u.getSender().getId());
                user.addProperty("username", u.getSender().getUsername());
                user.addProperty("password", u.getSender().getPassword());
                user.addProperty("age", u.getSender().getAge());
                user.addProperty("school", u.getSender().getSchool());
                JsonArray interests = new JsonArray();
                u.getSender().getInterests().forEach(interests::add);
                user.add("interests", interests);
                user.addProperty("avatar", u.getSender().getAvatar());
                users.add(user);
            });

            result.add("users", users);
            return gson.toJson(result);
        });

        get("/list/chatrooms/:username", (request, response) -> {
            String username = request.params(":username");
            JsonObject result = createResponse(null);

            JsonArray rooms = new JsonArray();
            adapter.getUserRooms(username).forEach(r -> {
                JsonObject room = new JsonObject();
                room.addProperty("id", r.getId());
                room.addProperty("owner", r.getOwner().getUsername());
                room.addProperty("roomName", r.getName());
                room.addProperty("roomSize", r.getSize());
                room.addProperty("roomType", r.getIsPublic() ? "public" : "private");
                room.addProperty("avatar", r.getAvatar());
                room.addProperty("capacity", r.getCapacity());

                // Convert latest message to JSON
                Message latestMessage = adapter.getLatestMessage(username, r.getName());
                if (latestMessage != null) {
                    JsonObject latestMessageJson = new JsonObject();
                    latestMessageJson.addProperty("msgID", latestMessage.getId());
                    latestMessageJson.addProperty("senderName", latestMessage.getSender().getUsername());
                    latestMessageJson.addProperty("content", latestMessage.getContent());
                    latestMessageJson.addProperty("timestamp", latestMessage.getTimestamp().toString());
                    latestMessageJson.addProperty("expressedHeartCount", latestMessage.getReactions().values().stream().filter(reaction -> reaction.equals("heart")).toList().size());
                    room.add("latestMessage", latestMessageJson);
                }

                rooms.add(room);
            });

            result.add("rooms", rooms);
            return gson.toJson(result);
        });

        get("/list/chatrooms", (request, response) -> {
            JsonObject result = createResponse(null);

            JsonArray rooms = new JsonArray();
            adapter.getPublicRooms().forEach(r -> {
                JsonObject room = new JsonObject();
                room.addProperty("id", r.getId());
                room.addProperty("owner", r.getOwner().getUsername());
                room.addProperty("roomName", r.getName());
                room.addProperty("roomSize", r.getSize());
                room.addProperty("roomType", r.getIsPublic() ? "public" : "private");
                room.addProperty("avatar", r.getAvatar());
                room.addProperty("capacity", r.getCapacity());
                rooms.add(room);
            });

            result.add("rooms", rooms);
            return gson.toJson(result);
        });

        get("/chatroom/details/:roomName", (request, response) -> {
            String roomName = request.params(":roomName");

            ChatRoom room = adapter.getRoom(roomName);
            if (room == null) {
                return gson.toJson(null);
            }

            JsonObject result = new JsonObject();
            result.addProperty("id", room.getId());
            result.addProperty("owner", room.getOwner().getUsername());
            result.addProperty("roomName", room.getName());
            result.addProperty("roomSize", room.getSize());
            result.addProperty("roomType", room.getIsPublic() ? "public" : "private");
            result.addProperty("avatar", room.getAvatar());
            result.addProperty("capacity", room.getCapacity());
            return gson.toJson(result);
        });

        post("/chatroom/block", (request, response) -> {
            JsonObject payload = parsePayload(request);
            String senderName = payload.get("senderName").getAsString();
            String receiverName = payload.get("receiverName").getAsString();

            return gson.toJson(createResponse(adapter.blockUser(senderName, receiverName)));
        });

        post("/chatroom/unblock", ((request, response) -> {
            JsonObject payload = parsePayload(request);
            String senderName = payload.get("senderName").getAsString();
            String receiverName = payload.get("receiverName").getAsString();

            return gson.toJson(createResponse(adapter.unblockUser(senderName, receiverName)));
        }));

        post("chatroom/setRoomName", (request, response) -> {
            JsonObject payload = parsePayload(request);
            String roomName = payload.get("roomName").getAsString();
            String newRoomName = payload.get("newRoomName").getAsString();

            JsonObject result = createResponse(null);
            result.addProperty("roomName", adapter.setRoomName(roomName, newRoomName));
            return gson.toJson(result);
        });

        post("chatroom/setCapacity", (request, response) -> {
            JsonObject payload = parsePayload(request);
            String roomName = payload.get("roomName").getAsString();
            int capacity = payload.get("capacity").getAsInt();

            JsonObject result = createResponse(null);
            result.addProperty("capacity", adapter.setRoomCapacity(roomName, capacity));
            return gson.toJson(result);
        });

        post("/user/description", (request, response) -> {
            JsonObject payload = parsePayload(request);
            String username = payload.get("username").getAsString();
            String description = payload.get("description").getAsString();

            JsonObject result = createResponse(null);
            result.addProperty("description", adapter.setUserDescription(username, description));
            return gson.toJson(result);
        });

        post("/user/setActive", (request, response) -> {
            JsonObject payload = parsePayload(request);
            String username = payload.get("username").getAsString();
            boolean activeStatus = payload.get("activeStatus").getAsBoolean();

            JsonObject result = createResponse(null);
            result.addProperty("result", adapter.setActive(username, activeStatus));
            return gson.toJson(result);
        });

        post("/user/setInfo", (request, response) -> {
            JsonObject payload = parsePayload(request);
            String userName = payload.get("userName").getAsString();
            String newSchool = payload.get("newSchool").getAsString();
            String newDescription = payload.get("newDescription").getAsString();
            JsonArray newInterestsArray = payload.getAsJsonArray("newInterests");

            // Convert JsonArray to List<String>
            List<String> newInterests = new ArrayList<>();
            for (JsonElement interestElement : newInterestsArray) {
                newInterests.add(interestElement.getAsString());
            }

            User user = adapter.getUser(userName);
            adapter.setUserInfo(user, newSchool, newInterests, newDescription);
            if (user == null) {
                return gson.toJson(null);
            }

            JsonObject result = new JsonObject();
            result.addProperty("school", user.getSchool());
            JsonArray interests = new JsonArray();
            user.getInterests().forEach(interests::add);
            result.add("interests", interests);
            result.addProperty("description", user.getSelfDescription());
            return gson.toJson(result);
        });

        get("/users/usersAll/:username", (request, response) -> {
            String username = request.params(":username");

            JsonObject result = createResponse(null);
            JsonArray usersArray = new JsonArray();

            List<User> users = adapter.allUsers(username);

            for (User user: users) {
                JsonObject userJson = new JsonObject();

                userJson.addProperty("id", user.getId());
                userJson.addProperty("username", user.getUsername());
                userJson.addProperty("age", user.getAge());
                userJson.addProperty("school", user.getSchool());

                JsonArray interestsArray = new JsonArray();
                user.getInterests().forEach(interestsArray::add);
                userJson.add("interests", interestsArray);

                userJson.addProperty("avatar", user.getAvatar());
                userJson.addProperty("description", user.getSelfDescription());
                userJson.addProperty("active", user.isActive());

                usersArray.add(userJson);
            }

            result.add("users", usersArray);
            return gson.toJson(result);
        });

        get("/users/usersAll", (request, response) -> {
            JsonObject result = createResponse(null);
            JsonArray usersArray = new JsonArray();

            List<User> users = adapter.allUsers();

            for (User user: users) {
                JsonObject userJson = new JsonObject();

                userJson.addProperty("id", user.getId());
                userJson.addProperty("username", user.getUsername());
                userJson.addProperty("age", user.getAge());
                userJson.addProperty("school", user.getSchool());

                JsonArray interestsArray = new JsonArray();
                user.getInterests().forEach(interestsArray::add);
                userJson.add("interests", interestsArray);

                userJson.addProperty("avatar", user.getAvatar());
                userJson.addProperty("description", user.getSelfDescription());
                userJson.addProperty("active", user.isActive());

                usersArray.add(userJson);
            }

            result.add("users", usersArray);
            return gson.toJson(result);
        });

        get("/user/friendsAll/:username", (request, response) -> {
            String username = request.params(":username");

            JsonObject result = createResponse(null);
            JsonArray friendsArray = new JsonArray();
            List<String> friends = adapter.getFriends(username);

            for (String friendName : friends) {
                JsonObject friendJson = new JsonObject();
                User friend = adapter.getUser(friendName);

                friendJson.addProperty("id", friend.getId());
                friendJson.addProperty("username", friend.getUsername());
                friendJson.addProperty("age", friend.getAge());
                friendJson.addProperty("school", friend.getSchool());

                JsonArray interestsArray = new JsonArray();
                friend.getInterests().forEach(interestsArray::add);
                friendJson.add("interests", interestsArray);

                friendJson.addProperty("avatar", friend.getAvatar());
                friendJson.addProperty("description", friend.getSelfDescription());
                friendJson.addProperty("active", friend.isActive());

                friendsArray.add(friendJson);
            }

            result.add("friends", friendsArray);
            return gson.toJson(result);
        });

        get("/user/blockList/:username", ((request, response) -> {
            String username = request.params(":username");
            JsonObject result = createResponse(null);
            JsonArray blockList = new JsonArray();
            adapter.getBlockList(username).forEach(blockList::add);
            result.add("blockList", blockList);
            return gson.toJson(result);
        }));

        get("/list/chatroom/message/:roomName/:username", (request, response) -> {
            String roomName = request.params(":roomName");
            String username = request.params(":username");

            JsonObject result = createResponse(null);
            JsonArray messages = new JsonArray();
            adapter.getUserMessages(username, roomName).forEach(m -> {
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
                messages.add(message);
            });

            result.add("messages", messages);
            return gson.toJson(result);
        });

        CorsFilter.apply();

    }

    private static JsonObject parsePayload(Request request) {
        return parser.parse(request.body()).getAsJsonObject();
    }

    private static JsonObject createResponse(String responseValue) {
        JsonObject response = new JsonObject();
        if (responseValue != null) {
            response.addProperty("result", responseValue);
        }
        return response;
    }

    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }

}
