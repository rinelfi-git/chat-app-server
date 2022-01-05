package mg.rinelfi.chat.entity;

import javax.persistence.*;

@Entity
@Table(name = "group")
public class Group extends Channel{
    @Column
    private String name;
    @OneToOne @JoinColumn
    private User administrator;
}
