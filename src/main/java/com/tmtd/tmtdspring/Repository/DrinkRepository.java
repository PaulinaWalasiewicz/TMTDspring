package com.tmtd.tmtdspring.Repository;

import com.tmtd.tmtdspring.Models.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrinkRepository extends JpaRepository<Drink,Long> {
    List<Drink> findByUserId(Long user_id);


}
