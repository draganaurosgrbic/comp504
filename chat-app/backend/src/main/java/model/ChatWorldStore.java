package model;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

import model.user.User;
import model.chatroom.ChatRoom;
import model.utils.FriendInvitation;
import model.utils.Message;
import model.utils.RoomInvitation;

// Class representing store for users and rooms
public class ChatWorldStore {

    // Users
    private Map<String, User> users;

    // Rooms
    private Map<String, ChatRoom> rooms;

    /**
     * Constructor.
     */
    public ChatWorldStore() {
        this.users = new HashMap<>();
        this.rooms = new HashMap<>();
    }

    /**
     * Get users.
     *
     * @return users
     */
    public List<User> getUsers() {
        return users.values().stream().sorted(Comparator.comparingInt(User::getId)).toList();
    }

    /**
     * Get rooms.
     *
     * @return rooms
     */
    public List<ChatRoom> getRooms() {
        return rooms.values().stream().sorted(Comparator.comparingInt(ChatRoom::getId)).toList();
    }

    /**
     * Register user.
     *
     * @param username  user's username
     * @param password  user's password
     * @param birthDate user's date of birth
     * @param school    user's school
     * @param interests user's interests
     * @return status of registration
     */
    public String register(String username, String password, LocalDate birthDate, String school, List<String> interests) {
        if (users.containsKey(username)) {
            return "failed: user already exists";
        }
        int id = createUserId();
        users.put(username, new User(id, username, password, birthDate, school, interests));
        users.get(username).setActive(true);
        return "success";
    }

    /**
     * Login user.
     *
     * @param username user's username
     * @param password user's password
     * @return status of login
     */
    public String login(String username, String password) {
        if (!users.containsKey(username)) {
            return "failed: user does not exist";
        }
        if (!users.get(username).getPassword().equals(password)) {
            return "failed: wrong password";
        }
        users.get(username).setActive(true);
        return "success";
    }

    /**
     * Create room.
     *
     * @param username user's username
     * @param name     room's name
     * @param size     room's size
     * @param isPublic room's public status
     * @return status of creating room
     */
    public String createRoom(String username, String name, int size, boolean isPublic) {
        if (!users.containsKey(username)) {
            return "failed: user does not exist";
        }
        User user = users.get(username);
        if (rooms.containsKey(name)) {
            return "failed: room already exists";
        }
        if (size <= 0) {
            return "failed: size is not greater than zero";
        }
        int id = createRoomId();
        ChatRoom room = new ChatRoom(id, user, name, size, isPublic);
        user.addRoom(room);
        user.addOwnedRoom(room);
        room.addUser(user);
        rooms.put(name, room);
        return "success";
    }

    /**
     * Join room.
     *
     * @param username user's username
     * @param roomName room's name
     * @return status of joining room
     */
    public String joinRoom(String username, String roomName) {
        if (!users.containsKey(username)) {
            return "failed: user does not exist";
        }
        User user = users.get(username);
        if (!rooms.containsKey(roomName)) {
            return "failed: room does not exist";
        }
        ChatRoom room = rooms.get(roomName);

        if (user.isBanned()) {
            return "failed: user is banned";
        }
        if (room.userBanned(user)) {
            return "failed: user is banned from the room";
        }

        if (room.userJoined(user)) {
            return "failed: user already joined the room";
        }
        if (!room.isAvailable()) {
            return "failed: room is full";
        }
        if (!room.getIsPublic()) {
            return "failed: room is private";
        }

        user.addRoom(room);
        room.addUser(user);
        return "success";
    }

    /**
     * Get available public rooms.
     *
     * @return available public rooms
     */
    public List<String> publicRooms() {
        return rooms.values().stream().filter(room -> room.getIsPublic() && room.isAvailable()).map(ChatRoom::getName).sorted().toList();
    }

    /**
     * Get user's rooms.
     *
     * @param username user's username
     * @return rooms
     */
    public List<String> userRooms(String username) {
        return users.containsKey(username)
                ? users.get(username).getRooms().stream().map(ChatRoom::getName).toList()
                : List.of();
    }

    /**
     * Get room's admin.
     *
     * @param roomName room's name
     * @return admin
     */
    public String roomAdmin(String roomName) {
        return rooms.containsKey(roomName) ? rooms.get(roomName).getOwner().getUsername() : "failed: room does not exist";
    }

