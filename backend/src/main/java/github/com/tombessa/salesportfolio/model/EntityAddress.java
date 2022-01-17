package github.com.tombessa.salesportfolio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {})
@SuperBuilder
@Entity
@Table(name = "entity_address", schema="sales", indexes = {
        @Index(name = "people_address_un", columnList = "id, address_id, people_id", unique = true),
        @Index(name = "client_address_un", columnList = "id, address_id, client_id", unique = true),
        @Index(name = "supplier_address_un", columnList = "id, supplier_id, address_id", unique = true)
})
public class EntityAddress extends BaseEntity{

    @Column(name = "status", length = 20)
    private String status;


    @JoinColumn(name = "supplier_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "entity_address_supplier_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Supplier supplier;


    @JoinColumn(name = "address_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "entity_address_address_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Address address;


    @JoinColumn(name = "people_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "entity_address_people_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Person people;


    @JoinColumn(name = "client_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "entity_address_client_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Client client;

}