package github.com.tombessa.salesportfolio.model;

import github.com.tombessa.salesportfolio.enums.RoleStatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10)
    private RoleStatusEnum status;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,mappedBy = "role")
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<UserAccess> userAccessList = new ArrayList<>();

}