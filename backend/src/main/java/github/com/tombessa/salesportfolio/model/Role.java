package github.com.tombessa.salesportfolio.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {})
@SuperBuilder
@Entity
@Table(name = "role", schema = "sales")
public class Role extends BaseEntity{

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "status", length = 10)
    private String status;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
    private List<UserAccess> roleList = new ArrayList<>();

}