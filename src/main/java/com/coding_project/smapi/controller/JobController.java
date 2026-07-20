package com.coding_project.smapi.controller;

import com.coding_project.smapi.dto.request.JobRequestDto;
import com.coding_project.smapi.dto.response.JobResponseDto;
import com.coding_project.smapi.enums.JobStatus;
import com.coding_project.smapi.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping()
    public List<JobResponseDto> getAll() {
        return jobService.getJobs();
    }

    @GetMapping("/{id}")
    public JobResponseDto getJobById(@PathVariable Long id) {
        return jobService.getJob(id);
    }

    @GetMapping("/status/{status}")
    public List<JobResponseDto> getJobsByStatus(@PathVariable JobStatus status) {
        return jobService.getJobsByStatus(status);
    }

    @PostMapping("/add")
    public ResponseEntity<JobResponseDto> createJob(@RequestBody JobRequestDto jobRequestDto) {
        JobResponseDto createdJob = jobService.createJob(jobRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobResponseDto> updateJob(
            @PathVariable Long id,
            @RequestBody JobRequestDto jobRequestDto) {
        JobResponseDto updatedJob = jobService.updateJob(id, jobRequestDto);
        return ResponseEntity.ok(updatedJob);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

}
