package github.com.tombessa.salesportfolio.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {})
@SuperBuilder
@Entity
@Table(name = "price", schema = "sales", indexes = {
        @Index(name = "price_idxv1", columnList = "status, product_id"),
        @Index(name = "price_idx", columnList = "status, profit_margin")
})
public class Price extends BaseEntity{

    @Column(name = "status", length = 10)
    private String status;

    @Column(name = "value", precision = 10, scale = 2)
    private BigDecimal value;

    @Column(name = "profit_margin", precision = 10, scale = 2)
    private BigDecimal profitMargin;


    @JoinColumn(name = "product_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "price_product_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Product product;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "price")
    private List<OrderItem> orderItemList = new ArrayList<>();
}