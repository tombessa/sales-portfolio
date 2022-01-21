package github.com.tombessa.salesportfolio.model;

import github.com.tombessa.salesportfolio.enums.ClientStatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {})
@SuperBuilder
@Entity
@Table(name = "client", schema = "sales")
public class Client extends BaseEntity{
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private ClientStatusEnum status;


    @JoinColumn(name = "people_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "client_people_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Person people;

    @Column(name = "name")
    private String name;

    @Column(name = "observation")
    private String observation;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    private List<Order> orderList = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    private List<EntityAddress> listEntityAddress = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    private List<EntityDocument> listEntityDocument = new ArrayList<>();
}