package mg.rinelfi.chat;

import mg.rinelfi.chat.entity.*;
import mg.rinelfi.chat.entity.relation.UserChannelUser;
import mg.rinelfi.chat.entityManager.*;
import mg.rinelfi.console.Console;
import mg.rinelfi.jiosocket.server.PseudoWebServer;
import mg.rinelfi.jiosocket.server.SocketServer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import mg.rinelfi.chat.entity.emmbed.UserGroupKey;
import mg.rinelfi.chat.entity.relation.UserGroup;

public class Launcher {

    public static void main(String[] args) {

        UserManager userManager = new UserManager(MySessionFactory.getInstance());
        ChannelManager channelManager = new ChannelManager(MySessionFactory.getInstance());
        UserChannelManager userChannelManager = new UserChannelManager(MySessionFactory.getInstance());
        UserGroupManager userGroupManager = new UserGroupManager(MySessionFactory.getInstance());
        MessageManager messageManager = new MessageManager(MySessionFactory.getInstance());

        User rinelfi = new User();
        rinelfi.setFirstname("Rijaniaina");
        rinelfi.setLastname("Elie Fidèle");
        rinelfi.setUsername("rinelfi");
        rinelfi.setPassword(BCrypt.hashpw("c'est facile", BCrypt.gensalt(12)));

        User foller = new User();
        foller.setFirstname("Ronie");
        foller.setLastname("Foller");
        foller.setUsername("foll");
        foller.setPassword(BCrypt.hashpw("foll", BCrypt.gensalt(12)));

        userManager.create(rinelfi);
        userManager.create(foller);

        UserChannel channel1 = new UserChannel();
        channelManager.create(channel1);

        UserChannelUser channelLink = new UserChannelUser();
        channelLink.setChannel(channel1);
        channelLink.setUser(rinelfi);
        channelLink.setUsername("Rijaniaina Elie Fidèle");
        channel1.getUserChannelLinks().add(channelLink);
        rinelfi.getUserChannelLinks().add(channelLink);
        userChannelManager.create(channelLink);

        channelLink = new UserChannelUser();
        channelLink.setChannel(channel1);
        channelLink.setUser(foller);
        channelLink.setUsername("Ny adala");
        channel1.getUserChannelLinks().add(channelLink);
        foller.getUserChannelLinks().add(channelLink);
        userChannelManager.create(channelLink);

        int socketPort = 2046;
        try {
            SocketServer server = new SocketServer(socketPort);
            server.start();
            System.out.println("\n\nLancement du serveur\nEcoute sur le port : " + socketPort);
            System.out.println("\n");
            server.on("message", data -> {
                JSONObject request = new JSONObject(data);
                String target = request.getString("target");
                String sender = request.getString("sender");
                server.emit("message", data.toString(), target);
            }).on("received", data -> {
                JSONObject request = new JSONObject(data);
                String target = request.getString("target");
                server.emit("received", data.toString(), target);
            }).on("seen", data -> {
                JSONObject request = new JSONObject(data);
                String target = request.getString("target");
                server.emit("seen", data.toString(), target);
            }).on("typing on", data -> {
                JSONObject request = new JSONObject(data);
                String target = request.getString("target");
                server.emit("typing on", data.toString(), target);
            }).on("typing off", data -> {
                JSONObject request = new JSONObject(data);
                String target = request.getString("target");
                server.emit("typing off", data.toString(), target);
            }).on("token connect message", data -> {
                server.emit("token connect message", data);
            }).on("token connect reply", data -> {
                JSONObject request = new JSONObject(data);
                String target = request.getString("target");
                server.emit("token connect reply", data, target);
            });
        } catch (IOException e) {
        }

        int webServerPort = 2045;
        try {
            PseudoWebServer webServer = new PseudoWebServer(webServerPort);
            webServer.start();
            webServer.on("registration", (json, handler) -> {
                User user = new User();
                user.setFirstname(json.getString("firstname"));
                user.setLastname(json.getString("lastname"));
                user.setUsername(json.getString("username"));
                user.setPassword(BCrypt.hashpw(json.getString("password"), BCrypt.gensalt(12)));
                userManager.create(user);
                handler.send(new JSONObject().put("status", true).toString());
            }).on("connection", (json, handler) -> {
                JSONObject response = new JSONObject();
                String password = userManager.getPassword(json.getString("username"));
                if (password != null && BCrypt.checkpw(json.getString("password"), password)) {
                    response.put("match", true);
                    response.put("user", new JSONObject(userManager.get("username", json.getString("username"))));
                } else
                    response.put("match", false);
                handler.send(response.toString());
            }).on("channels", (json, handler) -> {
                Console.log(Launcher.class, "channel synchrone");
                List<Channel> objectChannels = channelManager.selectFromUser(json.getLong("user"));
                Console.log(Launcher.class, "length : " + objectChannels.size());
                JSONArray jsonChannels = new JSONArray();
                for (Channel channel : objectChannels) {
                    if (channel instanceof UserChannel) {
                        UserChannel userChannel = (UserChannel) channel;
                        userChannel.setUserChannelLinks(userChannelManager.getUsersFromChannel(userChannel));
                        String lastMessage = messageManager.getLastMessageFromChannel(channel);
                        jsonChannels.put(new JSONObject().put("type", "user_channel").put("message", lastMessage).put("channel", new JSONObject(userChannel)));
                    } else {
                        Group group = (Group) channel;
                        group.setUserGroupLinks(userGroupManager.getMembersFromGroup(group));
                        String lastMessage = messageManager.getLastMessageFromChannel(channel);
                        jsonChannels.put(new JSONObject().put("type", "group").put("message", lastMessage).put("channel", new JSONObject(group)));
                    }
                }
                handler.send(new JSONObject().put("channels", jsonChannels).toString());
            }).on("text message", (json, handler) -> {
                User user = userManager.get("username", json.getString("username"));
                Channel channel = channelManager.get("id", json.getLong("channel"));
                MessageText message = new MessageText();
                message.setContent(json.getString("message"));
                message.setUser(user);
                message.setChannel(channel);
                message.setDate(new Date());
                message.setStatus(0);
                messageManager.create(message);
                handler.send(new JSONObject().put("sent", true).toString());
            }).on("message list", (json, handler) -> {
                Channel channel = channelManager.get("id", json.getLong("channel"));
                List<Message> dbMessages = messageManager.select(channel);
                JSONArray jsonUserArray = new JSONArray();
                for (Message message : dbMessages) {
                    JSONObject jsonUserObject = new JSONObject();
                    jsonUserObject.put("type", message instanceof MessageText ? "text" : "media");
                    jsonUserObject.put("user", message.getUser().getUsername());
                    jsonUserObject.put("date", message.getDate());
                    jsonUserObject.put("content", ((MessageText) message).getContent());
                    jsonUserArray.put(jsonUserObject);
                }
                handler.send(new JSONObject().put("messages", jsonUserArray).toString());
            }).on("users", (json, handler) -> {
                Console.log(Launcher.class, "user synchrone");
                List<User> userObjects = userManager.select();
                JSONArray jsonUserArray = new JSONArray();
                for (User user : userObjects) {
                    JSONObject jsonUserObject = new JSONObject();
                    jsonUserObject.put("username", user.getUsername());
                    jsonUserObject.put("firstname", user.getFirstname());
                    jsonUserObject.put("lastname", user.getLastname());
                    jsonUserArray.put(jsonUserObject);
                }
                handler.send(new JSONObject().put("users", jsonUserArray).toString());
            }).on("user:contact:search", (json, handler) -> {
                Console.log(Launcher.class, "paremeter : " + json.getString("name"));
                List<User> dbUsers = userManager.select(json.getString("name"));
                JSONArray jsonUsers = new JSONArray();
                for (User dbUser : dbUsers) {
                    JSONObject jsonUser = new JSONObject();
                    jsonUser.put("firstname", dbUser.getFirstname());
                    jsonUser.put("lastname", dbUser.getLastname());
                    jsonUser.put("username", dbUser.getUsername());
                    jsonUser.put("id", dbUser.getId());
                    jsonUsers.put(jsonUser);
                }
                handler.send(new JSONObject().put("users", jsonUsers).toString());
            }).on("group:create", (json, handler) -> {
                User administrator = userManager.get("username", json.getString("admin"));
                Group group = new Group();
                group.setName(json.getString("group"));
                group.setAdministrator(administrator);
                channelManager.create(group);
                UserGroup link = new UserGroup();
                link.setGroup(group);
                link.setUser(administrator);
                link.setName(administrator.getFirstname() + " " + administrator.getLastname());
                UserGroupKey key = userGroupManager.create(link);
                handler.send(new JSONObject().put("success", key.getGroup() > 0).toString());
            });
        } catch (IOException e) {
        }
    }
}
