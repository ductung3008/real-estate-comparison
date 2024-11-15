package com.example.realestate.repository;
/*
 * @author HongAnh
 * @created 06 / 11 / 2024 - 11:50 SA
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.domain.entity.Place;
import com.example.realestate.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlaceRepository extends JpaRepository<Place, UUID> {
    @Query(value = "SELECT * FROM places WHERE project_id = :projectId LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Place> findPlacesByProjectWithPagination(
            @Param("projectId") UUID projectId,
            @Param("limit") int limit,
            @Param("offset") int offset
    );
}
