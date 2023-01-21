package com.tmtd.tmtdspring.Repository;

import com.tmtd.tmtdspring.Models.Description;
import com.tmtd.tmtdspring.Models.DrinkType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkTypeRepository extends JpaRepository<DrinkType,Long> {
}
