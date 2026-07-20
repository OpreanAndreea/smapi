package com.coding_project.smapi.controller;

import com.coding_project.smapi.dto.request.JobMaterialRequestDto;
import com.coding_project.smapi.dto.response.JobMaterialResponseDto;
import com.coding_project.smapi.service.JobMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs/{jobId}/materials")
public class JobMaterialController {

    @Autowired
    private JobMaterialService jobMaterialService;

    @GetMapping
    public ResponseEntity<List<JobMaterialResponseDto>> getMaterialsByJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(jobMaterialService.getMaterialsByJobId(jobId));
    }

    @PostMapping
    public ResponseEntity<List<JobMaterialResponseDto>> allocateMaterials(
            @PathVariable Long jobId,
            @RequestBody List<JobMaterialRequestDto> materials) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jobMaterialService.allocateMaterials(jobId, materials));
    }

    @DeleteMapping("/{materialId}")
    public ResponseEntity<Void> removeMaterial(
            @PathVariable Long jobId,
            @PathVariable Long materialId) {
        jobMaterialService.removeMaterial(jobId, materialId);
        return ResponseEntity.noContent().build();
    }
}