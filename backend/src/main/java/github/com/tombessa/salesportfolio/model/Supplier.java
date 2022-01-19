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
@Table(name = "supplier", schema="sales", indexes = {
        @Index(name = "supplier_idxv1", columnList = "status"),
        @Index(name = "supplier_idx", columnList = "name, status")
})
public class Supplier extends BaseEntity{

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "name")
    private String name;


    @JoinColumn(name = "people_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "supplier_people_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Person people;

    @Column(name = "observation")
    private String observation;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "supplier")
    private List<EntityAddress> listEntityAddress = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "supplier")
    private List<EntityDocument> listEntityDocument = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "supplier")
    private List<Product> productList = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "supplier")
    private List<Order> orderList = new ArrayList<>();

}