package carlo.hibernate.demo1.repository.entities;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "delta_type")
// Lombok
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeltaType implements Serializable {
    static final long serialVersionUID = -2332251011426547525L;

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id", nullable = false)
    private String id;

}
