/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.rinelfi.chat.entity.emmbed;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author rinelfi
 */
@Embeddable
public class MessageSentKey implements Serializable{
    @Column(name = "ms_message")
    private Long message;
    @Column(name = "ms_user")
    private Long user;

    public Long getMessage() {
        return message;
    }

    public void setMessage(Long message) {
        this.message = message;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.message);
        hash = 47 * hash + Objects.hashCode(this.user);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final MessageSentKey other = (MessageSentKey) obj;
        if (!Objects.equals(this.message, other.message))
            return false;
        if (!Objects.equals(this.user, other.user))
            return false;
        return true;
    }
    
}
