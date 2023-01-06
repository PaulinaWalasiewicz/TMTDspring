package com.tmtd.tmtdspring.Repository;

import com.tmtd.tmtdspring.Models.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink,Long> {
}