    /**
     * Get room's members.
     *
     * @param roomName room's name
     * @return members
     */
    public List<String> roomMembers(String roomName) {
        return rooms.containsKey(roomName)
                ? rooms.get(roomName).getMembers().stream().map(User::getUsername).toList()
                : List.of();
    }

    /**
     * Report user.
     *
     * @param senderUsername reporter user's username
     * @param username       reported user's username
     * @param roomName       room's name
     * @param reason         reason for reporting
     * @return status of reporting
     */
    public String reportUser(String senderUsername, String username, String roomName, String reason) {
        if (!users.containsKey(senderUsername)) {
            return "failed: sender user does not exist";
        }
        User senderUser = users.get(senderUsername);
        if (!users.containsKey(username)) {
            return "failed: user does not exist";
        }
        User user = users.get(username);
        if (!rooms.containsKey(roomName)) {
            return "failed: room does not exist";
        }
        ChatRoom room = rooms.get(roomName);
        if (!room.userJoined(user)) {
            return "failed: user is not in the room";
        }
        room.reportUser(senderUser, user, reason);
        return "reportUser";
    }

    /**
     * Get room's reported users.
     *
     * @param roomName room's name
     * @return reported users
     */
    public List<String> reportedUsers(String roomName) {
        return rooms.containsKey(roomName)
                ? rooms.get(roomName).getReportedUsers().stream().map(User::getUsername).toList()
                : List.of();
    }

    /**
     * Ban user.
     *
     * @param senderUsername banner user's username
     * @param username       banned user's username
     * @param roomName       room's name
     * @param reason         reason for banning
     * @return status of banning
     */
    public String banUser(String senderUsername, String username, String roomName, String reason) {
        if (!users.containsKey(senderUsername)) {
            return "failed: sender user does not exist";
        }
        User senderUser = users.get(senderUsername);
        if (!users.containsKey(username)) {
            return "failed: user does not exist";
        }
        User user = users.get(username);
        if (!rooms.containsKey(roomName)) {
            return "failed: room does not exist";
        }
        ChatRoom room = rooms.get(roomName);
        if (!room.userJoined(user)) {
            return "failed: user is not in the room";
        }
        user.removeRoom(room);
        room.banUser(senderUser, user, reason);
        return "banUser";
    }

    /**
     * Ban user globally.
     *
     * @param username user's username
     * @return status of banning user
     */
    public String banUser(String username) {
        if (!users.containsKey(username)) {
            return "failed: user does not exist";
        }
        User user = users.get(username);
        user.setBanned(true);
        return "success";
    }

    /**
     * Get room's banned users.
     *
     * @param roomName room's name
     * @return banned users
     */
    public List<String> bannedUsers(String roomName) {
        return rooms.containsKey(roomName)
                ? rooms.get(roomName).getBannedUsers().stream().map(User::getUsername).toList()
                : List.of();
    }

    /**
     * Get user's banned status.
     *
     * @param username user's username
     * @return banned status
     */
    public boolean userBanned(String username) {
        return users.containsKey(username) && users.get(username).isBanned();
    }

    /**
     * Leave room.
     *
     * @param username user's username
     * @param roomName room's name
     * @return status of leaving room
     */
    public String leaveRoom(String username, String roomName) {
        if (!users.containsKey(username)) {
            return "failed: user does not exist";
        }
        User user = users.get(username);
        if (!rooms.containsKey(roomName)) {
            return "failed: room does not exist";
        }
        ChatRoom room = rooms.get(roomName);
        user.removeRoom(room);
        room.removeUser(user, "leaveRoom");

        // If the owner of the room left, assign a new owner
        if (username.equals(room.getOwner().getUsername())) {
            // room.setOwner(room.getMembers().get(0));
        }

        return "leaveRoom";
    }

    /**
     * Leave all rooms.
     *
     * @param username user's username
     * @return status of leaving all rooms
     */
    public String leaveRooms(String username) {
        if (!users.containsKey(username)) {
            return "failed: user does not exist";
        }
        User user = users.get(username);
        for (ChatRoom room : user.getRooms()) {
            user.removeRoom(room);
            room.removeUser(user, "LeaveAllRoom");
        }
        return "LeaveAllRoom";
    }

