package model;

import junit.framework.TestCase;
import model.chatroom.ChatRoom;
import model.user.User;
import model.utils.FriendInvitation;
import model.utils.Message;
import model.utils.RoomInvitation;

import java.time.LocalDate;
import java.util.List;

public class ChatWorldStoreTest extends TestCase {
    private final String TEST_USER_1 = "user1";
    private final String TEST_USER_2 = "user2";
    private final String TEST_USER_3 = "user3";
    private final String TEST_USER_4 = "user4";

    private final String TEST_PASSWORD = "password";
    private final String TEST_PASSWORD_2 = "password2";
    private final LocalDate TEST_BIRTH_DATE = LocalDate.now();
    private final String TEST_SCHOOL = "school";
    private final List<String> TEST_INTERESTS = List.of("interest1", "interest2");

    private final String TEST_ROOM_1 = "room1";
    private final String TEST_ROOM_2 = "room2";
    private final String TEST_ROOM_3 = "room3";
    private final String TEST_ROOM_4 = "room4";

    private final int TEST_ROOM_SIZE = 2;
    private final boolean TEST_ROOM_IS_PUBLIC = true;
    private final boolean TEST_ROOM_IS_NOT_PUBLIC = false;

    private final String TEST_MESSAGE_CONTENT = "Message content.";
    private final String TEST_MESSAGE_CONTENT_2 = "Message content 2.";

    private final String TEST_REPORT_REASON = "User is impolite.";
    private final String TEST_BAN_REASON = "User is impolite.";
    private final String TEST_LEAVE_ROOM_REASON = "User left this room.";
    private final String TEST_LEAVE_ROOMS_REASON = "User left all rooms.";

    private final String STATUS_SUCCESS = "success";
    private final String STATUS_REPORT_USER = "reportUser";
    private final String STATUS_BAN_USER = "banUser";
    private final String STATUS_LEAVE_ROOM = "leaveRoom";
    private final String STATUS_LEAVE_ROOMS = "LeaveAllRoom";
    private final String STATUS_INVITE_USER = "inviteUser";
    private final String STATUS_MESSAGE_CREATE = "received";
    private final String STATUS_MESSAGE_EDIT = "edited";
    private final String STATUS_MESSAGE_DELETE = "deleteMessage";


