/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.rinelfi.chat.entity;

import mg.rinelfi.chat.entity.relation.MessageReact;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author rinelfi
 */
@Entity(name="Message")
@Table(name="m_message")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Message implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "m_id")
    private long id;
    @ManyToOne
    private Channel channel;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @Column(name = "m_status")
    private int status;
    @Column(name="m_date")
    private Date date;
    @OneToMany(mappedBy = "message", fetch = FetchType.LAZY)
    private Set<MessageReact> messageReactLinks;
    
    public Message(){
        this.messageReactLinks = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public Channel getChannel() {
        return channel;
    }
    
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public Set<MessageReact> getMessageReactLinks() {
        return messageReactLinks;
    }
    
    public void setMessageReactLinks(Set<MessageReact> messageReactLinks) {
        this.messageReactLinks = messageReactLinks;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
