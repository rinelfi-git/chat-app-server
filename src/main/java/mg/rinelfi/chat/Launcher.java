package mg.rinelfi.chat;

import mg.rinelfi.chat.entity.Channel;
import mg.rinelfi.chat.entity.Group;
import mg.rinelfi.chat.entity.User;
import mg.rinelfi.chat.entity.UserChannel;
import mg.rinelfi.chat.entity.relation.UserChannelUser;
import mg.rinelfi.chat.entity.relation.UserGroup;
import mg.rinelfi.chat.entityManager.*;
import mg.rinelfi.jiosocket.server.PseudoWebServer;
import mg.rinelfi.jiosocket.server.SocketServer;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Launcher {
    
    public static void main(String[] args) {
        
        UserManager userManager = new UserManager(MySessionFactory.getInstance());
        ChannelManager channelManager = new ChannelManager(MySessionFactory.getInstance());
        UserChannelManager userChannelUserManager = new UserChannelManager(MySessionFactory.getInstance());
        UserGroupManager userGroupManager = new UserGroupManager(MySessionFactory.getInstance());
        
        User user1 = new User();
        User user2 = new User();
        user1.setUsername("Rijaniaina");
        user2.setUsername("Rinelfi");
        userManager.create(user1);
        userManager.create(user2);
        
        UserChannel channel = new UserChannel();
        channelManager.create(channel);
        
        UserChannelUser userChannel1 = new UserChannelUser();
        userChannel1.setUser(user1);
        userChannel1.setChannel(channel);
        userChannel1.setUsername(user1.getUsername());
        
        user1.getUserChannelLinks().add(userChannel1);
        channel.getUserChannelLinks().add(userChannel1);
        
        userChannelUserManager.create(userChannel1);
        
        UserChannelUser userChannel2 = new UserChannelUser();
        userChannel2.setUser(user2);
        userChannel2.setChannel(channel);
        userChannel2.setUsername(user2.getUsername());
        
        user2.getUserChannelLinks().add(userChannel2);
        channel.getUserChannelLinks().add(userChannel2);
        
        userChannelUserManager.create(userChannel2);
        
        List<Channel> fromDatabase = channelManager.select();
        Set<UserChannelUser> sets = ((UserChannel) fromDatabase.get(0)).getUserChannelLinks();
        System.out.println();
        for(UserChannelUser set : sets) {
            System.out.println(set.getUser().getUsername() + " at channel " + set.getChannel().getId());
        }
        System.out.println();
        Group groupe = new Group();
        groupe.setAdministrator(user1);
        channelManager.create(groupe);
        
        UserGroup userGroup1 = new UserGroup();
        userGroup1.setGroup(groupe);
        userGroup1.setUser(user1);
        userGroup1.setName(user1.getUsername());
        groupe.getUserGroupLinks().add(userGroup1);
        user1.getUserGroupLinks().add(userGroup1);
        userGroupManager.create(userGroup1);
        
        userGroup1 = new UserGroup();
        userGroup1.setGroup(groupe);
        userGroup1.setUser(user2);
        userGroup1.setName(user2.getUsername());
        groupe.getUserGroupLinks().add(userGroup1);
        user2.getUserGroupLinks().add(userGroup1);
        userGroupManager.create(userGroup1);
        
        fromDatabase = channelManager.select();
        System.out.println(fromDatabase.size());
        
        Group group = (Group) fromDatabase.get(1);
        System.out.println("administrator is " + group.getAdministrator().getUsername());
        Set<UserGroup> links = group.getUserGroupLinks();
        for(UserGroup link: links) {
            System.out.println("member is : " + link.getUser().getUsername() + ". as => " + link.getName());
        }
        
        int port = 2046;
        try {
            SocketServer server = new SocketServer(port);
            server.start();
            System.out.println("Lancement du serveur\nEcoute sur le port : " + port);
            server.on("message", data -> {
                String target = data.getString("target");
                String sender = data.getString("sender");
                server.emit("message", data.toString(), target);
                server.emit("sent", data.toString(), sender);
            }).on("received", data -> {
                String target = data.getString("target");
                server.emit("received", data.toString(), target);
            }).on("seen", data -> {
                String target = data.getString("target");
                server.emit("seen", data.toString(), target);
            }).on("typing on", data -> {
                String target = data.getString("target");
                server.emit("typing on", data.toString(), target);
            }).on("typing off", data -> {
                String target = data.getString("target");
                server.emit("typing off", data.toString(), target);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        port = 2045;
        try {
            PseudoWebServer webServer = new PseudoWebServer(port);
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
                if (password != null && BCrypt.checkpw(json.getString("password"), password))  response.put("match", true);
                else response.put("match", false);
                handler.send(response.toString());
            });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