    public void testRooms() {
        DispatchAdapter adapter = DispatchAdapter.getOnly();
        adapter.setStore(new ChatWorldStore());

        String status = adapter.createRoom(TEST_USER_1, TEST_ROOM_1, TEST_ROOM_SIZE, TEST_ROOM_IS_PUBLIC);
        assertEquals(status, "failed: user does not exist");

        status = adapter.register(TEST_USER_1, TEST_PASSWORD, TEST_BIRTH_DATE, TEST_SCHOOL, TEST_INTERESTS);
        assertEquals(status, STATUS_SUCCESS);

        status = adapter.createRoom(TEST_USER_1, TEST_ROOM_1, 0, TEST_ROOM_IS_PUBLIC);
        assertEquals(status, "failed: size is not greater than zero");

        status = adapter.createRoom(TEST_USER_1, TEST_ROOM_1, TEST_ROOM_SIZE, TEST_ROOM_IS_PUBLIC);
        assertEquals(status, STATUS_SUCCESS);

        ChatRoom room = adapter.getStore().getRooms().get(0);
        assertEquals(room.getId(), 1);
        assertEquals(room.getOwner(), adapter.getStore().getUsers().get(0));
        assertEquals(room.getName(), TEST_ROOM_1);
        assertEquals(room.getSize(), TEST_ROOM_SIZE - 1);
        assertEquals(room.getIsPublic(), TEST_ROOM_IS_PUBLIC);

        status = adapter.createRoom(TEST_USER_1, TEST_ROOM_1, TEST_ROOM_SIZE, TEST_ROOM_IS_PUBLIC);
        assertEquals(status, "failed: room already exists");

        status = adapter.joinRoom(TEST_USER_2, TEST_ROOM_1);
        assertEquals(status, "failed: user does not exist");

        status = adapter.register(TEST_USER_2, TEST_PASSWORD, TEST_BIRTH_DATE, TEST_SCHOOL, TEST_INTERESTS);
        assertEquals(status, STATUS_SUCCESS);

        status = adapter.joinRoom(TEST_USER_2, TEST_ROOM_2);
        assertEquals(status, "failed: room does not exist");

        status = adapter.joinRoom(TEST_USER_2, TEST_ROOM_1);
        assertEquals(status, STATUS_SUCCESS);

        assertEquals(adapter.getStore().getUsers().get(0).getRooms().size(), 1);
        assertEquals(adapter.getStore().getUsers().get(0).getRooms().get(0), adapter.getStore().getRooms().get(0));
        assertEquals(adapter.getStore().getUsers().get(1).getRooms().size(), 1);
        assertEquals(adapter.getStore().getUsers().get(1).getRooms().get(0), adapter.getStore().getRooms().get(0));
        assertEquals(adapter.getStore().getRooms().get(0).getMembers().size(), 2);
        assertEquals(adapter.getStore().getRooms().get(0).getMembers().get(0), adapter.getStore().getUsers().get(0));
        assertEquals(adapter.getStore().getRooms().get(0).getMembers().get(1), adapter.getStore().getUsers().get(1));

        status = adapter.joinRoom(TEST_USER_2, TEST_ROOM_1);
        assertEquals(status, "failed: user already joined the room");

        status = adapter.inviteToRoom(TEST_USER_1, TEST_USER_2, TEST_ROOM_1);
        assertEquals(status, "failed: user already joined the room");

        status = adapter.register(TEST_USER_3, TEST_PASSWORD, TEST_BIRTH_DATE, TEST_SCHOOL, TEST_INTERESTS);
        assertEquals(status, STATUS_SUCCESS);

        status = adapter.joinRoom(TEST_USER_3, TEST_ROOM_1);
        assertEquals(status, "failed: room is full");

        status = adapter.inviteToRoom(TEST_USER_1, TEST_USER_3, TEST_ROOM_1);
        assertEquals(status, "failed: room is full");

        status = adapter.createRoom(TEST_USER_1, TEST_ROOM_2, TEST_ROOM_SIZE, TEST_ROOM_IS_NOT_PUBLIC);
        assertEquals(status, STATUS_SUCCESS);

        status = adapter.joinRoom(TEST_USER_3, TEST_ROOM_2);
        assertEquals(status, "failed: room is private");

        status = adapter.roomAdmin(TEST_ROOM_1);
        assertEquals(status, TEST_USER_1);

        status = adapter.roomAdmin(TEST_ROOM_3);
        assertEquals(status, "failed: room does not exist");

        List<String> users = adapter.roomMembers(TEST_ROOM_1);
        assertEquals(users.size(), 2);
        assertEquals(users.get(0), TEST_USER_1);
        assertEquals(users.get(1), TEST_USER_2);
        assertTrue(adapter.roomMembers(TEST_ROOM_3).isEmpty());

        status = adapter.reportUser(TEST_USER_4, TEST_USER_2, TEST_ROOM_1, TEST_REPORT_REASON);
        assertEquals(status, "failed: sender user does not exist");

        status = adapter.reportUser(TEST_USER_1, TEST_USER_4, TEST_ROOM_1, TEST_REPORT_REASON);
        assertEquals(status, "failed: user does not exist");

        status = adapter.reportUser(TEST_USER_1, TEST_USER_2, TEST_ROOM_3, TEST_REPORT_REASON);
        assertEquals(status, "failed: room does not exist");

        status = adapter.reportUser(TEST_USER_1, TEST_USER_3, TEST_ROOM_1, TEST_REPORT_REASON);
        assertEquals(status, "failed: user is not in the room");

        status = adapter.reportUser(TEST_USER_1, TEST_USER_2, TEST_ROOM_1, TEST_REPORT_REASON);
        assertEquals(status, STATUS_REPORT_USER);

        assertEquals(adapter.getStore().getRooms().get(0).getMembers().size(), 2);
        assertEquals(adapter.getStore().getRooms().get(0).getMembers().get(0), adapter.getStore().getUsers().get(0));
        assertEquals(adapter.getStore().getRooms().get(0).getMembers().get(1), adapter.getStore().getUsers().get(1));
        assertTrue(adapter.getStore().getRooms().get(0).getMembersMap().get(adapter.getStore().getUsers().get(1)).isReported());
        assertEquals(adapter.getStore().getRooms().get(0).getMessages().size(), 1);

        Message message = adapter.getStore().getRooms().get(0).getMessages().get(0);
        assertEquals(message.getId(), 1);
        assertEquals(message.getSender(), adapter.getStore().getUsers().get(0));
        // assertEquals(message.getContent(), TEST_REPORT_REASON);
        assertEquals(message.getStatus(), 1);

        users = adapter.reportedUsers(TEST_ROOM_1);
        assertEquals(users.size(), 1);
        assertEquals(users.get(0), TEST_USER_2);
        assertTrue(adapter.reportedUsers(TEST_ROOM_3).isEmpty());

        status = adapter.banUser(TEST_USER_4, TEST_USER_2, TEST_ROOM_1, TEST_REPORT_REASON);
        assertEquals(status, "failed: sender user does not exist");

        status = adapter.banUser(TEST_USER_1, TEST_USER_4, TEST_ROOM_1, TEST_REPORT_REASON);
        assertEquals(status, "failed: user does not exist");

        status = adapter.banUser(TEST_USER_1, TEST_USER_2, TEST_ROOM_3, TEST_REPORT_REASON);
        assertEquals(status, "failed: room does not exist");

        status = adapter.banUser(TEST_USER_1, TEST_USER_3, TEST_ROOM_1, TEST_REPORT_REASON);
        assertEquals(status, "failed: user is not in the room");

        status = adapter.banUser(TEST_USER_1, TEST_USER_2, TEST_ROOM_1, TEST_REPORT_REASON);
        assertEquals(status, STATUS_BAN_USER);




        assertEquals(adapter.getStore().getRooms().get(0).getMembers().size(), 1);
        assertEquals(adapter.getStore().getRooms().get(0).getMembers().get(0), adapter.getStore().getUsers().get(0));
        assertEquals(adapter.getStore().getRooms().get(0).getBannedUsers().size(), 1);
        assertEquals(adapter.getStore().getRooms().get(0).getMessages().size(), 2);

        message = adapter.getStore().getRooms().get(0).getMessages().get(1);
        assertEquals(message.getId(), 2);
        assertEquals(message.getSender(), adapter.getStore().getUsers().get(0));
        // assertEquals(message.getContent(), TEST_BAN_REASON);
        assertEquals(message.getStatus(), 1);

        users = adapter.bannedUsers(TEST_ROOM_1);
        assertEquals(users.size(), 1);
        assertEquals(users.get(0), TEST_USER_2);
        assertTrue(adapter.bannedUsers(TEST_ROOM_3).isEmpty());
        assertTrue(adapter.reportedUsers(TEST_ROOM_1).isEmpty());


        status = adapter.joinRoom(TEST_USER_2, TEST_ROOM_1);
        assertEquals(status, "failed: user is banned from the room");

        status = adapter.inviteToRoom(TEST_USER_1, TEST_USER_2, TEST_ROOM_1);
        assertEquals(status, "failed: user is banned from the room");

        status = adapter.banUser(TEST_USER_4);
        assertEquals(status, "failed: user does not exist");

        status = adapter.banUser(TEST_USER_2);
        assertEquals(status, STATUS_SUCCESS);
        assertTrue(adapter.getStore().getUsers().get(1).isBanned());
        assertTrue(adapter.userBanned(TEST_USER_2));
        assertFalse(adapter.userBanned(TEST_USER_4));

        status = adapter.joinRoom(TEST_USER_2, TEST_ROOM_1);
        assertEquals(status, "failed: user is banned");

        status = adapter.inviteToRoom(TEST_USER_1, TEST_USER_2, TEST_ROOM_1);
        assertEquals(status, "failed: user is banned");

        status = adapter.inviteToRoom(TEST_USER_4, TEST_USER_1, TEST_ROOM_1);
        assertEquals(status, "failed: sender user does not exist");

        status = adapter.inviteToRoom(TEST_USER_1, TEST_USER_4, TEST_ROOM_1);
        assertEquals(status, "failed: user does not exist");

        status = adapter.inviteToRoom(TEST_USER_1, TEST_USER_1, TEST_ROOM_3);
        assertEquals(status, "failed: room does not exist");

        status = adapter.inviteToRoom(TEST_USER_2, TEST_USER_1, TEST_ROOM_1);
        assertEquals(status, "failed: sender user is not a member of the room");

        assertTrue(adapter.userRooms(TEST_USER_4).isEmpty());
        assertTrue(adapter.userRoomInvitations(TEST_USER_4).isEmpty());

        status = adapter.leaveRoom(TEST_USER_4, TEST_ROOM_1);
        assertEquals(status, "failed: user does not exist");

        status = adapter.leaveRoom(TEST_USER_1, TEST_ROOM_3);
        assertEquals(status, "failed: room does not exist");

        status = adapter.leaveRooms(TEST_USER_4);
        assertEquals(status, "failed: user does not exist");

        status = adapter.register(TEST_USER_4, TEST_PASSWORD, TEST_BIRTH_DATE, TEST_SCHOOL, TEST_INTERESTS);
        assertEquals(status, STATUS_SUCCESS);

        status = adapter.createRoom(TEST_USER_1, TEST_ROOM_3, TEST_ROOM_SIZE, TEST_ROOM_IS_PUBLIC);
        assertEquals(status, STATUS_SUCCESS);

        status = adapter.createRoom(TEST_USER_1, TEST_ROOM_4, TEST_ROOM_SIZE, TEST_ROOM_IS_NOT_PUBLIC);
        assertEquals(status, STATUS_SUCCESS);

        // status = adapter.inviteToRoom(TEST_USER_1, TEST_USER_3, TEST_ROOM_1);
        // assertEquals(status, "failed: room is public");

        status = adapter.inviteToRoom(TEST_USER_1, TEST_USER_3, TEST_ROOM_2);
        assertEquals(status, STATUS_INVITE_USER);

        assertEquals(adapter.getStore().getUsers().get(2).getRoomInvitations().size(), 1);
        RoomInvitation invitation = adapter.getStore().getUsers().get(2).getRoomInvitations().get(0);
        assertEquals(invitation.getSender(), adapter.getStore().getUsers().get(0));
        assertEquals(invitation.getReceiver(), adapter.getStore().getUsers().get(2));
        assertEquals(invitation.getRoom(), adapter.getStore().getRooms().get(1));

        status = adapter.inviteToRoom(TEST_USER_1, TEST_USER_3, TEST_ROOM_4);
        assertEquals(status, STATUS_INVITE_USER);

        assertEquals(adapter.getStore().getUsers().get(2).getRoomInvitations().size(), 2);
        invitation = adapter.getStore().getUsers().get(2).getRoomInvitations().get(1);
        assertEquals(invitation.getSender(), adapter.getStore().getUsers().get(0));
        assertEquals(invitation.getReceiver(), adapter.getStore().getUsers().get(2));
        assertEquals(invitation.getRoom(), adapter.getStore().getRooms().get(3));

        List<String> rooms = adapter.userRoomInvitations(TEST_USER_3);
        assertEquals(rooms.size(), 2);
        assertEquals(rooms.get(0), TEST_ROOM_2);
        assertEquals(rooms.get(1), TEST_ROOM_4);

        status = adapter.acceptRoomInvitation("", TEST_ROOM_2);
        assertEquals(status, "failed: user does not exist");

        status = adapter.acceptRoomInvitation(TEST_USER_3, "");
        assertEquals(status, "failed: room does not exist");

        status = adapter.acceptRoomInvitation(TEST_USER_3, TEST_ROOM_2);
        assertEquals(status, STATUS_SUCCESS);

        rooms = adapter.userRoomInvitations(TEST_USER_3);
        assertEquals(rooms.size(), 1);
        assertEquals(rooms.get(0), TEST_ROOM_4);

        status = adapter.declineRoomInvitation("", TEST_ROOM_4);
        assertEquals(status, "failed: user does not exist");

        status = adapter.declineRoomInvitation(TEST_USER_3, "");
        assertEquals(status, "failed: room does not exist");

        status = adapter.declineRoomInvitation(TEST_USER_3, TEST_ROOM_4);
        assertEquals(status, STATUS_SUCCESS);

        rooms = adapter.userRoomInvitations(TEST_USER_3);
        assertEquals(rooms.size(), 0);

        rooms = adapter.publicRooms();
        assertEquals(rooms.size(), 2);
        assertEquals(rooms.get(0), TEST_ROOM_1);
        assertEquals(rooms.get(1), TEST_ROOM_3);

        status = adapter.joinRoom(TEST_USER_3, TEST_ROOM_1);
        assertEquals(status, STATUS_SUCCESS);

        status = adapter.joinRoom(TEST_USER_4, TEST_ROOM_3);
        assertEquals(status, STATUS_SUCCESS);

        rooms = adapter.publicRooms();
        assertEquals(rooms.size(), 0);

        rooms = adapter.userRooms(TEST_USER_1);
        assertEquals(rooms.size(), 4);
        assertEquals(rooms.get(0), TEST_ROOM_1);
        assertEquals(rooms.get(1), TEST_ROOM_2);
        assertEquals(rooms.get(2), TEST_ROOM_3);
        assertEquals(rooms.get(3), TEST_ROOM_4);

        rooms = adapter.userRooms(TEST_USER_2);
        assertEquals(rooms.size(), 0);

        rooms = adapter.userRooms(TEST_USER_3);
        assertEquals(rooms.size(), 2);
        assertEquals(rooms.get(0), TEST_ROOM_1);
        assertEquals(rooms.get(1), TEST_ROOM_2);

        rooms = adapter.userRooms(TEST_USER_4);
        assertEquals(rooms.size(), 1);
        assertEquals(rooms.get(0), TEST_ROOM_3);

        assertEquals(adapter.getStore().getUsers().get(0).getRooms().size(), 4);
        assertEquals(adapter.getStore().getUsers().get(0).getRooms().get(0), adapter.getStore().getRooms().get(0));
        assertEquals(adapter.getStore().getUsers().get(0).getRooms().get(1), adapter.getStore().getRooms().get(1));
        assertEquals(adapter.getStore().getUsers().get(0).getRooms().get(2), adapter.getStore().getRooms().get(2));
        assertEquals(adapter.getStore().getUsers().get(0).getRooms().get(3), adapter.getStore().getRooms().get(3));

        assertEquals(adapter.getStore().getUsers().get(1).getRooms().size(), 0);

        assertEquals(adapter.getStore().getUsers().get(2).getRooms().size(), 2);
        assertEquals(adapter.getStore().getUsers().get(2).getRooms().get(0), adapter.getStore().getRooms().get(0));
        assertEquals(adapter.getStore().getUsers().get(2).getRooms().get(1), adapter.getStore().getRooms().get(1));

        assertEquals(adapter.getStore().getUsers().get(3).getRooms().size(), 1);
        assertEquals(adapter.getStore().getUsers().get(3).getRooms().get(0), adapter.getStore().getRooms().get(2));

        assertEquals(adapter.getStore().getRooms().get(0).getOwner(), adapter.getStore().getUsers().get(0));
        assertEquals(adapter.getStore().getRooms().get(0).getMembers().size(), 2);
        assertEquals(adapter.getStore().getRooms().get(0).getMembers().get(0), adapter.getStore().getUsers().get(0));
        assertEquals(adapter.getStore().getRooms().get(0).getMembers().get(1), adapter.getStore().getUsers().get(2));

        assertEquals(adapter.getStore().getRooms().get(1).getOwner(), adapter.getStore().getUsers().get(0));
        assertEquals(adapter.getStore().getRooms().get(1).getMembers().size(), 2);
        assertEquals(adapter.getStore().getRooms().get(1).getMembers().get(0), adapter.getStore().getUsers().get(0));
        assertEquals(adapter.getStore().getRooms().get(1).getMembers().get(1), adapter.getStore().getUsers().get(2));

        assertEquals(adapter.getStore().getRooms().get(2).getOwner(), adapter.getStore().getUsers().get(0));
        assertEquals(adapter.getStore().getRooms().get(2).getMembers().size(), 2);
        assertEquals(adapter.getStore().getRooms().get(2).getMembers().get(0), adapter.getStore().getUsers().get(0));
        assertEquals(adapter.getStore().getRooms().get(2).getMembers().get(1), adapter.getStore().getUsers().get(3));

        assertEquals(adapter.getStore().getRooms().get(3).getOwner(), adapter.getStore().getUsers().get(0));
        assertEquals(adapter.getStore().getRooms().get(3).getMembers().size(), 1);
        assertEquals(adapter.getStore().getRooms().get(3).getMembers().get(0), adapter.getStore().getUsers().get(0));

        status = adapter.leaveRoom(TEST_USER_3, TEST_ROOM_1);
        assertEquals(status, STATUS_LEAVE_ROOM);

        status = adapter.leaveRoom(TEST_USER_4, TEST_ROOM_3);
        assertEquals(status, STATUS_LEAVE_ROOM);

        status = adapter.leaveRooms(TEST_USER_1);
        assertEquals(status, STATUS_LEAVE_ROOMS);

        assertEquals(adapter.getStore().getUsers().get(0).getRooms().size(), 0);
        assertEquals(adapter.getStore().getUsers().get(1).getRooms().size(), 0);
        assertEquals(adapter.getStore().getUsers().get(2).getRooms().size(), 1);
        assertEquals(adapter.getStore().getUsers().get(3).getRooms().size(), 0);

        assertEquals(adapter.getStore().getRooms().get(0).getOwner(), adapter.getStore().getUsers().get(0));
        assertEquals(adapter.getStore().getRooms().get(0).getMembers().size(), 0);
        assertEquals(adapter.getStore().getRooms().get(0).getMessages().size(), 4);

        message = adapter.getStore().getRooms().get(0).getMessages().get(0);
        assertEquals(message.getId(), 1);
        assertEquals(message.getSender(), adapter.getStore().getUsers().get(0));
        // assertEquals(message.getContent(), TEST_REPORT_REASON);
        assertEquals(message.getStatus(), 1);

        message = adapter.getStore().getRooms().get(0).getMessages().get(1);
        assertEquals(message.getId(), 2);
        assertEquals(message.getSender(), adapter.getStore().getUsers().get(0));
        // assertEquals(message.getContent(), TEST_BAN_REASON);
        assertEquals(message.getStatus(), 1);

        message = adapter.getStore().getRooms().get(0).getMessages().get(2);
        assertEquals(message.getId(), 3);
        assertEquals(message.getSender(), adapter.getStore().getUsers().get(2));
        // assertEquals(message.getContent(), TEST_LEAVE_ROOM_REASON);
        assertEquals(message.getStatus(), 1);

        message = adapter.getStore().getRooms().get(0).getMessages().get(3);
        assertEquals(message.getId(), 4);
        assertEquals(message.getSender(), adapter.getStore().getUsers().get(0));
        // assertEquals(message.getContent(), TEST_LEAVE_ROOMS_REASON);
        assertEquals(message.getStatus(), 1);

        assertEquals(adapter.getStore().getRooms().get(1).getOwner(), adapter.getStore().getUsers().get(0));
        assertEquals(adapter.getStore().getRooms().get(1).getMembers().size(), 1);
        assertEquals(adapter.getStore().getRooms().get(1).getMessages().size(), 1);

        message = adapter.getStore().getRooms().get(1).getMessages().get(0);
        assertEquals(message.getId(), 1);
        assertEquals(message.getSender(), adapter.getStore().getUsers().get(0));
        // assertEquals(message.getContent(), TEST_LEAVE_ROOMS_REASON);
        assertEquals(message.getStatus(), 1);

        assertEquals(adapter.getStore().getRooms().get(2).getOwner(), adapter.getStore().getUsers().get(0));
        assertEquals(adapter.getStore().getRooms().get(2).getMembers().size(), 0);
        assertEquals(adapter.getStore().getRooms().get(2).getMessages().size(), 2);

        message = adapter.getStore().getRooms().get(2).getMessages().get(0);
        assertEquals(message.getId(), 1);
        assertEquals(message.getSender(), adapter.getStore().getUsers().get(3));
        // assertEquals(message.getContent(), TEST_LEAVE_ROOM_REASON);
        assertEquals(message.getStatus(), 1);

        message = adapter.getStore().getRooms().get(2).getMessages().get(1);
        assertEquals(message.getId(), 2);
        assertEquals(message.getSender(), adapter.getStore().getUsers().get(0));
        // assertEquals(message.getContent(), TEST_LEAVE_ROOMS_REASON);
        assertEquals(message.getStatus(), 1);

        assertEquals(adapter.getStore().getRooms().get(3).getOwner(), adapter.getStore().getUsers().get(0));
        assertEquals(adapter.getStore().getRooms().get(3).getMembers().size(), 0);
        assertEquals(adapter.getStore().getRooms().get(3).getMessages().size(), 1);

        message = adapter.getStore().getRooms().get(3).getMessages().get(0);
        assertEquals(message.getId(), 1);
        assertEquals(message.getSender(), adapter.getStore().getUsers().get(0));
        // assertEquals(message.getContent(), TEST_LEAVE_ROOMS_REASON);
        assertEquals(message.getStatus(), 1);

    }

