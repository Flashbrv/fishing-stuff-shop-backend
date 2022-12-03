package com.example.fishingstuffshopbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fs_order")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE fs_order SET deleted = true WHERE id = ? and version = ?")
public class OrderEntity extends BaseEntity {
    @Column(name = "number", unique = true)
    private long number;

    @Column(name = "info_sent")
    private boolean infoSent;

    @ManyToOne(optional = false)
    private UserEntity user;

    @Column(name = "paid")
    private boolean paid;

    @Column(name = "collected")
    private boolean collected;

    @Column(name = "shipped")
    private boolean shipped;

    @Column(name = "shipped_number")
    private String shippedNumber;

    @Column(name = "shipped_number_sent")
    private boolean shippedNumberSent;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    private void setOrderItems(List<OrderItemEntity> orderItems) {
        this.orderItems = orderItems;
    }

    public void addOrderItem(OrderItemEntity orderItem) {
        Objects.requireNonNull(orderItem);
        orderItem.setOrder(this);
        orderItems.add(orderItem);
    }

    public void removeOrderItem(OrderItemEntity orderItem) {
        Objects.requireNonNull(orderItem);
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }
}
