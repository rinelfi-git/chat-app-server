package mg.rinelfi.chat.entity.relation;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import mg.rinelfi.chat.entity.User;
import mg.rinelfi.chat.entity.UserChannel;
import mg.rinelfi.chat.entity.emmbed.UserChannelUserKey;


@Entity
@Table(name = "ucu_user_channel_user")
public class UserChannelUser implements Serializable {
    
    @EmbeddedId
	private UserChannelUserKey id;
    @ManyToOne
    @MapsId("channel")
    private UserChannel channel;
    @ManyToOne
    @MapsId("user")
    private User user;
	@Column(name = "ucu_username")
	private String username;

	public UserChannelUser() {
        this.id = new UserChannelUserKey();
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    public UserChannelUserKey getId() {
        return id;
    }

    public void setId(UserChannelUserKey id) {
        this.id = id;
    }

    public UserChannel getChannel() {
        return channel;
    }

    public void setChannel(UserChannel channel) {
        this.channel = channel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