    public void testUsers() {
        DispatchAdapter adapter = DispatchAdapter.getOnly();
        adapter.setStore(new ChatWorldStore());

        String status = adapter.register(TEST_USER_1, TEST_PASSWORD, TEST_BIRTH_DATE, TEST_SCHOOL, TEST_INTERESTS);
        assertEquals(status, STATUS_SUCCESS);

        assertEquals(adapter.getStore().getUsers().size(), 1);
        User user = adapter.getStore().getUsers().get(0);
        assertEquals(user.getId(), 1);
        assertEquals(user.getUsername(), TEST_USER_1);
        assertEquals(user.getPassword(), TEST_PASSWORD);
        assertEquals(user.getBirthDate(), TEST_BIRTH_DATE);
        assertEquals(user.getSchool(), TEST_SCHOOL);
        assertEquals(user.getInterests(), TEST_INTERESTS);

        status = adapter.register(TEST_USER_1, TEST_PASSWORD, TEST_BIRTH_DATE, TEST_SCHOOL, TEST_INTERESTS);
        assertEquals(status, "failed: user already exists");

        status = adapter.login(TEST_USER_2, TEST_PASSWORD);
        assertEquals(status, "failed: user does not exist");

        status = adapter.login(TEST_USER_1, TEST_PASSWORD_2);
        assertEquals(status, "failed: wrong password");

        status = adapter.login(TEST_USER_1, TEST_PASSWORD);
        assertEquals(status, STATUS_SUCCESS);
    }

