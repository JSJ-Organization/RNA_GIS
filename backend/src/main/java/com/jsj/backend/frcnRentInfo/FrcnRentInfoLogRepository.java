package com.jsj.backend.frcnRentInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrcnRentInfoLogRepository extends JpaRepository<FrcnRentInfoLog, Long> {
}