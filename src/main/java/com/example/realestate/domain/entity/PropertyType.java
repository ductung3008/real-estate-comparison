package com.example.realestate.domain.entity;
/*
 * @author HongAnh
 * @created 06 / 11 / 2024 - 10:18 SA
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "property_types")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PropertyType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    BigDecimal numberOfBedroom;
    BigDecimal minArea;
    BigDecimal maxArea;
    BigDecimal minPrice;
    BigDecimal maxPrice;

    @CreationTimestamp
    Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "project_id")
    Project project;
}