    public void testMessages() {
        DispatchAdapter adapter = DispatchAdapter.getOnly();
        adapter.setStore(new ChatWorldStore());

        String status = adapter.createMessage(TEST_USER_1, TEST_ROOM_1, TEST_MESSAGE_CONTENT);
        assertEquals(status, "failed: user does not exist");

        status = adapter.register(TEST_USER_1, TEST_PASSWORD, TEST_BIRTH_DATE, TEST_SCHOOL, TEST_INTERESTS);
        assertEquals(status, STATUS_SUCCESS);

        status = adapter.createMessage(TEST_USER_1, TEST_ROOM_1, TEST_MESSAGE_CONTENT);
        assertEquals(status, "failed: room does not exist");

        status = adapter.createRoom(TEST_USER_1, TEST_ROOM_1, TEST_ROOM_SIZE, TEST_ROOM_IS_PUBLIC);
        assertEquals(status, STATUS_SUCCESS);

        status = adapter.createMessage(TEST_USER_1, TEST_ROOM_1, TEST_MESSAGE_CONTENT);
        assertEquals(status, STATUS_MESSAGE_CREATE);
        assertEquals(adapter.getStore().getRooms().get(0).getMessages().size(), 1);

        Message message = adapter.getStore().getRooms().get(0).getMessages().get(0);
        assertEquals(message.getId(), 1);
        assertEquals(message.getSender(), adapter.getStore().getUsers().get(0));
        assertEquals(message.getContent(), TEST_MESSAGE_CONTENT);
        assertEquals(message.getStatus(), 0);
        assertEquals(message.getReactions().size(), 0);

        status = adapter.editMessage(TEST_ROOM_2, message.getId(), TEST_MESSAGE_CONTENT_2);
        assertEquals(status, "failed: room does not exist");

        status = adapter.editMessage(TEST_ROOM_1, message.getId(), TEST_MESSAGE_CONTENT_2);
        assertEquals(status, STATUS_MESSAGE_EDIT);

        assertEquals(message.getId(), 1);
        assertEquals(message.getSender(), adapter.getStore().getUsers().get(0));
        assertEquals(message.getContent(), TEST_MESSAGE_CONTENT_2);
        assertEquals(message.getStatus(), 0);
        assertEquals(message.getReactions().size(), 0);

        status = adapter.addHeartToMessage(TEST_USER_2, TEST_ROOM_1, message.getId());
        assertEquals(status, "failed: user does not exist");

        status = adapter.addHeartToMessage(TEST_USER_1, TEST_ROOM_2, message.getId());
        assertEquals(status, "failed: room does not exist");

        status = adapter.addHeartToMessage(TEST_USER_1, TEST_ROOM_1, message.getId());
        assertEquals(status, STATUS_SUCCESS);

        assertEquals(message.getId(), 1);
        assertEquals(message.getSender(), adapter.getStore().getUsers().get(0));
        assertEquals(message.getContent(), TEST_MESSAGE_CONTENT_2);
        assertEquals(message.getStatus(), 0);
        assertEquals(message.getReactions().size(), 1);
        assertEquals(message.getReactions().get(adapter.getStore().getUsers().get(0)), "heart");

        status = adapter.deleteMessage(TEST_ROOM_2, message.getId());
        assertEquals(status, "failed: room does not exist");

        status = adapter.deleteMessage(TEST_ROOM_1, message.getId());
        assertEquals(status, STATUS_MESSAGE_DELETE);
        assertEquals(adapter.getStore().getRooms().get(0).getMessages().size(), 0);

        status = adapter.createRoom(TEST_USER_1, TEST_ROOM_2, TEST_ROOM_SIZE, TEST_ROOM_IS_PUBLIC);
        assertEquals(status, STATUS_SUCCESS);

        status = adapter.createMessage(TEST_USER_1, TEST_ROOM_1, TEST_MESSAGE_CONTENT);
        assertEquals(status, STATUS_MESSAGE_CREATE);

        status = adapter.createMessage(TEST_USER_1, TEST_ROOM_1, TEST_MESSAGE_CONTENT_2);
        assertEquals(status, STATUS_MESSAGE_CREATE);

        status = adapter.createMessage(TEST_USER_1, TEST_ROOM_2, TEST_MESSAGE_CONTENT);
        assertEquals(status, STATUS_MESSAGE_CREATE);

        List<Message> messages = adapter.roomMessages(TEST_ROOM_1);
        assertEquals(messages.size(), 2);
        assertEquals(messages.get(0).getId(), 1);
        assertEquals(messages.get(0).getSender(), adapter.getStore().getUsers().get(0));
        assertEquals(messages.get(0).getContent(), TEST_MESSAGE_CONTENT);
        assertEquals(messages.get(0).getStatus(), 0);
        assertEquals(messages.get(1).getId(), 2);
        assertEquals(messages.get(1).getSender(), adapter.getStore().getUsers().get(0));
        assertEquals(messages.get(1).getContent(), TEST_MESSAGE_CONTENT_2);
        assertEquals(messages.get(1).getStatus(), 0);

        messages = adapter.allMessages();
        assertEquals(messages.size(), 3);
        assertEquals(messages.get(0).getId(), 1);
        assertEquals(messages.get(0).getSender(), adapter.getStore().getUsers().get(0));
        assertEquals(messages.get(0).getContent(), TEST_MESSAGE_CONTENT);
        assertEquals(messages.get(0).getStatus(), 0);
        assertEquals(messages.get(1).getId(), 2);
        assertEquals(messages.get(1).getSender(), adapter.getStore().getUsers().get(0));
        assertEquals(messages.get(1).getContent(), TEST_MESSAGE_CONTENT_2);
        assertEquals(messages.get(1).getStatus(), 0);
        assertEquals(messages.get(2).getId(), 1);
        assertEquals(messages.get(2).getSender(), adapter.getStore().getUsers().get(0));
        assertEquals(messages.get(2).getContent(), TEST_MESSAGE_CONTENT);
        assertEquals(messages.get(2).getStatus(), 0);
    }

