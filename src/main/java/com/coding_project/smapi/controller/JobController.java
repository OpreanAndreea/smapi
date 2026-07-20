package com.coding_project.smapi.controller;

import com.coding_project.smapi.dto.request.JobRequestDto;
import com.coding_project.smapi.dto.response.JobResponseDto;
import com.coding_project.smapi.enums.JobStatus;
import com.coding_project.smapi.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@Tag(name = "Jobs", description = "Lifecycle management for Jobs: create, read, update, delete, and filter by status")
@SecurityRequirement(name = "bearerAuth")
public class JobController {

    @Autowired
    private JobService jobService;

    // -------------------------------------------------------------------------
    // GET /jobs
    // -------------------------------------------------------------------------
    @Operation(
            summary = "List all Jobs",
            description = "Returns the complete list of Jobs regardless of their status."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Jobs retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = JobResponseDto.class)))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error",
                    content = @Content)
    })
    @GetMapping()
    public List<JobResponseDto> getAll() {
        return jobService.getJobs();
    }

    // -------------------------------------------------------------------------
    // GET /jobs/{id}
    // -------------------------------------------------------------------------
    @Operation(
            summary = "Get a Job by ID",
            description = "Returns the full Job details including nested tasks, materials, and cost summary."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Job found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = JobResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Job not found for the given ID",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public JobResponseDto getJobById(
            @Parameter(description = "Surrogate ID of the Job", example = "42", required = true)
            @PathVariable Long id) {
        return jobService.getJob(id);
    }

    // -------------------------------------------------------------------------
    // GET /jobs/status/{status}
    // -------------------------------------------------------------------------
    @Operation(
            summary = "List Jobs by status",
            description = "Filters the Job list to only those matching the specified status value."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Filtered list returned",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = JobResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid status value supplied",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error",
                    content = @Content)
    })
    @GetMapping("/status/{status}")
    public List<JobResponseDto> getJobsByStatus(
            @Parameter(description = "Job status enum value", example = "OPEN",
                    schema = @Schema(allowableValues = {"PENDING", "OPEN", "CLOSED", "IMMINENT", "OVERDUE"}),
                    required = true)
            @PathVariable JobStatus status) {
        return jobService.getJobsByStatus(status);
    }

    // -------------------------------------------------------------------------
    // POST /jobs/add
    // -------------------------------------------------------------------------
    @Operation(
            summary = "Create a new Job",
            description = "Creates a Job with the provided details. Returns the persisted Job with its generated ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Job created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = JobResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body (e.g. missing required fields)",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error",
                    content = @Content)
    })
    @PostMapping("/add")
    public ResponseEntity<JobResponseDto> createJob(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Job creation payload", required = true,
                    content = @Content(schema = @Schema(implementation = JobRequestDto.class)))
            @RequestBody JobRequestDto jobRequestDto) {
        JobResponseDto createdJob = jobService.createJob(jobRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
    }

    // -------------------------------------------------------------------------
    // PUT /jobs/{id}
    // -------------------------------------------------------------------------
    @Operation(
            summary = "Update an existing Job",
            description = "Performs a full replacement of the Job identified by the given ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Job updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = JobResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Job not found for the given ID",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<JobResponseDto> updateJob(
            @Parameter(description = "Surrogate ID of the Job to update", example = "42", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated Job payload", required = true,
                    content = @Content(schema = @Schema(implementation = JobRequestDto.class)))
            @RequestBody JobRequestDto jobRequestDto) {
        JobResponseDto updatedJob = jobService.updateJob(id, jobRequestDto);
        return ResponseEntity.ok(updatedJob);
    }

    // -------------------------------------------------------------------------
    // DELETE /jobs/{id}
    // -------------------------------------------------------------------------
    @Operation(
            summary = "Delete a Job",
            description = "Permanently removes the Job and all its associated tasks and material allocations."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Job deleted successfully — no body returned",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Job not found for the given ID",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(
            @Parameter(description = "Surrogate ID of the Job to delete", example = "42", required = true)
            @PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
}
