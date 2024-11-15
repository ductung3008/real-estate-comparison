package com.example.realestate.domain.entity;
/*
 * @author HongAnh
 * @created 06 / 11 / 2024 - 10:13 SA
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.constant.PlaceCategory;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "places")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column(nullable = false)
    String name;

    BigDecimal latitude;
    BigDecimal longitude;
    BigDecimal distance;
    BigDecimal rating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    PlaceCategory category;

    @ManyToOne
    @JoinColumn(nullable = false, name = "project_id")
    Project project;

    public void addProject(Project project) {
        if (Objects.isNull(this.project)) {
            setProject(project);
        }
    }
}
