package model.utils;

import model.user.User;

// Class representing invitation in the chat application
public abstract class Invitation {

    // User who sent the invitation
    protected User sender;

    // User who received the invitation
    protected User receiver;

    /**
     * Constructor.
     */
    public Invitation(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
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
}


