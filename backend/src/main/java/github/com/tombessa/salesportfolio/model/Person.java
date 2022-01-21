package github.com.tombessa.salesportfolio.model;

import github.com.tombessa.salesportfolio.enums.PersonStatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {})
@SuperBuilder
@Entity
@Table(name = "people", schema = "sales")
public class Person extends BaseEntity{

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
    private Date birthdate;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,mappedBy = "people")
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<UserAccess> userAccessList = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,mappedBy = "people")
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<Client> clientList = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,mappedBy = "people")
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<EntityAddress> entityAddressList = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,mappedBy = "people")
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<Supplier> supplierList = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,mappedBy = "people")
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<EntityDocument> entityDocumentList = new ArrayList<>();

}