package com.tmtd.tmtdspring.Repository;

import com.tmtd.tmtdspring.Models.Description;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Description,Long> {
}
