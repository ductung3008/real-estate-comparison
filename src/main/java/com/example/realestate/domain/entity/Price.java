package com.example.realestate.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "prices")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer unit;

    Integer year;

    BigDecimal percent;

    BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "project_id")
    Project project;

    public void addProject(Project project) {
        if (Objects.isNull(this.project)) {
            setProject(project);
        }
    }
}
