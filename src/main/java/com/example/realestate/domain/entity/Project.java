package com.example.realestate.domain.entity;
/*
 * @author HongAnh
 * @created 06 / 11 / 2024 - 9:57 SA
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "projects")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column(nullable = false)
    String code;

    @Column(nullable = false)
    String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    String address;

//    @Column(nullable = false)
    @Column
    String developerName;

//    @Column(nullable = false, columnDefinition = "TEXT")
    @Column(columnDefinition = "TEXT")
    String masterPlanUrl;

    @Column(columnDefinition = "TEXT")
    String infrastructureMapUrl;

    LocalDate constructionStartDateFrom;
    LocalDate handoverDate;

    String rank;

    @Column(precision = 10, scale = 2)
    BigDecimal totalArea;

    @Column(precision = 5, scale = 2)
    BigDecimal ctsnDens;

//    @Column(nullable = false)
    @Column
    Integer totalProperty;

    @Column(precision = 15, scale = 2)
    BigDecimal minSellingPrice;

    @Column(precision = 15, scale = 2)
    BigDecimal maxSellingPrice;

    @Column(precision = 15, scale = 2)
    BigDecimal minUnitPrice;

    @Column(precision = 15, scale = 2)
    BigDecimal maxUnitPrice;

    Integer blocks;
    Integer numberEle;
    Integer numberLivingFloor;
    Integer numberBasement;
    Integer minPropPerFloor;
    Integer maxPropPerFloor;

    Integer bikeParkingMonthly;
    Integer carParkingMonthly;

    @Column(precision = 15, scale = 10)  // Thay đổi precision và scale
    BigDecimal latitude;

    @Column(precision = 15, scale = 10)  // Thay đổi precision và scale
    BigDecimal longitude;

    @ManyToOne
    User createdBy;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Place> places;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    List<PropertyType> types;

    @CreationTimestamp
    Timestamp createdAt;

    @UpdateTimestamp
    Timestamp updatedAt;

    public void addProperty(PropertyType type) {
        if (Objects.isNull(types)) {
            types = new ArrayList<>();
        }
        types.add(type);
        type.setProject(this);
    }
}
