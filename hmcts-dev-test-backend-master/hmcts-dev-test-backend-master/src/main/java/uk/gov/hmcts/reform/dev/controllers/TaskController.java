package uk.gov.hmcts.reform.dev.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.hmcts.reform.dev.Services.TaskService;
import uk.gov.hmcts.reform.dev.models.Task;

import java.util.List;

/**
 * @Tag – Groups endpoints logically in Swagger UI. (purely documentation)
 * @Operation – Describes each endpoint, including summary, description, and response codes. (purely documentation)
 * @ApiResponse – Documents HTTP responses (200, 201, 400, 500, etc.).
 * @Schema – Tells Swagger to use your Task class for request/response structure.
 * @RequestBody tells Spring to deserialize the HTTP request body into a Task object.
 * @Valid tells Spring to automatically validate the Task object using annotations
 * like @NotBlank, @NotNull, @Size, etc. before entering the method.
 * If validation fails, a MethodArgumentNotValidException is thrown.
 */

/**
 * REST controller for managing tasks.
 * -
 * Responsibilities:
 * - Exposes HTTP endpoints to create new tasks.
 * - Delegates business logic to TaskService.
 * - Handles request validation and returns appropriate responses.
 */

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "tasks", description = "Endpoints to manage tasks") //TAG is purely documentation purpose—it doesn’t affect your application logic at all.
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Endpoint to create a new task.
     *
     * @param task the task payload
     * @return the saved task with generated ID and createdAt
     */
    @Operation(
        summary = "Create a new task",
        description = "Creates a new task with title, description, status, and due date",
        responses = {
            @ApiResponse(responseCode = "201", description = "Task successfully created",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed")
        }
    )
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task savedTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
        //return ResponseEntity.ok(savedTask);
    }


    @Operation(  //Describes each endpoint, including summary, description, and response codes.
        summary = "Retrieve all tasks",
        description = "Returns a list of all tasks in the system",
        responses = {
            @ApiResponse(responseCode = "200", description = "Tasks successfully retrieved",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }
}

