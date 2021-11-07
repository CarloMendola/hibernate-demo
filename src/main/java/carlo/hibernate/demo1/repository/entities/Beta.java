package carlo.hibernate.demo1.repository.entities;


import java.io.Serializable;
import javax.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "beta")
// Lombok
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Beta implements Serializable {

    static final long serialVersionUID = -6366251005426557525L;

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "col1", nullable = false)
    String col1;

    @Column(name = "col2", nullable = false)
    Boolean col2;

    @Column(name = "col3", nullable = false)
    Integer col3;

    @ToString.Exclude
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumns(value = {
        @JoinColumn(name="alpha_id", nullable=false),
        @JoinColumn(name="alpha_ts", nullable=false)
    })
    Alpha alpha;



}
