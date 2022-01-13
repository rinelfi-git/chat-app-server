/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.rinelfi.chat.entity.relation;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import mg.rinelfi.chat.entity.Message;
import mg.rinelfi.chat.entity.User;
import mg.rinelfi.chat.entity.emmbed.MessageReactKey;

/**
 *
 * @author rinelfi
 */
@Entity(name="message_react")
public class MessageReact implements Serializable{
@EmbeddedId
    private MessageReactKey id;
    @ManyToOne
    @MapsId("message")
    private Message message;
    @ManyToOne
    @MapsId("user")
    private User user;
    private Date sent;

    public MessageReact() {
        this.id = new MessageReactKey();
    }

    public MessageReactKey getId() {
        return id;
    }

    public void setId(MessageReactKey id) {
        this.id = id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getSent() {
        return sent;
    }

    public void setSent(Date sent) {
        this.sent = sent;
    }

}
