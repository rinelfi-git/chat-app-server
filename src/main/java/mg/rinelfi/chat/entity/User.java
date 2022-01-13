package mg.rinelfi.chat.entity;

import mg.rinelfi.chat.entity.relation.MessageReact;
import mg.rinelfi.chat.entity.relation.UserChannelUser;
import mg.rinelfi.chat.entity.relation.UserGroup;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity(name = "User")
@Table(name = "u_user")
public class User implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id")
    private long id;
    @Column(name = "u_firstname")
    private String firstname;
    @Column(name = "u_lastname")
    private String lastname;
    @Column(unique = true, name = "u_username")
    private String username;
    @Column(name = "u_password")
    private String password;
    @Column(name = "u_online")
    private boolean online;
    @Column(name = "u_banned")
    private boolean banned;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserChannelUser> userChannelLinks;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserGroup> userGroupLinks;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<MessageReact> messageReactLinks;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Message> messages;
    
    public User() {
        this.userChannelLinks = new HashSet<>();
        this.userGroupLinks = new HashSet<>();
        this.messageReactLinks = new HashSet<>();
        this.messages = new HashSet<>();
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getFirstname() {
        return firstname;
    }
    
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    
    public String getLastname() {
        return lastname;
    }
    
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isOnline() {
        return online;
    }
    
    public void setOnline(boolean online) {
        this.online = online;
    }
    
    public boolean isBanned() {
        return banned;
    }
    
    public void setBanned(boolean banned) {
        this.banned = banned;
    }
    
    public Set<UserChannelUser> getUserChannelLinks() {
        return userChannelLinks;
    }
    
    public void setUserChannelLinks(Set<UserChannelUser> channels) {
        this.userChannelLinks = channels;
    }
    
    public Set<UserGroup> getUserGroupLinks() {
        return userGroupLinks;
    }
    
    public void setUserGroupLinks(Set<UserGroup> groups) {
        this.userGroupLinks = groups;
    }
    
    public Set<MessageReact> getMessageReactLinks() {
        return messageReactLinks;
    }
    
    public void setMessageReactLinks(Set<MessageReact> messageReactLinks) {
        this.messageReactLinks = messageReactLinks;
    }
}
