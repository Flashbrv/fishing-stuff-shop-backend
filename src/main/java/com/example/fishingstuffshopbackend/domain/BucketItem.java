package com.example.fishingstuffshopbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fs_bucket_items")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE fs_bucket_items SET deleted = true WHERE id = ? and version = ?")
public class BucketItem extends BaseEntity {
    @Column(name = "qty")
    private int qty;

    @ManyToOne(optional = false)
    private Bucket bucket;

    @ManyToOne(optional = false)
    private Product product;

    @Column(name = "comment")
    private String comment;
}
