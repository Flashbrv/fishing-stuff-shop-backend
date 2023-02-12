package com.example.fishingstuffshopbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

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
}
