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
@Table(name = "dynamicdata")
@DynamicInsert
@DynamicUpdate
// Lombok
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Dynamicdata implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "dyn_data_id", nullable = false)
    String dynDataId;

    @Column(name = "app_context_id", nullable = false)
    String appContextId;

    @Column(name = "inserted_at", nullable = false)
    OffsetDateTime insertedAt;

    @Column(name = "dyn_value", nullable = false)
    Integer dynValue;


}
