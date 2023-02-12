package com.example.fishingstuffshopbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fs_buckets")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE fs_buckets SET deleted = true WHERE id = ? and version = ?")
public class Bucket extends BaseEntity {

    @OneToMany(mappedBy = "bucket")
    private Set<BucketItem> bucketItems = new HashSet<>();
}
