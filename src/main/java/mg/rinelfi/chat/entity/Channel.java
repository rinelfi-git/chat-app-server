package mg.rinelfi.chat.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;


@Entity(name = "Channel")
@Table(name = "c_channel")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Channel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
	private long id;
    
    @OneToMany(mappedBy = "channel")
    private Set<Message> messages;
    
    public Channel() {
        this.messages = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Channel other = (Channel) obj;
        return Objects.equals(getId(), other.getId());
    }
}
