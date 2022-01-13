package mg.rinelfi.chat.entity;

import mg.rinelfi.chat.entity.relation.UserGroup;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Group")
public class Group extends Channel implements Serializable {

    @Column(name = "g_name")
    private String name;
    @OneToOne()
    @JoinColumn
    private User administrator;
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private Set<UserGroup> userGroupLinks;

    public Group() {
        super();
        this.userGroupLinks = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAdministrator() {
        return administrator;
    }

    public void setAdministrator(User administrator) {
        this.administrator = administrator;
    }

    public Set<UserGroup> getUserGroupLinks() {
        return userGroupLinks;
    }

    public void setUserGroupLinks(Set<UserGroup> users) {
        this.userGroupLinks = users;
    }    

}
