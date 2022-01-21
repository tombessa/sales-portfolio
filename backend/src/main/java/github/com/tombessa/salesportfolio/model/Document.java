package github.com.tombessa.salesportfolio.model;

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
@Table(name = "document", schema="sales", indexes = {
        @Index(name = "document_idx", columnList = "status")
})
public class Document extends BaseEntity{

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "type", length = 10)
    private String type;

    @Column(name = "serial")
    private String serial;

    @Column(name = "observation")
    private String observation;

    @Column(name = "image")
    private byte[] image;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "document")
    private List<EntityDocument> listEntityDocument = new ArrayList<>();

}