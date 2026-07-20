package com.coding_project.smapi.repository;

import com.coding_project.smapi.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findByCode(String code);

    List<InventoryItem> findByCodeIn(List<String> codes);
}
