package com.tmtd.tmtdspring.Repository;

import com.tmtd.tmtdspring.Models.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByUserId(Long user_id);

    @Transactional
    void deleteByUserId(long user_id);
}
