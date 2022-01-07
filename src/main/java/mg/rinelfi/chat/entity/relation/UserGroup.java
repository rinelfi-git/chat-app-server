package mg.rinelfi.chat.entity.relation;

import mg.rinelfi.chat.entity.Group;
import mg.rinelfi.chat.entity.User;
import mg.rinelfi.chat.entity.emmbed.UserGroupKey;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "ug_user_group")
public class UserGroup implements Serializable {
    
    @EmbeddedId
    private UserGroupKey id;
    @ManyToOne
    @MapsId("group")
    private Group group;
    @ManyToOne
    @MapsId("user")
    private User user;
    @Column(name = "ug_name")
    private String name;
    
    public UserGroup() {
        this.id = new UserGroupKey();
    }
    
    public UserGroupKey getId() {
        return id;
    }
    
    public void setId(UserGroupKey id) {
        this.id = id;
    }
    
    public Group getGroup() {
        return group;
    }
    
    public void setGroup(Group group) {
        this.group = group;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
