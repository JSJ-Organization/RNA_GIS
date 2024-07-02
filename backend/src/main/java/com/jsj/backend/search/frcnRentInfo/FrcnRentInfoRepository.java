package com.jsj.backend.search.frcnRentInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrcnRentInfoRepository extends JpaRepository<FrcnRentInfo, Long> {

}
