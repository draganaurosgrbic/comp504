package model;

import model.chatroom.ChatRoom;
import model.user.User;
import model.utils.FriendInvitation;
import model.utils.Message;
import model.utils.RoomInvitation;

import java.time.LocalDate;
import java.util.List;

// Class representing dispatch adapter for communication with the model
public class DispatchAdapter {

    // Store
    private ChatWorldStore store;

    // Singleton instance
    private static DispatchAdapter ONLY;

    /**
     * Get the singleton instance.
     */
    public static DispatchAdapter getOnly() {
        if (ONLY == null) {
            ONLY = new DispatchAdapter();
        }
        return ONLY;
    }

    /**
     * Constructor.
     */
    private DispatchAdapter() {
    }

    /**
     * Get store.
     *
     * @return store
     */
    public ChatWorldStore getStore() {
        return store;
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
        return store.register(username, password, birthDate, school, interests);
    }

    /**
     * Login user.
     *
     * @param username user's username
     * @param password user's password
     * @return status of login
     */
    public String login(String username, String password) {
        return store.login(username, password);
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
        return store.createRoom(username, name, size, isPublic);
    }

    /**
     * Join room.
     *
     * @param username user's username
     * @param roomName room's name
     * @return status of joining room
     */
    public String joinRoom(String username, String roomName) {
        return store.joinRoom(username, roomName);
    }

    /**
     * Get available public rooms.
     *
     * @return available public rooms
     */
    public List<String> publicRooms() {
        return store.publicRooms();
    }

    /**
     * Get user's rooms.
     *
     * @param username user's username
     * @return rooms
     */
    public List<String> userRooms(String username) {
        return store.userRooms(username);
    }

    /**
     * Get room's admin.
     *
     * @param roomName room's name
     * @return admin
     */
    public String roomAdmin(String roomName) {
        return store.roomAdmin(roomName);
    }

    /**
     * Get room's members.
     *
     * @param roomName room's name
     * @return members
     */
    public List<String> roomMembers(String roomName) {
        return store.roomMembers(roomName);
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
        return store.reportUser(senderUsername, username, roomName, reason);
    }

    /**
     * Get room's reported users.
     *
     * @param roomName room's name
     * @return reported users
     */
    public List<String> reportedUsers(String roomName) {
        return store.reportedUsers(roomName);
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
        return store.banUser(senderUsername, username, roomName, reason);
    }

    /**
     * Ban user globally.
     *
     * @param username user's username
     * @return status of banning user
     */
    public String banUser(String username) {
        return store.banUser(username);
    }

    /**
     * Get room's banned users.
     *
     * @param roomName room's name
     * @return banned users
     */
    public List<String> bannedUsers(String roomName) {
        return store.bannedUsers(roomName);
    }

    /**
     * Get user's banned status.
     *
     * @param username user's username
     * @return banned status
     */
    public boolean userBanned(String username) {
        return store.userBanned(username);
    }

    /**
     * Leave room.
     *
     * @param username user's username
     * @param roomName room's name
     * @return status of leaving room
     */
    public String leaveRoom(String username, String roomName) {
        return store.leaveRoom(username, roomName);
    }

    /**
     * Leave all rooms.
     *
     * @param username user's username
     * @return status of leaving all rooms
     */
    public String leaveRooms(String username) {
        return store.leaveRooms(username);
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
        return store.inviteToRoom(senderUsername, username, roomName);
    }

    /**
     * Get user's room invitations.
     *
     * @param username user's username
     * @return room invitations
     */
    public List<String> userRoomInvitations(String username) {
        return store.userRoomInvitations(username);
    }

    /**
     * Accept room invitation.
     *
     * @param username user's username
     * @param roomName room's name
     * @return status of accepting room invitation
     */
    public String acceptRoomInvitation(String username, String roomName) {
        return store.acceptRoomInvitation(username, roomName);
    }

    /**
     * Decline room invitation.
     *
     * @param username user's username
     * @param roomName room's name
     * @return status of declining room invitation
     */
    public String declineRoomInvitation(String username, String roomName) {
        return store.declineRoomInvitation(username, roomName);
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
        return store.createMessage(username, roomName, content);
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
        return store.editMessage(roomName, id, content);
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
        return store.addHeartToMessage(username, roomName, id);
    }

    /**
     * Add reaction to message.
     *
     * @param username     user's username
     * @param roomName     room's name
     * @param id           message id
     * @param reactionType type of reaction
     * @return status of adding reaction to message
     */
    public String addReactionToMessage(String username, String roomName, int id, String reactionType) {
        return store.addReactionToMessage(username, roomName, id, reactionType);
    }

    /**
     * Delete message.
     *
     * @param roomName room's name
     * @param id       message id
     * @return status of deleting message
     */
    public String deleteMessage(String roomName, int id) {
        return store.deleteMessage(roomName, id);
    }

    /**
     * Get room's messages.
     *
     * @param roomName room's name
     * @return messages
     */
    public List<Message> roomMessages(String roomName) {
        return store.roomMessages(roomName);
    }

    /**
     * Get all messages.
     *
     * @return all messages
     */
    public List<Message> allMessages() {
        return store.allMessages();
    }

    /**
     * Get user.
     *
     * @param username user's username
     * @return user
     */
    public User getUser(String username) {
        return store.getUser(username);
    }

