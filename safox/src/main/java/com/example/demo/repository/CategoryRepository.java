package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.Category;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findAllByStatus(Boolean status, Sort sort);
}
