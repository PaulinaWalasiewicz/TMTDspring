package com.tmtd.tmtdspring.Repository;

import com.tmtd.tmtdspring.Models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
