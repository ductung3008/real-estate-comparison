package com.example.realestate.repository;
/*
 * @author HongAnh
 * @created 06 / 11 / 2024 - 11:50 SA
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.domain.dto.stat.PriceRangeStats;
import com.example.realestate.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    @Query(value = "SELECT * FROM projects LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Project> findProjectsWithPagination(
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    @Query("SELECT " +
            "   CASE " +
            "       WHEN p.maxSellingPrice >= 0 AND p.maxSellingPrice < 2000000000 THEN '<2' " +
            "       WHEN p.maxSellingPrice >= 2000000000 AND p.maxSellingPrice < 3000000000 THEN '2-3' " +
            "       WHEN p.maxSellingPrice >= 3000000000 AND p.maxSellingPrice < 4000000000 THEN '3-4' " +
            "       WHEN p.maxSellingPrice >= 4000000000 AND p.maxSellingPrice < 5000000000 THEN '4-5' " +
            "       WHEN p.maxSellingPrice >= 5000000000 AND p.maxSellingPrice < 6000000000 THEN '5-6' " +
            "       WHEN p.maxSellingPrice >= 6000000000 AND p.maxSellingPrice < 7000000000 THEN '6-7' " +
            "       WHEN p.maxSellingPrice >= 7000000000 AND p.maxSellingPrice < 8000000000 THEN '7-8' " +
            "       WHEN p.maxSellingPrice >= 8000000000 AND p.maxSellingPrice < 9000000000 THEN '8-9' " +
            "       WHEN p.maxSellingPrice >= 9000000000 AND p.maxSellingPrice < 10000000000 THEN '9-10' " +
            "       ELSE '>10' " +
            "   END AS priceRange, " +
            "   COUNT(p) AS count " +
            "FROM Project p " +
            "GROUP BY priceRange " +
            "ORDER BY priceRange")
    List<Object[]> findPriceRangeStats();

    @Query(value = """
            SELECT 
                substring(address FROM 'Q\\. ([^,]+)') AS district, 
                COUNT(*) AS count
            FROM 
                projects
            GROUP BY 
                district
            ORDER BY 
                count DESC
            """, nativeQuery = true)
    List<Object[]> findDistrictStats();

    @Query(value = """
            SELECT 
                CASE 
                    WHEN total_area < 0.5 THEN '< 0.5 ha'
                    WHEN total_area >= 0.5 AND total_area < 1 THEN '0.5 - 1 ha'
                    WHEN total_area >= 1 AND total_area < 5 THEN '1 - 5 ha'
                    WHEN total_area >= 5 AND total_area < 10 THEN '5 - 10 ha'
                    WHEN total_area >= 10 AND total_area < 100 THEN '10 - 100 ha'
                    WHEN total_area >= 100 THEN '> 100 ha'
                END AS area_category,
                COUNT(*) AS project_count,
                CASE 
                    WHEN total_area < 0.5 THEN 1
                    WHEN total_area >= 0.5 AND total_area < 1 THEN 2
                    WHEN total_area >= 1 AND total_area < 5 THEN 3
                    WHEN total_area >= 5 AND total_area < 10 THEN 4
                    WHEN total_area >= 10 AND total_area < 100 THEN 5
                    WHEN total_area >= 100 THEN 6
                END AS area_order
            FROM projects
            GROUP BY area_category, area_order
            ORDER BY area_order ASC, project_count DESC
            """, nativeQuery = true)
    List<Object[]> findAreaCategoryStats();

    @Query(value = """
        SELECT 
            bike_parking_category,
            COUNT(*) AS project_count
        FROM (
            SELECT 
                CASE 
                    WHEN bike_parking_monthly IS NULL OR bike_parking_monthly <= 0 THEN 'No Fee'
                    WHEN bike_parking_monthly > 0 AND bike_parking_monthly <= 100000 THEN '0 - 100,000 VND'
                    WHEN bike_parking_monthly > 100000 AND bike_parking_monthly <= 500000 THEN '100,000 - 500,000 VND'
                    WHEN bike_parking_monthly > 500000 AND bike_parking_monthly <= 1000000 THEN '500,000 - 1 Million VND'
                    ELSE '> 1 Million VND'
                END AS bike_parking_category
            FROM projects
        ) subquery
        GROUP BY bike_parking_category
        ORDER BY 
            CASE bike_parking_category
                WHEN 'No Fee' THEN 1
                WHEN '0 - 100,000 VND' THEN 2
                WHEN '100,000 - 500,000 VND' THEN 3
                WHEN '500,000 - 1 Million VND' THEN 4
                ELSE 5
            END
        """, nativeQuery = true)
    List<Object[]> findBikeParkingCategoriesWithCounts();

    @Query(value = """
        SELECT 
            car_parking_category,
            COUNT(*) AS project_count
        FROM (
            SELECT 
                CASE 
                    WHEN car_parking_monthly IS NULL OR car_parking_monthly <= 0 THEN 'No Fee'
                    WHEN car_parking_monthly > 0 AND car_parking_monthly <= 100000 THEN '0 - 100,000 VND'
                    WHEN car_parking_monthly > 100000 AND car_parking_monthly <= 500000 THEN '100,000 - 500,000 VND'
                    WHEN car_parking_monthly > 500000 AND car_parking_monthly <= 1000000 THEN '500,000 - 1 Million VND'
                    ELSE '> 1 Million VND'
                END AS car_parking_category
            FROM projects
        ) subquery
        GROUP BY car_parking_category
        ORDER BY 
            CASE car_parking_category
                WHEN 'No Fee' THEN 1
                WHEN '0 - 100,000 VND' THEN 2
                WHEN '100,000 - 500,000 VND' THEN 3
                WHEN '500,000 - 1 Million VND' THEN 4
                ELSE 5
            END
        """, nativeQuery = true)
    List<Object[]> findCarParkingCategoriesWithCounts();
}
