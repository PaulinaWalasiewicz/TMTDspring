package com.tmtd.tmtdspring.Repository;

import com.tmtd.tmtdspring.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {
}
