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
@Table(name = "fs_product")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE fs_product SET deleted = true WHERE id = ? and version = ?")
public class ProductEntity extends BaseEntity {
    @Column(name = "short_name", nullable = false, unique = true)
    private String shortName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price")
    private int price;

    @ManyToOne(optional = false)
    private TypeEntity type;
}
