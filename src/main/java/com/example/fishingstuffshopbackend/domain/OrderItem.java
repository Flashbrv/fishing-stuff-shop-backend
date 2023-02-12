package com.example.fishingstuffshopbackend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fs_order_items")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE fs_order_items SET deleted = true WHERE id = ? and version = ?")
public class OrderItem extends BaseEntity {
    @Column(name = "qty")
    private int qty;

    @ManyToOne(optional = false)
    private Order order;

    @ManyToOne(optional = false)
    private Product product;

    @Column(name = "comment")
    private String comment;
}