    public void testConnections() {
        User user1 = new User(1, TEST_USER_1, TEST_PASSWORD, TEST_BIRTH_DATE, TEST_SCHOOL, TEST_INTERESTS);
        User user2 = new User(2, TEST_USER_2, TEST_PASSWORD, TEST_BIRTH_DATE, TEST_SCHOOL, TEST_INTERESTS);
        User user3 = new User(3, TEST_USER_3, TEST_PASSWORD, TEST_BIRTH_DATE, TEST_SCHOOL, TEST_INTERESTS);

        user1.addFriendInvitation(user2);
        assertEquals(user1.getFriendInvitations().size(), 1);
        assertEquals(user1.getFriendInvitations().get(0).getSender(), user2);
        assertEquals(user1.getFriendInvitations().get(0).getReceiver(), user1);

        user1.acceptFriendInvitation(user2);
        assertEquals(user1.getFriendInvitations().size(), 0);
        assertEquals(user1.getFriends().size(), 1);
        assertEquals(user1.getFriends().get(0), user2);
        assertEquals(user2.getFriends().size(), 1);
        assertEquals(user2.getFriends().get(0), user1);

        user1.addFriendInvitation(user3);
        assertEquals(user1.getFriendInvitations().size(), 1);
        assertEquals(user1.getFriendInvitations().get(0).getSender(), user3);
        assertEquals(user1.getFriendInvitations().get(0).getReceiver(), user1);

        user1.declineFriendInvitation(user3);
        assertEquals(user1.getFriendInvitations().size(), 0);
        assertEquals(user1.getFriends().size(), 1);
        assertEquals(user1.getFriends().get(0), user2);
        assertEquals(user3.getFriends().size(), 0);

        user1.addFriendInvitation(user3);
        assertEquals(user1.getFriendInvitations().size(), 1);
        assertEquals(user1.getFriendInvitations().get(0).getSender(), user3);
        assertEquals(user1.getFriendInvitations().get(0).getReceiver(), user1);
    }

