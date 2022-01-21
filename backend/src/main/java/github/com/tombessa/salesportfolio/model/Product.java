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
@Table(name = "product", schema = "sales")
public class Product extends BaseEntity{

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "status", length = 10)
    private String status;


    @JoinColumn(name = "supplier_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "product_supplier_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Supplier supplier;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    private List<OrderItem> orderItemList = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    private List<Price> priceList = new ArrayList<>();

}