package model.utils;

import model.user.User;

// Class representing friend invitation in the chat application
public class FriendInvitation extends Invitation {

    /**
     * Constructor.
     */
    public FriendInvitation(User sender, User recipient) {
        super(sender, recipient);
    }

}
