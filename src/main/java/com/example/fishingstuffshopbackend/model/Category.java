package com.example.fishingstuffshopbackend.model;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fs_categories")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE fs_categories SET deleted = true WHERE id = ? and version = ?")
public class Category extends BaseEntity {
    @Column(name = "title", nullable = false, unique = true)
    @Size(min = 3, max = 75)
    private String title;

    @ManyToMany
    @JoinTable(name = "products_categories",
        joinColumns = @JoinColumn(name = "category_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();

    private void setProducts(List<Product> products) {
        this.products = products;
    }
}
