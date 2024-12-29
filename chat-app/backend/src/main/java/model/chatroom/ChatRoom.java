package model.chatroom;

import java.util.*;

import controller.ChatApiWebSocket;
import model.utils.Message;

import model.user.User;
import model.utils.*;
import java.time.LocalDateTime;

// Class representing room in the chat application
public class ChatRoom {

    // Room's id
    private int id;

    // Room's owner
    private User owner;

    // Room's name
    private String name;

    // Room's size
    private int size;

    // Room's public status
    private boolean isPublic;

    // Room's creation time
    private LocalDateTime creationTime;

    // Room's members
    private Map<User, UserMetadata> members;

    // Room's messages
    private Map<Integer, Message> messages;

    // Room's banned users
    private List<User> bannedUsers;

    // Room's avatar
    private int avatar;

    /**
     * Constructor.
     */
    public ChatRoom(int id, User owner, String name, int size, boolean isPublic) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.size = size;
        this.isPublic = isPublic;

        this.creationTime = LocalDateTime.now();
        this.members = new HashMap<>();
        this.messages = new HashMap<>();
        this.bannedUsers = new ArrayList<>();
        this.avatar = new Random().nextInt(1, 11);
    }

    /**
     * Get id.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Get owner.
     * @return owner
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Get name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get size.
     * @return size
     */
    public int getSize() {
        return members.size();
    }

    /**
     * Get capacity.
     * @return capacity
     */
    public int getCapacity() {
        return size;
    }

    /**
     * Get public status.
     * @return public status
     */
    public boolean getIsPublic() {
        return isPublic;
    }

    /**
     * Get available status.
     * @return available status
     */
    public boolean isAvailable() {
        return members.size() < size;
    }

    /**
     * Get avatar.
     * @return avatar
     */
    public int getAvatar() {
        return avatar;
    }

    /**
     * Get members.
     * @return members
     */
    public List<User> getMembers() {
        return members.keySet().stream().sorted(Comparator.comparing(User::getUsername)).toList();
    }

    /**
     * Get messages.
     * @return messages
     */
    public List<Message> getMessages() {
        return messages.values().stream().sorted(Comparator.comparing(Message::getTimestamp)).toList();
    }

    /**
     * Get reported users.
     * @return reported users
     */
    public List<User> getReportedUsers() {
        return members.entrySet().stream().filter(item->item.getValue().isReported())
                .map(Map.Entry::getKey).sorted(Comparator.comparing(User::getUsername)).toList();
    }

    /**
     * Get banned users.
     * @return banned users
     */
    public List<User> getBannedUsers() {
        return bannedUsers.stream().sorted(Comparator.comparing(User::getUsername)).toList();
    }

    /**
     * Get members map.
     * @return members map
     */
    public Map<User, UserMetadata> getMembersMap() {
        return members;
    }

    /**
     * Add user.
     * @param user user being added
     */
    public void addUser(User user) {
        members.put(user, new UserMetadata(LocalDateTime.now(), false));
    }

    /**
     * Remove user.
     * @param user user being removed
     * @param reason reason for removing
     */
    public void removeUser(User user, String reason) {
        members.remove(user);
        int id = createMessageId();
        reason = "User " + user.getUsername() + " left this room.";
        Message message = new Message(id, user, null, reason, 1);
        messages.put(id, message);
        ChatApiWebSocket.sendMessage(name, message);
    }

    /**
     * Report user.
     * @param reporterUser reporter user
     * @param reportedUser reported user
     * @param reason reason for reporting
     */
    public void reportUser(User reporterUser, User reportedUser, String reason) {
        members.get(reportedUser).setReported(true);
        int id = createMessageId();
        reason = "User " + reportedUser.getUsername() + " reported user " + reportedUser.getUsername() + " with reason: " + reason;
        Message message = new Message(id, reporterUser, owner, reason, 1);
        messages.put(id, message);
        ChatApiWebSocket.sendMessage(name, message);
    }

    /**
     * Ban user.
     * @param bannerUser banner user
     * @param bannedUser banned user
     * @param reason reason for banning
     */
    public void banUser(User bannerUser, User bannedUser, String reason) {
        members.remove(bannedUser);
        bannedUsers.add(bannedUser);
        int id = createMessageId();
        reason = "Admin banned user " + bannedUser.getUsername() + " from the room with reason: " + reason;
        Message message = new Message(id, bannerUser, null, reason, 1);
        messages.put(id, message);
        ChatApiWebSocket.sendMessage(name, message);
    }

    /**
     * Create message.
     * @param user user creating the message
     * @param content message content
     */
    public void createMessage(User user, String content) {
        int id = createMessageId();
        Message message = new Message(id, user, null, content, 0);
        messages.put(id, message);
        ChatApiWebSocket.sendMessage(name, message);
    }

    /**
     * Edit message.
     * @param id message id
     * @param content message content
     */
    public void editMessage(int id, String content) {
        if (messages.containsKey(id)) {
            messages.get(id).setContent(content);
        }
    }

    /**
     * Add reaction to message.
     * @param id message id
     * @param user user adding reaction
     * @param reaction type of reaction
     */
    public  void addReactionToMessage(int id, User user, String reaction) {
        if (messages.containsKey(id)) {
            messages.get(id).addReaction(user, reaction);
        }
    }

    /**
     * Delete message.
     * @param id message id
     */
    public  void deleteMessage(int id) {
        messages.remove(id);
    }

    /**
     * Check whether user has joined the room.
     * @param user user being checked
     * @return flag whether user has joined the room
     */
    public boolean userJoined(User user) {
        return members.containsKey(user);
    }

    /**
     * Check whether user has been banned from the room.
     * @param user user being checked
     * @return flag whether user has been banned from the room
     */
    public boolean userBanned(User user) {
        return bannedUsers.contains(user);
    }

    /**
     * Create message id.
     * @return id of a new message
     */
    private int createMessageId() {
        try {
            return Collections.max(messages.values().stream().map(Message::getId).toList()) + 1;
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * Set name.
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set size.
     * @param size size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Set owner.
     * @param owner owner
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * Get latest message.
     * @return latest message, or null if there are no messages
     */
    public Message getLatestMessage() {
        return messages.values().stream()
                .max(Comparator.comparing(Message::getTimestamp))
                .orElse(null);
    }

}
