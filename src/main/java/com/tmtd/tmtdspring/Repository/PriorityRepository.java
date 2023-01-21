package com.tmtd.tmtdspring.Repository;

import com.tmtd.tmtdspring.Models.Description;
import com.tmtd.tmtdspring.Models.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriorityRepository extends JpaRepository<Priority,Long> {
}
