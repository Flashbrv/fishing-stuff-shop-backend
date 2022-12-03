package com.example.fishingstuffshopbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fs_order_item")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE fs_order_item SET deleted = true WHERE id = ? and version = ?")
public class OrderItemEntity extends BaseEntity {
    @Column(name = "qty")
    private int qty;

    @ManyToOne(optional = false)
    private OrderEntity order;

    @ManyToOne(optional = false)
    private ProductEntity product;
}
