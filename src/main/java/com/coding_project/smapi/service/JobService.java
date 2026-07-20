package com.coding_project.smapi.service;

import com.coding_project.smapi.dto.request.JobRequestDto;
import com.coding_project.smapi.dto.response.JobResponseDto;
import com.coding_project.smapi.enums.JobStatus;
import com.coding_project.smapi.mapper.JobMapper;

import com.coding_project.smapi.entity.Job;
import com.coding_project.smapi.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobMapper jobMapper;

    public List<JobResponseDto> getJobs() {
        return jobRepository.findAll().stream()
                .map(jobMapper::toDto)
                .toList();
    }

    public JobResponseDto getJob(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return jobMapper.toDto(job);
    }

    public List<JobResponseDto> getJobsByStatus(JobStatus status) {
        LocalDateTime now = LocalDateTime.now();

        List<Job> jobs = switch (status) {
            case PENDING -> jobRepository.findPendingJobs();
            case OPEN -> jobRepository.findOpenJobs();
            case CLOSED -> jobRepository.findClosedJobs();
            case OVERDUE -> jobRepository.findOverdueJobs(now);
            case IMMINENT -> jobRepository.findImminentJobs(now, now.plusDays(7));
        };

        return jobs.stream()
                .map(jobMapper::toDto)
                .toList();
    }

    public JobResponseDto createJob(JobRequestDto jobRequestDto) {
        Job job = new Job();

        job.setTitle(jobRequestDto.getTitle());
        job.setDescription(jobRequestDto.getDescription());
        job.setStartDate(jobRequestDto.getStartDate());
        job.setEndDate(jobRequestDto.getEndDate());
        job.setDeadline(jobRequestDto.getDeadline());

        return jobMapper.toDto(jobRepository.save(job));
    }

    public JobResponseDto updateJob(Long id, JobRequestDto jobRequestDto) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found with id: " + id));

        if (jobRequestDto.getTitle() != null) {
            job.setTitle(jobRequestDto.getTitle());
        }

        if(jobRequestDto.getDescription() != null) {
            job.setDescription(jobRequestDto.getDescription());
        }

        if(jobRequestDto.getStartDate() != null) {
            job.setStartDate(jobRequestDto.getStartDate());
        }

        if(jobRequestDto.getEndDate() != null) {
            job.setEndDate(jobRequestDto.getEndDate());
        }

        if(jobRequestDto.getDeadline() != null) {
            job.setDeadline(jobRequestDto.getDeadline());
        }

        return jobMapper.toDto(jobRepository.save(job));
    }

    public void deleteJob(Long id) {
        if (!jobRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found with id: " + id);
        }
        jobRepository.deleteById(id);
    }
}