    public void testOthers() {
        DispatchAdapter adapter = DispatchAdapter.getOnly();
        adapter.setStore(new ChatWorldStore());

        adapter.register(TEST_USER_1, TEST_PASSWORD, TEST_BIRTH_DATE, TEST_SCHOOL, TEST_INTERESTS);
        User user = adapter.getUser(TEST_USER_1);
        assertEquals(user.getUsername(), TEST_USER_1);
        assertEquals(user.getPassword(), TEST_PASSWORD);
        assertEquals(user.getBirthDate(), TEST_BIRTH_DATE);
        assertEquals(user.getSchool(), TEST_SCHOOL);
        assertEquals(user.getInterests(), TEST_INTERESTS);

        adapter.register(TEST_USER_2, TEST_PASSWORD, TEST_BIRTH_DATE, TEST_SCHOOL, TEST_INTERESTS);
        adapter.inviteFriend(TEST_USER_2, TEST_USER_1);
        adapter.acceptFriend(TEST_USER_2, TEST_USER_1);
        List<String> friends = adapter.getFriends(TEST_USER_1);
        assertEquals(friends.size(), 1);
        assertEquals(friends.get(0), TEST_USER_2);
        friends = adapter.getFriends(TEST_USER_2);
        assertEquals(friends.size(), 1);
        assertEquals(friends.get(0), TEST_USER_1);

        adapter.register(TEST_USER_3, TEST_PASSWORD, TEST_BIRTH_DATE, TEST_SCHOOL, TEST_INTERESTS);
        adapter.inviteFriend(TEST_USER_3, TEST_USER_1);
        adapter.declineFriend(TEST_USER_3, TEST_USER_1);
        friends = adapter.getFriends(TEST_USER_1);
        assertEquals(friends.size(), 1);
        assertEquals(friends.get(0), TEST_USER_2);
        List<String> notFriends = adapter.getNotFriends(TEST_USER_1);
        assertEquals(notFriends.size(), 1);
        assertEquals(notFriends.get(0), TEST_USER_3);

        assertTrue(adapter.getFriends(TEST_USER_4).isEmpty());
        assertTrue(adapter.getNotFriends(TEST_USER_4).isEmpty());

        adapter.createRoom(TEST_USER_1, TEST_ROOM_1, TEST_ROOM_SIZE, TEST_ROOM_IS_PUBLIC);
        ChatRoom room = adapter.getRoom(TEST_ROOM_1);
        assertEquals(room.getOwner().getUsername(), TEST_USER_1);
        assertEquals(room.getName(), TEST_ROOM_1);
        assertEquals(room.getSize(), TEST_ROOM_SIZE - 1);
        assertEquals(room.getIsPublic(), TEST_ROOM_IS_PUBLIC);

        List<ChatRoom> rooms = adapter.getPublicRooms();
        assertEquals(rooms.size(), 1);
        assertEquals(rooms.get(0), room);

        rooms = adapter.getUserRooms(TEST_USER_1);
        assertEquals(rooms.size(), 1);
        assertEquals(rooms.get(0), room);
        assertTrue(adapter.getUserRooms(TEST_USER_4).isEmpty());

        adapter.createMessage(TEST_USER_1, TEST_ROOM_1, TEST_MESSAGE_CONTENT);
        List<Message> messages = adapter.getUserMessages(TEST_USER_1, TEST_ROOM_1);
        assertEquals(messages.size(), 1);
        Message message = messages.get(0);
        assertEquals(message.getSender().getUsername(), TEST_USER_1);
        assertEquals(message.getContent(), TEST_MESSAGE_CONTENT);
        assertEquals(message.getStatus(), 0);

        adapter.inviteToRoom(TEST_USER_1, TEST_USER_2, TEST_ROOM_1);
        List<RoomInvitation> roomInvitations = adapter.getUserRoomInvitations(TEST_USER_2);
        assertEquals(roomInvitations.size(), 1);
        assertEquals(roomInvitations.get(0).getRoom(), room);
        assertTrue(adapter.getUserRoomInvitations(TEST_USER_4).isEmpty());

        adapter.inviteFriend(TEST_USER_1, TEST_USER_3);
        List<FriendInvitation> friendInvitations = adapter.getUserFriendInvitations(TEST_USER_3);
        assertEquals(friendInvitations.size(), 1);
        assertEquals(friendInvitations.get(0).getSender(), user);
        assertTrue(adapter.getUserFriendInvitations(TEST_USER_4).isEmpty());
        adapter.inviteFriend(TEST_USER_3, TEST_USER_1);

        adapter.blockUser(TEST_USER_1, TEST_USER_2);
        adapter.blockUser(TEST_USER_1, TEST_USER_3);
        assertTrue(adapter.getFriends(TEST_USER_1).isEmpty());
        assertTrue(adapter.getUserFriendInvitations(TEST_USER_1).isEmpty());
        assertEquals(adapter.getBlockList(TEST_USER_1).size(), 2);
        assertEquals(adapter.getBlockList(TEST_USER_1).get(0), TEST_USER_2);
        assertEquals(adapter.getBlockList(TEST_USER_1).get(1), TEST_USER_3);
        assertTrue(adapter.getFriends(TEST_USER_2).isEmpty());
        assertTrue(adapter.getUserFriendInvitations(TEST_USER_2).isEmpty());
        assertTrue(adapter.getBlockList(TEST_USER_2).isEmpty());
        assertTrue(adapter.getFriends(TEST_USER_3).isEmpty());
        assertTrue(adapter.getUserFriendInvitations(TEST_USER_3).isEmpty());
        assertTrue(adapter.getBlockList(TEST_USER_3).isEmpty());

        adapter.unblockUser(TEST_USER_1, TEST_USER_2);
        adapter.unblockUser(TEST_USER_1, TEST_USER_3);
        assertTrue(adapter.getFriends(TEST_USER_1).isEmpty());
        assertTrue(adapter.getUserFriendInvitations(TEST_USER_1).isEmpty());
        assertTrue(adapter.getBlockList(TEST_USER_1).isEmpty());
        assertTrue(adapter.getFriends(TEST_USER_2).isEmpty());
        assertTrue(adapter.getUserFriendInvitations(TEST_USER_2).isEmpty());
        assertTrue(adapter.getBlockList(TEST_USER_2).isEmpty());
        assertTrue(adapter.getFriends(TEST_USER_3).isEmpty());
        assertTrue(adapter.getUserFriendInvitations(TEST_USER_3).isEmpty());
        assertTrue(adapter.getBlockList(TEST_USER_3).isEmpty());

        List<User> users = adapter.getRoomMembers(TEST_ROOM_1);
        assertEquals(users.size(), 1);
        assertEquals(users.get(0), user);

        adapter.setUserInfo(user, TEST_MESSAGE_CONTENT, List.of(), TEST_MESSAGE_CONTENT_2);
        assertEquals(user.getUsername(), TEST_USER_1);
        assertEquals(user.getPassword(), TEST_PASSWORD);
        assertEquals(user.getSchool(), TEST_MESSAGE_CONTENT);
        assertTrue(user.getInterests().isEmpty());
        assertEquals(user.getSelfDescription(), TEST_MESSAGE_CONTENT_2);

        adapter.setUserDescription(TEST_USER_1, TEST_MESSAGE_CONTENT);
        assertEquals(user.getSelfDescription(), TEST_MESSAGE_CONTENT);

        assertTrue(user.isActive());
        adapter.setActive(TEST_USER_1, false);
        assertFalse(user.isActive());

        adapter.setRoomCapacity(TEST_ROOM_1, 10);
        assertEquals(room.getCapacity(), 10);

        adapter.createRoom(TEST_USER_1, TEST_ROOM_2, TEST_ROOM_SIZE, TEST_ROOM_IS_PUBLIC);
        adapter.setRoomName(TEST_ROOM_1, TEST_ROOM_2);
        assertEquals(room.getName(), TEST_ROOM_1);
        adapter.setRoomName(TEST_ROOM_1, TEST_ROOM_3);
        assertEquals(room.getName(), TEST_ROOM_3);

        assertTrue(user.getAvatar() >= 1 && user.getAge() <= 10);
        assertTrue(user.getAge() >= 0);
        assertTrue(room.getAvatar() >= 1 && room.getAvatar() <= 10);

        adapter.createMessage(TEST_USER_1, TEST_ROOM_1, "hate speech");
        assertEquals(room.getMessages().size(), 2);
        assertEquals(room.getMessages().get(1).getStatus(), 2);
        // assertTrue(user.isBanned());
        for (int i = 0; i < 9; ++i) {
            adapter.createMessage(TEST_USER_1, TEST_ROOM_1, "hate speech");
        }

        assertTrue(user.isBanned());
        assertEquals(user.getHateSpeechCounter(), 10);

        assertEquals(room.getMessages().get(10), room.getLatestMessage());
        room.setOwner(user);
        assertEquals(room.getOwner(), user);

        adapter.addReactionToMessage(TEST_USER_1, TEST_ROOM_1, 1, "heart");
        adapter.addReactionToMessage("", TEST_ROOM_1, 1, "heart");
        adapter.addReactionToMessage(TEST_USER_1, "", 1, "heart");
        assertNotNull(adapter.getLatestMessage(TEST_USER_1, TEST_ROOM_1));
        assertEquals(adapter.allUsers().size(), 3);
        assertTrue(adapter.allUsers(TEST_USER_1).isEmpty());
    }

    public void testAllUsers() {
        DispatchAdapter adapter = DispatchAdapter.getOnly();
        adapter.setStore(new ChatWorldStore());

        adapter.register(TEST_USER_4, TEST_PASSWORD, TEST_BIRTH_DATE, TEST_SCHOOL, TEST_INTERESTS);
        adapter.register(TEST_USER_4 + "_", TEST_PASSWORD, TEST_BIRTH_DATE, TEST_SCHOOL, List.of());
        adapter.register(TEST_USER_4 + "__", TEST_PASSWORD, TEST_BIRTH_DATE, "", TEST_INTERESTS);
        assertEquals(adapter.allUsers(TEST_USER_4).size(), 2);
    }

}
