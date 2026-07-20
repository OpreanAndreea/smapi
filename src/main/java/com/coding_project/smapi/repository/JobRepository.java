package com.coding_project.smapi.repository;

import com.coding_project.smapi.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("SELECT j FROM Job j WHERE j.startDate IS NULL AND j.endDate IS NULL")
    List<Job> findPendingJobs();

    @Query("SELECT j FROM Job j WHERE j.startDate IS NOT NULL AND j.endDate IS NULL")
    List<Job> findOpenJobs();

    @Query("SELECT j FROM Job j WHERE j.startDate IS NOT NULL AND j.endDate IS NOT NULL")
    List<Job> findClosedJobs();

    // Overdue: deadline has passed, but it's not closed yet
    @Query("SELECT j FROM Job j WHERE j.endDate IS NULL AND j.deadline < :now")
    List<Job> findOverdueJobs(@Param("now") LocalDateTime now);

    // Imminent: deadline is in the future but less than 7 days away, and it's not closed
    @Query("SELECT j FROM Job j WHERE j.endDate IS NULL AND j.deadline > :now AND j.deadline <= :imminentLimit")
    List<Job> findImminentJobs(@Param("now") LocalDateTime now, @Param("imminentLimit") LocalDateTime imminentLimit);
}