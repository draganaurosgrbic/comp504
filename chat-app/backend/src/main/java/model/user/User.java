package model.user;

import java.util.Comparator;
import java.util.List;
import java.time.Period;
import java.util.ArrayList;
import model.utils.*;
import model.chatroom.*;
import java.time.LocalDate;
import java.util.Random;

// Class representing user in the chat application
public class User {

    // User's id
    private int id;

    // User's username
    private String username;

    // User's password
    private String password;

    // User's date of birth
    private LocalDate birthDate;

    // User's school
    private String school;

    // User's interests
    private List<String> interests;

    // User's rooms
    private List<ChatRoom> rooms;

    // User's owned rooms
    private List<ChatRoom> ownedRooms;

    // User's room invitations
    private List<RoomInvitation> roomInvitations;

    // User's friends
    private List<User> friends;

    // User's friend invitations
    private List<FriendInvitation> friendInvitations;

    // User's banned status
    private boolean banned;

    // User's avatar index
    private int avatar;

    // User's blocked users
    private List<User> blockedUsers;

    // User's self description
    private String selfDescription;

    // User's active status
    private boolean active;

    // Hate speech counter
    private int hateSpeechCounter;

    /**
     * Constructor.
     */
    public User(int id, String username, String password, LocalDate birthDate, String school, List<String> interests) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.school = school;
        this.interests = interests;

        this.rooms = new ArrayList<>();
        this.roomInvitations = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.friendInvitations = new ArrayList<>();
        this.banned = false;

        this.avatar = new Random().nextInt(1, 11);
        this.blockedUsers = new ArrayList<>();
        this.active = true;
        this.ownedRooms = new ArrayList<>();
        this.selfDescription = "";
    }

    /**
     * Get id.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Get username.
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get password.
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get date of birth.
     * @return date of birth
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Get school.
     * @return school
     */
    public String getSchool() {
        return school;
    }

    /**
     * Get interests.
     * @return interests
     */
    public List<String> getInterests() {
        return interests;
    }

    /**
     * Get banned status.
     * @return banned status
     */
    public boolean isBanned() {
        return banned;
    }

    /**
     * Get rooms.
     * @return rooms
     */
    public List<ChatRoom> getRooms() {
        return rooms.stream().sorted(Comparator.comparing(ChatRoom::getName)).toList();
    }

    /**
     * Get room invitations.
     * @return room invitations
     */
    public List<RoomInvitation> getRoomInvitations() {
        return roomInvitations.stream().sorted(Comparator.comparing(invitation -> invitation.getRoom().getName())).toList();
    }

    /**
     * Get friends.
     * @return friends
     */
    public List<User> getFriends() {
        return friends.stream().sorted(Comparator.comparing(User::getUsername)).toList();
    }

    /**
     * Get friend invitations.
     * @return friend invitations
     */
    public List<FriendInvitation> getFriendInvitations() {
        return friendInvitations.stream().sorted(Comparator.comparing(invitation -> invitation.getSender().getUsername())).toList();
    }

    /**
     * Set banned status.
     * @param banned banned status
     */
    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    /**
     * Add room.
     * @param room room being added
     */
    public void addRoom(ChatRoom room) {
        rooms.add(room);
    }

    /**
     * Remove room.
     * @param room room being removed
     */
    public void removeRoom(ChatRoom room) {
        rooms.remove(room);
    }

    /**
     * Add room invitation.
     * @param user user sending the invitation
     * @param room room for which the invitation is being added
     */
    public void addRoomInvitation(User user, ChatRoom room) {
        roomInvitations.add(new RoomInvitation(user, this, room));
    }

    /**
     * Accept room invitation.
     * @param room room for which the invitation is being accepted
     */
    public void acceptRoomInvitation(ChatRoom room) {
        List<RoomInvitation> invitations = roomInvitations.stream().filter(invitation -> invitation.getRoom().equals(room)).toList();
        for (RoomInvitation ri: invitations) {
            roomInvitations.remove(ri);
        }
        if (!invitations.isEmpty()) {
            rooms.add(room);
            room.addUser(this);
        }
    }

    /**
     * Decline room invitation.
     * @param room room for which the invitation is being declined
     */
    public void declineRoomInvitation(ChatRoom room) {
        List<RoomInvitation> invitations = roomInvitations.stream().filter(invitation -> invitation.getRoom().equals(room)).toList();
        for (RoomInvitation ri: invitations) {
            roomInvitations.remove(ri);
        }
    }

    /**
     * Add friend invitation.
     * @param user user for which the invitation is being added
     */
    public void addFriendInvitation(User user) {
        friendInvitations.add(new FriendInvitation(user, this));
    }

    /**
     * Accept friend invitation.
     * @param user user for which the friend invitation is being accepted
     */
    public void acceptFriendInvitation(User user) {
        List<FriendInvitation> invitations = friendInvitations.stream().filter(invitation -> invitation.getSender().equals(user)).toList();
        for (FriendInvitation fi: invitations) {
            friendInvitations.remove(fi);
        }
        friends.add(user);
        user.friends.add(this);
    }

    /**
     * Decline friend invitation.
     * @param user user for which the friend invitation is being declined
     */
    public void declineFriendInvitation(User user) {
        List<FriendInvitation> invitations = friendInvitations.stream().filter(invitation -> invitation.getSender().equals(user)).toList();
        for (FriendInvitation fi: invitations) {
            friendInvitations.remove(fi);
        }
    }

    /**
     * Block user.
     * @param user user being blocked
     */
    public void blockUser(User user) {
        blockedUsers.add(user);
        friends.remove(user);
        user.friends.remove(this);
        List<FriendInvitation> invitations = friendInvitations.stream().filter(invitation -> invitation.getSender().equals(user)).toList();
        for (FriendInvitation fi: invitations) {
            friendInvitations.remove(fi);
        }

        invitations = user.friendInvitations.stream().filter(invitation -> invitation.getSender().equals(this)).toList();
        for (FriendInvitation fi: invitations) {
            user.friendInvitations.remove(fi);
        }
    }

    /**
     * Unblock user.
     * @param user user being unblocked
     */
    public void unblockUser(User user) {
        blockedUsers.remove(user);
    }

    /**
     * Get avatar.
     * @return avatar
     */
    public int getAvatar() {
        return avatar;
    }

    /**
     * Get blocked users.
     * @return blocked users
     */
    public List<User> getBlockedUsers() {
        return blockedUsers;
    }

    /**
     * Get active status.
     * @return active status
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Get self description.
     * @return self description
     */
    public String getSelfDescription() {
        return selfDescription;
    }

    /**
     * Get age.
     * @return age
     */
    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    /**
     * Add owned room.
     * @param room room being added
     */
    public void addOwnedRoom(ChatRoom room) {
        ownedRooms.add(room);
    }

    /**
     * Set self description.
     * @param selfDescription self description
     */
    public void setDescription(String selfDescription) {
        this.selfDescription = selfDescription;
    }

    /**
     * Set school.
     * @param school school
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * Set the interests.
     * @param interests interests
     */
    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    /**
     * Set active status.
     * @param active active status
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Increment hate speech counter.
     */
    public void incrementHateSpeechCounter() {
        ++hateSpeechCounter;
        if (hateSpeechCounter >= 10) {
            banned = true;
        }
    }

    /**
     * Get hate speech counter.
     * @return hate speech counter
     */
    public int getHateSpeechCounter() {
        return hateSpeechCounter;
    }

}