    /**
     * Invite user to room.
     *
     * @param senderUsername inviting user's username
     * @param username       invited user's username
     * @param roomName       room's name
     * @return status of inviting user to room
     */
    public String inviteToRoom(String senderUsername, String username, String roomName) {
        if (!users.containsKey(senderUsername)) {
            return "failed: sender user does not exist";
        }
        User senderUser = users.get(senderUsername);
        if (!users.containsKey(username)) {
            return "failed: user does not exist";
        }
        User user = users.get(username);
        if (!rooms.containsKey(roomName)) {
            return "failed: room does not exist";
        }
        ChatRoom room = rooms.get(roomName);

        if (!room.userJoined(senderUser)) {
            return "failed: sender user is not a member of the room";
        }

        if (user.isBanned()) {
            return "failed: user is banned";
        }
        if (room.userBanned(user)) {
            return "failed: user is banned from the room";
        }
        if (room.userJoined(user)) {
            return "failed: user already joined the room";
        }

        if (!room.isAvailable()) {
            return "failed: room is full";
        }
        // if (room.getIsPublic()) {
            // return "failed: room is public";
        // }

        user.addRoomInvitation(senderUser, room);
        return "inviteUser";
    }

    /**
     * Get user's room invitations.
     *
     * @param username user's username
     * @return room invitations
     */
    public List<String> userRoomInvitations(String username) {
        return users.containsKey(username)
                ? users.get(username).getRoomInvitations().stream().map(invitation -> invitation.getRoom().getName()).toList()
                : List.of();
    }

    /**
     * Accept room invitation.
     *
     * @param username user's username
     * @param roomName room's name
     * @return status of accepting room invitation
     */
    public String acceptRoomInvitation(String username, String roomName) {
        if (!users.containsKey(username)) {
            return "failed: user does not exist";
        }
        User user = users.get(username);
        if (!rooms.containsKey(roomName)) {
            return "failed: room does not exist";
        }
        ChatRoom room = rooms.get(roomName);
        user.acceptRoomInvitation(room);
        return "success";
    }

    /**
     * Decline room invitation.
     *
     * @param username user's username
     * @param roomName room's name
     * @return status of declining room invitation
     */
    public String declineRoomInvitation(String username, String roomName) {
        if (!users.containsKey(username)) {
            return "failed: user does not exist";
        }
        User user = users.get(username);
        if (!rooms.containsKey(roomName)) {
            return "failed: room does not exist";
        }
        ChatRoom room = rooms.get(roomName);
        user.declineRoomInvitation(room);
        return "success";
    }

    /**
     * Create message.
     *
     * @param username user's username
     * @param roomName room's name
     * @param content  message content
     * @return status of creating message
     */
    public String createMessage(String username, String roomName, String content) {
        if (!users.containsKey(username)) {
            return "failed: user does not exist";
        }
        User user = users.get(username);
        if (!rooms.containsKey(roomName)) {
            return "failed: room does not exist";
        }
        ChatRoom room = rooms.get(roomName);
        room.createMessage(user, content);
        return "received";
    }

    /**
     * Edit message.
     *
     * @param roomName room's name
     * @param id       message id
     * @param content  message content
     * @return status of editing message
     */
    public String editMessage(String roomName, int id, String content) {
        if (!rooms.containsKey(roomName)) {
            return "failed: room does not exist";
        }
        ChatRoom room = rooms.get(roomName);
        room.editMessage(id, content);
        return "edited";
    }

    /**
     * Add heart to message.
     *
     * @param username user's username
     * @param roomName room's name
     * @param id       message id
     * @return status of adding heart to message
     */
    public String addHeartToMessage(String username, String roomName, int id) {
        if (!users.containsKey(username)) {
            return "failed: user does not exist";
        }
        User user = users.get(username);
        if (!rooms.containsKey(roomName)) {
            return "failed: room does not exist";
        }
        ChatRoom room = rooms.get(roomName);
        room.addReactionToMessage(id, user, "heart");
        return "success";
    }

    /**
     * Add reaction to message.
     *
     * @param username user's username
     * @param roomName room's name
     * @param id       message id
     * @param reactionType reaction type
     * @return status of adding heart to message
     */
    public String addReactionToMessage(String username, String roomName, int id, String reactionType) {
        if (!users.containsKey(username)) {
            return "failed: user does not exist";
        }
        User user = users.get(username);
        if (!rooms.containsKey(roomName)) {
            return "failed: room does not exist";
        }
        ChatRoom room = rooms.get(roomName);
        room.addReactionToMessage(id, user, reactionType);
        return "success";
    }

