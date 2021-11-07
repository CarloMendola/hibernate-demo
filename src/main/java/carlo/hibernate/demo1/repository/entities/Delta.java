package carlo.hibernate.demo1.repository.entities;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "delta")
// Lombok
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Delta implements Serializable {
    static final long serialVersionUID = -2336251005426547525L;

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "type", nullable = false)
    private DeltaType type;

    @ToString.Exclude
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="gamma_id", nullable=false)
    Gamma gamma;

}
