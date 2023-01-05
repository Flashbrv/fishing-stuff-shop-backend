package com.example.fishingstuffshopbackend.model;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "fs_product_type")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE fs_product_type SET deleted = true WHERE id = ? and version = ?")
public class ProductTypeEntity extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    @Size(min = 3, max = 75)
    private String name;
}
