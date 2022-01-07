/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.rinelfi.chat.entity.emmbed;

import mg.rinelfi.chat.entity.relation.UserChannelUser;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author rinelfi
 */
@Embeddable
public class UserChannelUserKey implements Serializable {
    
    @Column(name = "ucu_channel")
    private Long channel;
    @Column(name = "ucu_user")
    private Long user;
    
    public UserChannelUserKey() {}
    
    public Long getChannel() {
        return channel;
    }
    
    public void setChannel(Long channel) {
        this.channel = channel;
    }
    
    public Long getUser() {
        return user;
    }
    
    public void setUser(Long user) {
        this.user = user;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserChannelUserKey that = (UserChannelUserKey) o;
        return channel.equals(that.channel) && user.equals(that.user);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(channel, user);
    }
}