    /**
     * Send friend invitation.
     *
     * @param senderUsername inviting user's username
     * @param username       invited user's username
     * @return status of sending friend invitation
     */
    public String inviteFriend(String senderUsername, String username) {
        return store.inviteFriend(senderUsername, username);
    }

    /**
     * Accept friend invitation.
     *
     * @param senderUsername inviting user's username
     * @param username       invited user's username
     * @return status of accepting friend invitation
     */
    public String acceptFriend(String senderUsername, String username) {
        return store.acceptFriend(senderUsername, username);
    }

    /**
     * Decline friend invitation.
     *
     * @param senderUsername inviting user's username
     * @param username       invited user's username
     * @return status of declining friend invitation
     */
    public String declineFriend(String senderUsername, String username) {
        return store.declineFriend(senderUsername, username);
    }

    /**
     * Get user's friends.
     *
     * @param username user's username
     * @return friends
     */
    public List<String> getFriends(String username) {
        return store.getFriends(username);
    }

    /**
     * Get user's not friends.
     *
     * @param username user's username
     * @return not friends
     */
    public List<String> getNotFriends(String username) {
        return store.getNotFriends(username);
    }

    /**
     * Get room.
     *
     * @param roomName room's name
     * @return room
     */
    public ChatRoom getRoom(String roomName) {
        return store.getRoom(roomName);
    }

    /**
     * Get available public rooms.
     *
     * @return available public rooms
     */
    public List<ChatRoom> getPublicRooms() {
        return store.getPublicRooms();
    }

    /**
     * Get user's rooms.
     *
     * @param username user's username
     * @return rooms
     */
    public List<ChatRoom> getUserRooms(String username) {
        return store.getUserRooms(username);
    }

    /**
     * Get user's messages.
     *
     * @param username user's username
     * @param roomName room's name
     * @return messages
     */
    public List<Message> getUserMessages(String username, String roomName) {
        return store.getUserMessages(username, roomName);
    }

    /**
     * Get user's room invitations.
     *
     * @param username user's username
     * @return room invitations
     */
    public List<RoomInvitation> getUserRoomInvitations(String username) {
        return store.getUserRoomInvitations(username);
    }

    /**
     * Get user's friend invitations.
     *
     * @param username user's username
     * @return friend invitations
     */
    public List<FriendInvitation> getUserFriendInvitations(String username) {
        return store.getUserFriendInvitations(username);
    }


    /**
     * Block user.
     *
     * @param senderName   senderName who wants to block
     * @param receiverName receiverName who is blocked
     * @return status of blocking
     */
    public String blockUser(String senderName, String receiverName) {
        return store.blockUser(senderName, receiverName);
    }

    /**
     * Unblock user.
     *
     * @param senderName   senderName who wants to unblock
     * @param receiverName receiverName who is unblocked
     * @return status of unblocking
     */
    public String unblockUser(String senderName, String receiverName) {
        return store.unblockUser(senderName, receiverName);
    }

    /**
     * Get user's block list.
     *
     * @param username user's username
     * @return block list
     */
    public List<String> getBlockList(String username) {
        return store.getBlockList(username);
    }

    /**
     * Get room's members.
     *
     * @param roomName room's name
     * @return members
     */
    public List<User> getRoomMembers(String roomName) {
        return store.getRoomMembers(roomName);
    }

    /**
     * Set user's information.
     *
     * @param user           user
     * @param newSchool      new school
     * @param newInterests   new interests
     * @param newDescription new description
     * @return status of setting information
     */
    public String setUserInfo(User user, String newSchool, List<String> newInterests, String newDescription) {
        return store.setUserInfo(user, newSchool, newInterests, newDescription);
    }

    /**
     * Set user's description.
     *
     * @param username    user's username
     * @param description user's description
     * @return status of setting description
     */
    public String setUserDescription(String username, String description) {
        return store.setUserDescription(username, description);
    }

    /**
     * Set user's active status.
     *
     * @param username user's username
     * @param active   user's active status
     * @return status of setting active status
     */
    public String setActive(String username, boolean active) {
        return store.setActive(username, active);
    }

    /**
     * Set room's name.
     *
     * @param roomName room's name
     * @param newName  new room's name
     * @return status of setting name
     */
    public String setRoomName(String roomName, String newName) {
        return store.setRoomName(roomName, newName);
    }

    /**
     * Set room's capacity.
     *
     * @param roomName room's name
     * @param capacity room's capacity
     * @return status of setting capacity
     */
    public String setRoomCapacity(String roomName, int capacity) {
        return store.setRoomCapacity(roomName, capacity);
    }

    /**
     * Set store.
     *
     * @param store store
     */
    public void setStore(ChatWorldStore store) {
        this.store = store;
    }

    /**
     * Get al users.
     *
     * @return all users
     */
    public List<User> allUsers() {
        return store.allUsers();
    }

    /**
     * Get al users filtered by same school and interests.
     *
     * @return all users
     */
    public List<User> allUsers(String username) {
        return store.allUsers(username);
    }

    /**
     * Get latest message.
     * @param username user's username
     * @param roomName room'name
     * @return latest message
     */
    public Message getLatestMessage(String username, String roomName) {
        return store.getLatestMessage(username, roomName);
    }
}