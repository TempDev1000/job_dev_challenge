package uk.gov.hmcts.reform.dev.Services;

import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;

import java.util.List;


/**
 * Service layer for Task operations.
 *
 * Responsibilities:
 * - Contains business logic for task creation.
 * - Interacts with the repository to persist tasks.
 * - Keeps the controller layer clean and simple.
 */

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Create a new task and persist it to the database.
     *
     * @param task the task to save
     * @return the saved task with ID and createdAt set
     */
    public Task createTask(Task task) {

        validateTask(task);

        return taskRepository.save(task);
    }

    // Separate validation method
    private void validateTask(Task task) {
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new RuntimeException("Title is required");
        }

        if (task.getDescription() == null || task.getDescription().isBlank()) {
            throw new RuntimeException("Description is required");
        }

        if (task.getStatus() == null) {
            throw new RuntimeException("Status is required");
        }

        if (task.getDueAt() == null) {
            throw new RuntimeException("Due date is required");
        }

        // You can add more business rules here if needed
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
