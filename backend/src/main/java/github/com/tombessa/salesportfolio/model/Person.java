package github.com.tombessa.salesportfolio.model;

import github.com.tombessa.salesportfolio.enums.PersonStatusEnum;
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
@Table(name = "people", schema = "sales")
public class Person extends BaseEntity{
    @JoinColumn(name = "address_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "entity_address_address_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private PersonStatusEnum status;

    @Column(name = "name")
    private String name;

    @Column(name = "cellphone", length = 20)
    private String cellPhone;

    @Column(name = "otherphone", length = 20)
    private String otherPhone;

    @Column(name = "email", length = 25)
    private String email;

    @Column(name = "birthdate")
    private Instant birthdate;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "people")
    private List<UserAccess> personList = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "people")
    private List<Client> clientList = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "people")
    private List<EntityAddress> listEntityAddress = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "people")
    private List<Supplier> listSupplier = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "people")
    private List<EntityDocument> listEntityDocument = new ArrayList<>();

}