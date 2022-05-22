package carlo.hibernate.demo1.repository.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Table(name = "appcontexts")
@DynamicInsert
@DynamicUpdate
// Lombok
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppContext implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false)
    String id;

    @Column(name = "last_update", nullable = false)
    OffsetDateTime lastUpdate;

    @Column(name = "locked", nullable = false)
    boolean locked;

    @Column(name = "status", nullable = false)
    String status;

    @Column(name = "worker")
    String worker;

    @Column(name = "thread_id")
    String threadId;

}
