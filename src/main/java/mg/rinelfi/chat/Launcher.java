package mg.rinelfi.chat;

import mg.rinelfi.chat.entity.User;
import mg.rinelfi.chat.entityManager.MySessionFactory;
import mg.rinelfi.chat.entityManager.UserManager;
import mg.rinelfi.jiosocket.server.PseudoWebServer;
import mg.rinelfi.jiosocket.server.SocketServer;

import java.io.IOException;

import org.json.JSONObject;

public class Launcher {
    public static void main(String[] args) {
    	UserManager userManager = new UserManager(MySessionFactory.getInstance());
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
			webServer.on("test", (json, handler) -> {
				User user = new User();
				user.setUsername(json.getString("username"));
				user.setPassword(json.getString("password"));
				userManager.insert(user);
				handler.send(json.toString());
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
    }
}
