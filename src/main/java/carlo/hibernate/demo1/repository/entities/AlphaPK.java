package carlo.hibernate.demo1.repository.entities;

import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(callSuper = true, doNotUseGetters = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlphaPK implements Serializable {
    static final long serialVersionUID = -6358703849111440858L;

    @Column(name="id",nullable=false)
    String id;

    @Column(name="ts",nullable=false)
    OffsetDateTime ts;


}
