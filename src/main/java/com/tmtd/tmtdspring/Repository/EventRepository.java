package com.tmtd.tmtdspring.Repository;

import com.tmtd.tmtdspring.Models.Event;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long> {
    List<Event> findByUserId(Long user_id);

    @Transactional
    void deleteByUserId(long user_id);
}
