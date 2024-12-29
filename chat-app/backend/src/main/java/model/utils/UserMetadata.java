package model.utils;

import java.time.LocalDateTime;

// Class representing metadata of a user that joined a room
public class UserMetadata {

    // Timestamp when the user joined the room
    private LocalDateTime joinTimestamp;

    // Flag whether the used is reported in the room
    private boolean reported;

    /**
     * Constructor.
     */
    public UserMetadata(LocalDateTime joinTimestamp, boolean reported) {
        this.joinTimestamp = joinTimestamp;
        this.reported = reported;
    }

    /**
     * Get reported status.
     * @return reported status
     */
    public boolean isReported() {
        return reported;
    }

    /**
     * Set report status.
     * @param reported reported status
     */
    public void setReported(boolean reported) {
        this.reported = reported;
    }

    /**
     * Get join timestamp.
     * @return join timestamp
     */
    public LocalDateTime getJoinTimestamp() {
        return joinTimestamp;
    }
}

