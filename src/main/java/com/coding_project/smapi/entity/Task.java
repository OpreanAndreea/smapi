package com.coding_project.smapi.entity;

import com.coding_project.smapi.enums.Priority;
import com.coding_project.smapi.enums.TaskStatus;
import com.coding_project.smapi.enums.TaskType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime deadline;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Override
    public String toString(){
        return
                "Task: " + '\n'
                        + " - id: " + id + '\n'
                        + " - name: " + name + '\n'
                        + " - description: " + description + '\n'
                        + " - type: " + type + '\n'
                        + " - priority: " + priority + '\n'
                        + " - status: " + status + '\n'
                        + " - createdAt: " + createdAt + '\n'
                        + " - deadline: " + deadline + '\n'
                        + " - jobId: " + job;
    }
}
