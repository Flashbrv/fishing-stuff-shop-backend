package com.example.fishingstuffshopbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fs_type")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE fs_type SET deleted = true WHERE id = ? and version = ?")
public class TypeEntity extends BaseEntity {
    @Column(name = "group_name", nullable = false, unique = true)
    @Size(min = 3, max = 75)
    private String groupName;
}
