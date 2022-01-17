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
@Table(name = "entity_document", schema="sales", indexes = {
        @Index(name = "people_document_un", columnList = "id, document_id, people_id", unique = true),
        @Index(name = "client_document_un", columnList = "id, document_id, client_id", unique = true),
        @Index(name = "supplier_document_un", columnList = "id, supplier_id, document_id", unique = true)
})
public class EntityDocument extends BaseEntity{

    @Column(name = "status", length = 20)
    private String status;


    @JoinColumn(name = "supplier_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "supplier_document_supplier_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Supplier supplier;


    @JoinColumn(name = "document_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "supplier_document_document_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Document document;


    @JoinColumn(name = "people_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "entity_document_people_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Person people;

    @JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "entity_document_client_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Client client;

}