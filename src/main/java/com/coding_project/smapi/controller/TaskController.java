package com.coding_project.smapi.controller;

import com.coding_project.smapi.dto.request.TaskRequestDto;
import com.coding_project.smapi.dto.response.TaskResponseDto;
import com.coding_project.smapi.enums.Priority;
import com.coding_project.smapi.enums.TaskStatus;
import com.coding_project.smapi.service.TaskService;
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
@RequestMapping("/tasks")
@Tag(name = "Tasks", description = "Task management: create, retrieve, filter by status/priority/deadline, update, and delete")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // -------------------------------------------------------------------------
    // GET /tasks
    // -------------------------------------------------------------------------
    @Operation(summary = "List all Tasks", description = "Returns all tasks across all jobs and all statuses.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = TaskResponseDto.class)))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content)
    })
    @GetMapping()
    public List<TaskResponseDto> getTasks() {
        return taskService.getTasks();
    }

    // -------------------------------------------------------------------------
    // GET /tasks/{id}
    // -------------------------------------------------------------------------
    @Operation(summary = "Get a Task by ID", description = "Returns the full Task record, including its attachments.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TaskResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token", content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content)
    })
    @GetMapping("/{id}")
    public TaskResponseDto getTaskById(
            @Parameter(description = "Surrogate ID of the Task", example = "101", required = true)
            @PathVariable Long id) {
        return taskService.getTask(id);
    }

    // -------------------------------------------------------------------------
    // GET /tasks/status/{status}
    // -------------------------------------------------------------------------
    @Operation(summary = "List Tasks by status", description = "Filters all tasks to only those matching the given lifecycle status.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Filtered list returned",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = TaskResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid status value", content = @Content),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content)
    })
    @GetMapping("/status/{status}")
    public List<TaskResponseDto> getTasksByStatus(
            @Parameter(description = "Task lifecycle status", example = "IN_PROGRESS",
                    schema = @Schema(allowableValues = {"CREATED", "IN_PROGRESS", "BLOCKED", "IN_REVIEW", "DONE", "CANCELLED"}),
                    required = true)
            @PathVariable TaskStatus status) {
        return taskService.getTasksByStatus(status);
    }

    // -------------------------------------------------------------------------
    // GET /tasks/priority/{priority}
    // -------------------------------------------------------------------------
    @Operation(summary = "List Tasks by priority", description = "Returns all tasks with the given priority level.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Filtered list returned",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = TaskResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid priority value", content = @Content),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content)
    })
    @GetMapping("/priority/{priority}")
    public List<TaskResponseDto> getTasksByPriority(
            @Parameter(description = "Priority level of the task", example = "HIGH",
                    schema = @Schema(allowableValues = {"CRITICAL", "HIGH", "MEDIUM", "LOW"}),
                    required = true)
            @PathVariable Priority priority) {
        return taskService.getTasksByPriority(priority);
    }

    // -------------------------------------------------------------------------
    // GET /tasks/deadline/{days}
    // -------------------------------------------------------------------------
    @Operation(
            summary = "List Tasks due within N days",
            description = "Returns tasks whose deadline falls within the next `days` calendar days from today."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks due within the window returned",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = TaskResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid days value (must be >= 0)", content = @Content),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content)
    })
    @GetMapping("/deadline/{days}")
    public List<TaskResponseDto> getTasksByDeadline(
            @Parameter(description = "Look-ahead window in calendar days (e.g. 7 = next 7 days)", example = "7", required = true)
            @PathVariable int days) {
        return taskService.getTasksByDeadline(days);
    }

    // -------------------------------------------------------------------------
    // POST /tasks/add
    // -------------------------------------------------------------------------
    @Operation(summary = "Create a new Task", description = "Creates a Task and associates it with the specified Job.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Task created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TaskResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token", content = @Content),
            @ApiResponse(responseCode = "404", description = "Parent Job not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content)
    })
    @PostMapping("/add")
    public ResponseEntity<TaskResponseDto> createTask(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Task creation payload", required = true,
                    content = @Content(schema = @Schema(implementation = TaskRequestDto.class)))
            @RequestBody TaskRequestDto taskRequestDto) {
        TaskResponseDto createdTask = taskService.createTask(taskRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    // -------------------------------------------------------------------------
    // PUT /tasks/{id}
    // -------------------------------------------------------------------------
    @Operation(summary = "Update an existing Task", description = "Performs a full replacement of the Task identified by the given ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task updated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TaskResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token", content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @Parameter(description = "Surrogate ID of the Task to update", example = "101", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated Task payload", required = true,
                    content = @Content(schema = @Schema(implementation = TaskRequestDto.class)))
            @RequestBody TaskRequestDto taskRequestDto) {
        TaskResponseDto updatedTask = taskService.updateTask(id, taskRequestDto);
        return ResponseEntity.ok(updatedTask);
    }

    // -------------------------------------------------------------------------
    // DELETE /tasks/{id}
    // -------------------------------------------------------------------------
    @Operation(summary = "Delete a Task", description = "Permanently removes the Task and all its file attachments.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Task deleted — no body returned", content = @Content),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token", content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "Surrogate ID of the Task to delete", example = "101", required = true)
            @PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
