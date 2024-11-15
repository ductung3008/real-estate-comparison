package com.example.realestate.repository;

import com.example.realestate.domain.entity.Price;
import com.example.realestate.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {
    List<Price> findAllByProject(Project project);
}
