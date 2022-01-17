package github.com.tombessa.salesportfolio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {})
@SuperBuilder
@Entity
@Table(name = "order_item", schema="sales", indexes = {
        @Index(name = "order_item_idx", columnList = "status, order_id")
})
public class OrderItem extends BaseEntity{

    @Column(name = "status", length = 20)
    private String status;


    @JoinColumn(name = "product_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "order_item_product_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Product product;


    @JoinColumn(name = "price_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "order_item_price_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Price price;


    @JoinColumn(name = "order_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "order_item_order_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Order order;

    @Column(name = "qtd")
    private Integer qtd;

    @Column(name = "UNITARY_VALUE", precision = 10, scale = 2)
    private BigDecimal unitaryValue;

    @Column(name = "TOTAL_VALUE", precision = 10, scale = 2)
    private BigDecimal totalValue;

    @Column(name = "discount_rate", precision = 10, scale = 2)
    private BigDecimal discountRate;

}