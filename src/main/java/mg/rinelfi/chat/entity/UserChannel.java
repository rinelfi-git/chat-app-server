package mg.rinelfi.chat.entity;

import mg.rinelfi.chat.entity.relation.UserChannelUser;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity(name = "uc_user_channel")
public class UserChannel extends Channel implements Serializable {

    @OneToMany(mappedBy = "channel", fetch = FetchType.EAGER)
    private Set<UserChannelUser> userChannelLinks;

    public UserChannel() {
        super();
        this.userChannelLinks = new HashSet<>();
    }

    public Set<UserChannelUser> getUserChannelLinks() {
        return userChannelLinks;
    }

    public void setUserChannelLinks(Set<UserChannelUser> users) {
        this.userChannelLinks = users;
    }

}
