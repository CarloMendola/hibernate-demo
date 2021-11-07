package carlo.hibernate.demo1.repository.entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "gamma")
// Lombok
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Gamma implements Serializable {

    static final long serialVersionUID = -6366251085416557525L;

    @EqualsAndHashCode.Include
    @Column(name = "id")
    @Id
    String id;

    @Column(name = "col1", nullable = false)
    String col1;

    @Column(name = "col2", nullable = false)
    Boolean col2;

    @Column(name = "col3", nullable = false)
    Integer col3;

    @ToString.Exclude
    @ManyToMany(mappedBy="gammas")
    Set<Alpha> alphas;

    @OneToMany(mappedBy = "gamma", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    Set<Delta> deltas;

}
