package com.tmtd.tmtdspring.Repository;
import java.util.List;

import com.tmtd.tmtdspring.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends  JpaRepository<User,Long>{
    List<User> findByEmail(String email);
}