    /**
     * Delete message.
     *
     * @param roomName room's name
     * @param id       message id
     * @return status of deleting message
     */
    public String deleteMessage(String roomName, int id) {
        if (!rooms.containsKey(roomName)) {
            return "failed: room does not exist";
        }
        ChatRoom room = rooms.get(roomName);
        room.deleteMessage(id);
        return "deleteMessage";
    }

    /**
     * Get room's messages.
     *
     * @param roomName room's name
     * @return messages
     */
    public List<Message> roomMessages(String roomName) {
        return rooms.containsKey(roomName) ? rooms.get(roomName).getMessages() : List.of();
    }

    /**
     * Get all messages.
     *
     * @return all messages
     */
    public List<Message> allMessages() {
        return rooms.values().stream()
                .sorted(Comparator.comparing(ChatRoom::getName))
                .map(ChatRoom::getMessages)
                .reduce(List.of(), (a, b) -> Stream.concat(a.stream(), b.stream()).toList());
    }

    /**
     * Get user.
     *
     * @param username user's username
     * @return user
     */
    public User getUser(String username) {
        return users.getOrDefault(username, null);
    }

    /**
     * Send friend invitation.
     *
     * @param senderUsername inviting user's username
     * @param username       invited user's username
     * @return status of sending friend invitation
     */
    public String inviteFriend(String senderUsername, String username) {
        users.get(username).addFriendInvitation(users.get(senderUsername));
        return "success";
    }

    /**
     * Accept friend invitation.
     *
     * @param senderUsername inviting user's username
     * @param username       invited user's username
     * @return status of accepting friend invitation
     */
    public String acceptFriend(String senderUsername, String username) {
        users.get(username).acceptFriendInvitation(users.get(senderUsername));
        return "success";
    }

    /**
     * Decline friend invitation.
     *
     * @param senderUsername inviting user's username
     * @param username       invited user's username
     * @return status of declining friend invitation
     */
    public String declineFriend(String senderUsername, String username) {
        users.get(username).declineFriendInvitation(users.get(senderUsername));
        return "success";
    }

    /**
     * Get user's friends.
     *
     * @param username user's username
     * @return friends
     */
    public List<String> getFriends(String username) {
        return users.containsKey(username)
                ? users.get(username).getFriends().stream().map(User::getUsername).toList()
                : List.of();
    }

    /**
     * Get user's not friends.
     *
     * @param username user's username
     * @return not friends
     */
    public List<String> getNotFriends(String username) {
        return users.containsKey(username)
                ? users.values().stream().map(User::getUsername).filter(u -> !u.equals(username) && !getFriends(username).contains(u)).toList()
                : List.of();
    }

