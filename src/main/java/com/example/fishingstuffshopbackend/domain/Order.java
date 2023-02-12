package com.example.fishingstuffshopbackend.domain;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fs_orders")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE fs_orders SET deleted = true WHERE id = ? and version = ?")
public class Order extends BaseEntity {
    @ManyToOne(optional = false)
    private User user;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    private void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addOrderItem(OrderItem orderItem) {
        Objects.requireNonNull(orderItem);
        orderItem.setOrder(this);
        orderItems.add(orderItem);
    }

    public void removeOrderItem(OrderItem orderItem) {
        Objects.requireNonNull(orderItem);
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }
}
