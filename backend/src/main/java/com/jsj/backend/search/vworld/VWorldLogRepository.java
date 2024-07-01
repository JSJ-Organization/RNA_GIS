package com.jsj.backend.search.vworld;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VWorldLogRepository extends JpaRepository<VWorldLog, Long> {
}
