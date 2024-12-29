package model.utils;

import model.user.User;
import model.chatroom.ChatRoom;

// Class representing room invitation in the chat application
public class RoomInvitation extends Invitation {

    // Room for which the invitation is being sent
    private ChatRoom room;

    /**
     * Constructor.
     */
    public RoomInvitation(User sender, User recipient, ChatRoom room) {
        super(sender, recipient);
        this.room = room;
    }

    /**
     * Get room.
     * @return room
     */
    public ChatRoom getRoom() {
        return room;
    }

}