    /**
     * Create user id.
     *
     * @return id of a new user
     */
    private int createUserId() {
        try {
            return Collections.max(users.values().stream().map(User::getId).toList()) + 1;
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * Create room id.
     *
     * @return id of a new room
     */
    private int createRoomId() {
        try {
            return Collections.max(rooms.values().stream().map(ChatRoom::getId).toList()) + 1;
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * Get room.
     * @param roomName room's name
     * @return room
     */
    public ChatRoom getRoom(String roomName) {
        return rooms.getOrDefault(roomName, null);
    }

    /**
     * Get available public rooms.
     * @return available public rooms
     */
    public List<ChatRoom> getPublicRooms() {
        return rooms.values().stream().filter(room -> room.getIsPublic() && room.isAvailable()).sorted(Comparator.comparing(ChatRoom::getName)).toList();
    }

    /**
     * Get user's rooms.
     * @param username user's username
     * @return rooms
     */
    public List<ChatRoom> getUserRooms(String username) {
        return users.containsKey(username)
                ? users.get(username).getRooms()
                : List.of();
    }

    /**
     * Get user's messages.
     * @param username user's username
     * @param roomName room's name
     * @return messages
     */
    public List<Message> getUserMessages(String username, String roomName) {
        User user = users.get(username);
        ChatRoom room = rooms.get(roomName);

        return room.getMessages().stream().filter(message ->
            (message.getReceiver() == null || message.getReceiver().equals(user))
                && !user.getBlockedUsers().contains(message.getSender())
                && message.getTimestamp().isAfter(room.getMembersMap().get(user).getJoinTimestamp()))
                .toList();
    }

    /**
     * Get user's room invitations.
     * @param username user's username
     * @return room invitations
     */
    public List<RoomInvitation> getUserRoomInvitations(String username) {
        return users.containsKey(username)
                ? users.get(username).getRoomInvitations()
                : List.of();
    }

    /**
     * Get user's friend invitations.
     * @param username user's username
     * @return friend invitations
     */
    public List<FriendInvitation> getUserFriendInvitations(String username) {
        return users.containsKey(username)
                ? users.get(username).getFriendInvitations()
                : List.of();

    }

    /**
     * Block user.
     * @param senderName senderName who wants to block
     * @param receiverName receiverName who is blocked
     * @return status of blocking
     */
    public String blockUser(String senderName, String receiverName) {
        users.get(senderName).blockUser(users.get(receiverName));
        return "success";
    }

    /**
     * Unblock user.
     * @param senderName senderName who wants to unblock
     * @param receiverName receiverName who is unblocked
     * @return status of unblocking
     */
    public String unblockUser(String senderName, String receiverName) {
        users.get(senderName).unblockUser(users.get(receiverName));
        return "success";
    }

    /**
     * Get user's block list.
     * @param username user's username
     * @return block list
     */
    public List<String> getBlockList(String username) {
        return users.containsKey(username) ? users.get(username).getBlockedUsers().stream().map(User::getUsername).toList() : List.of();
    }

    /**
     * Get room's members.
     * @param roomName room's name
     * @return members
     */
    public List<User> getRoomMembers(String roomName) {
        return rooms.containsKey(roomName) ? rooms.get(roomName).getMembers() : List.of();
    }

    /**
     * Set user's information.
     * @param user user
     * @param newSchool new school
     * @param newInterests new interests
     * @param newDescription new description
     * @return status of setting information
     */
    public String setUserInfo(User user, String newSchool, List<String> newInterests, String newDescription) {
        user.setSchool(newSchool);
        user.setInterests(newInterests);
        user.setDescription(newDescription);
        return "success";
    }

    /**
     * Set user's description.
     * @param username user's username
     * @param description user's description
     * @return status of setting description
     */
    public String setUserDescription(String username, String description) {
        users.get(username).setDescription(description);
        return "success";
    }

    /**
     * Set user's active status.
     * @param username user's username
     * @param active user's active status
     * @return status of setting active status
     */
    public String setActive(String username, boolean active) {
        users.get(username).setActive(active);
        return "success";
    }

    /**
     * Set room's name.
     * @param roomName room's name
     * @param newName new room's name
     * @return status of setting name
     */
    public String setRoomName(String roomName, String newName) {
        if (rooms.containsKey(newName) && !newName.equals(roomName)) {
            return "failed: room already exists";
        }
        rooms.get(roomName).setName(newName);
        return "success";
    }

    /**
     * Set room's capacity.
     * @param roomName room's name
     * @param capacity room's capacity
     * @return status of setting capacity
     */
    public String setRoomCapacity(String roomName, int capacity) {
        rooms.get(roomName).setSize(capacity);
        return "success";
    }

    /**
     * Get all users.
     * @return all users
     */
    public List<User> allUsers() {
        return users.values().stream().sorted(Comparator.comparing(User::getUsername)).toList();
    }

    /**
     * Get al users filtered by same school and interests.
     *
     * @return all users
     */
    public List<User> allUsers(String username) {
        User user = getUser(username);
        return users.values().stream().filter(u -> {
            if (u == user) {
                return false;
            }
            if (u.getSchool().equals(user.getSchool())) {
                return true;
            }
            for (String interest: u.getInterests()) {
                if (user.getInterests().contains(interest)) {
                    return true;
                }
            }
            return false;
        }).sorted(Comparator.comparing(User::getUsername)).toList();
    }

    /**
     * Get latest message.
     * @param username user's username
     * @param roomName room'name
     * @return latest message
     */
    public Message getLatestMessage(String username, String roomName) {
        List<Message> messages = getUserMessages(username, roomName);
        return messages.isEmpty() ? null : messages.get(messages.size() - 1);
    }

}