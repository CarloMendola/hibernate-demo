package carlo.hibernate.demo1.repository.entities;


import java.io.Serializable;

import java.util.Set;
import javax.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "alpha")
// Lombok
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Alpha implements Serializable {

    static final long serialVersionUID = -6366051005426557525L;

    @EmbeddedId
    @EqualsAndHashCode.Include
    AlphaPK id;

    @Column(name = "col1", nullable = false)
    String col1;

    @Column(name = "col2", nullable = false)
    Boolean col2;

    @Column(name = "col3", nullable = false)
    Integer col3;

    @OneToMany(mappedBy="alpha", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    Set<Beta> betas;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name="alpha_gamma"
        , joinColumns={
        @JoinColumn(name="alpha_id", nullable=false),
        @JoinColumn(name="alpha_ts", nullable=false)
    }
        , inverseJoinColumns={
        @JoinColumn(name="gamma_id", nullable=false)
    }
    )
    Set<Gamma> gammas;

}
