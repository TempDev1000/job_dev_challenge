package uk.gov.hmcts.reform.dev.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.errorprone.annotations.InlineMeValidationDisabled;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

//    Title
//    Description (optional field)
//    Status
//    Due date/time

@SuppressWarnings("Lombok")
@Entity
@Table(name = "tasks", schema = "public") // <-- make sure this matches your PostgreSQL table name
public class Task {

    @Id //mark field as primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //tells the database to auto-generate values for this primary key.
    private Long id;

    @NotBlank(message = "Title is required") //field can not be blank
    @Size(max = 200) // Restrict length of field to 200 characters
    private String title;

    @Size(max = 2000) // Restrict length of field to 2000 characters
    private String description;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private TaskStatus status; //create enum for this

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name ="due_date")
    private LocalDateTime dueDate;;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    //@PrePersist ensures createdAt is set automatically.
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    //empty constructor
    public Task() {}

    public Task(String title, String description, TaskStatus status, LocalDateTime dueDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
    }


    //Getters & Setters
    //Note: could use Lombok but want more control of getters/setters

    /** Getter for ID (no setter to prevent accidental changes). */
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    /** Getter and Setter for dueAt */
    public LocalDateTime getDueAt() {
        return dueDate;
    }

    public void setDueAt(LocalDateTime dueAt) {
        this.dueDate = dueAt;
    }

    /** Getter for createdAt (no setter; set automatically via @PrePersist) */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
