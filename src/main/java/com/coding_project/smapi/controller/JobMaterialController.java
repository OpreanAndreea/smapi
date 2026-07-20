package com.coding_project.smapi.controller;

import com.coding_project.smapi.dto.request.JobMaterialRequestDto;
import com.coding_project.smapi.dto.response.JobMaterialResponseDto;
import com.coding_project.smapi.service.JobMaterialService;
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
@RequestMapping("/jobs/{jobId}/materials")
@Tag(name = "Job Materials", description = "Allocate, list, and remove inventory materials for a specific Job")
@SecurityRequirement(name = "bearerAuth")
public class JobMaterialController {

    @Autowired
    private JobMaterialService jobMaterialService;

    // -------------------------------------------------------------------------
    // GET /jobs/{jobId}/materials
    // -------------------------------------------------------------------------
    @Operation(
            summary = "List materials allocated to a Job",
            description = "Returns all inventory items currently allocated to the specified Job, with per-unit and total costs."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Materials list returned",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = JobMaterialResponseDto.class)))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token", content = @Content),
            @ApiResponse(responseCode = "404", description = "Job not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<JobMaterialResponseDto>> getMaterialsByJob(
            @Parameter(description = "Surrogate ID of the Job", example = "42", required = true)
            @PathVariable Long jobId) {
        return ResponseEntity.ok(jobMaterialService.getMaterialsByJobId(jobId));
    }

    // -------------------------------------------------------------------------
    // POST /jobs/{jobId}/materials
    // -------------------------------------------------------------------------
    @Operation(
            summary = "Allocate materials to a Job",
            description = "Allocates one or more inventory items to the Job. " +
                          "Decrements the corresponding inventory stock. Each allocation line stores the unit price at allocation time."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Materials allocated — allocation records returned",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = JobMaterialResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid request body or insufficient stock", content = @Content),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token", content = @Content),
            @ApiResponse(responseCode = "404", description = "Job or inventory item not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<List<JobMaterialResponseDto>> allocateMaterials(
            @Parameter(description = "Surrogate ID of the Job", example = "42", required = true)
            @PathVariable Long jobId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "List of inventory items with quantities to allocate", required = true,
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = JobMaterialRequestDto.class))))
            @RequestBody List<JobMaterialRequestDto> materials) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jobMaterialService.allocateMaterials(jobId, materials));
    }

    // -------------------------------------------------------------------------
    // DELETE /jobs/{jobId}/materials/{materialId}
    // -------------------------------------------------------------------------
    @Operation(
            summary = "Remove a material allocation from a Job",
            description = "Removes a single allocation record and returns the quantity to inventory stock."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Allocation removed — no body returned", content = @Content),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token", content = @Content),
            @ApiResponse(responseCode = "404", description = "Job or allocation record not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content)
    })
    @DeleteMapping("/{materialId}")
    public ResponseEntity<Void> removeMaterial(
            @Parameter(description = "Surrogate ID of the Job", example = "42", required = true)
            @PathVariable Long jobId,
            @Parameter(description = "Surrogate ID of the material allocation record to remove", example = "15", required = true)
            @PathVariable Long materialId) {
        jobMaterialService.removeMaterial(jobId, materialId);
        return ResponseEntity.noContent().build();
    }
}
