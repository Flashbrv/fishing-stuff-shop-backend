package com.example.fishingstuffshopbackend.model;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fs_products")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE fs_products SET deleted = true WHERE id = ? and version = ?")
public class Product extends BaseEntity {
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToMany(mappedBy = "products", cascade = CascadeType.PERSIST)
    private List<Category> categories = new ArrayList<>();

    private void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category) {
        Objects.requireNonNull(category);
        category.getProducts().add(this);
        categories.add(category);
    }

    public void removeCategory(Category category) {
        category.getProducts().remove(this);
        categories.remove(category);
    }
}
