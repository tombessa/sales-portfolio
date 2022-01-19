package github.com.tombessa.salesportfolio.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {})
@SuperBuilder
@Entity
@Table(name = "\"order\"", schema="sales", indexes = {
        @Index(name = "order_idxv3", columnList = "status, discount_rate"),
        @Index(name = "order_idx", columnList = "status, client_id"),
        @Index(name = "order_idxv2", columnList = "status, amount"),
        @Index(name = "order_idxv1", columnList = "status")
})
public class Order extends BaseEntity{

    @Column(name = "status", length = 20)
    private String status;

    @JoinColumn(name = "supplier_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "order_supplier_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Supplier supplier;


    @JoinColumn(name = "client_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "order_client_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Client client;


    @JoinColumn(name = "delivery_address_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "order_address_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Address deliveryAddress;


    @JoinColumn(name = "billing_address_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "order_address_fkv1"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Address billingAddress;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "amount_paid", precision = 10, scale = 2)
    private BigDecimal amountPaid;

    @Column(name = "discount_rate", precision = 10, scale = 2)
    private BigDecimal discountRate;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
    private List<Payment> paymentList = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderItem> orderItemList = new ArrayList<>();
}