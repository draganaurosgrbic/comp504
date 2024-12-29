package model.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.chatroom.ChatRoom;
import model.user.User;

// Class representing message in the chat application
public class Message {

    // Message id
    private int id;

    // Message sender
    private User sender;

    // Message receiver
    private User receiver;

    // Message content
    private String content;

    // Message status
    // 0: normal user chat
    // 1: reason for removing user, reporting user, banning user
    // 2: hate speech
    private int status;

    // Message timestamp
    private LocalDateTime timestamp;

    // Message links
    private List<String> links;

    // Message reactions
    private Map<User, String> reactions;

    // Hate vocabulary
    private static final List<String> HATE_VOCABULARY = List.of("hate speech");

    /**
     * Constructor.
     */
    public Message(int id, User sender, User receiver, String content, int status) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.status = status;

        this.timestamp = LocalDateTime.now();
        this.links = new ArrayList<>();
        this.reactions = new HashMap<>();

        extractLinks();
        detectHateSpeech();
    }

    /**
     * Get id.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Get sender.
     * @return sender
     */
    public User getSender() {
        return sender;
    }

    /**
     * Get receiver.
     * @return receiver
     */
    public User getReceiver() {
        return receiver;
    }

    /**
     * Get content.
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * Get status.
     * @return status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Get timestamp.
     * @return timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Get reactions.
     * @return reactions
     */
    public Map<User, String> getReactions() {
        return reactions;
    }

    /**
     * Set content.
     * @param content content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Add reaction to message.
     * @param user user adding reaction
     * @param reaction type of reaction
     */
    public void addReaction(User user, String reaction) {
        reactions.put(user, reaction);
    }

    /**
     * Extracts links from message content.
     */
    private void extractLinks() {}

    /**
     * Detect hate speech in message content.
     */
    private void detectHateSpeech() {
        for (String word : HATE_VOCABULARY) {
            if (content.toLowerCase().contains(word.toLowerCase())) {
                status = 2;
                sender.incrementHateSpeechCounter();
            }
        }
    }

}
