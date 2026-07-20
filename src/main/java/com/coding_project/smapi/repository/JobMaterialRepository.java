package com.coding_project.smapi.repository;

import com.coding_project.smapi.entity.JobMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobMaterialRepository extends JpaRepository<JobMaterial, Long> {

    List<JobMaterial> findByJobId(Long jobId);
}
