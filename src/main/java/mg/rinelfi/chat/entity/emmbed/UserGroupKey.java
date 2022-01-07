package mg.rinelfi.chat.entity.emmbed;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserGroupKey implements Serializable {
    @Column(name = "ug_group")
    private long group;
    @Column(name = "ug_user")
    private long user;
    
    public UserGroupKey() {}
    
    public long getGroup() {
        return group;
    }
    
    public void setGroup(long group) {
        this.group = group;
    }
    
    public long getUser() {
        return user;
    }
    
    public void setUser(long user) {
        this.user = user;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGroupKey that = (UserGroupKey) o;
        return group == that.group && user == that.user;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(group, user);
    }
}
