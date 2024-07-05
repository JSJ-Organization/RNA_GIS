package com.jsj.backend.frcnRentInfo.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FrcnRentInfoRepository extends JpaRepository<FrcnRentInfo, Long> {

    List<FrcnRentInfo> findAllByBatchDate(String batchDate);
    boolean existsByBatchDate(String batchDate);
    @Modifying
    @Query("DELETE FROM FrcnRentInfo f WHERE f.batchDate = :date")
    void deleteAllByBatchDate(@Param("date") String date);

    @Query("SELECT COUNT(f) > 0 FROM FrcnRentInfo f WHERE f.batchDate < :date")
    boolean existsPastDataByBatchDate(@Param("date") String date);

    @Modifying
    @Query("DELETE FROM FrcnRentInfo f WHERE f.batchDate < :date")
    void deletePastDataByDate(@Param("date") String date);

    List<FrcnRentInfo> findAllByBatchDateAndRdnmadr(String batchDate, String rdnmadr);

    List<FrcnRentInfo> findAllByBatchDateAndLnmadr(String batchDate, String lnmadr);

    List<FrcnRentInfo> findAllByBatchDateAndOfficeNm(String batchDate, String officeNm);
    List<FrcnRentInfo> findAllByBatchDateAndOfficeNmContaining(String batchDate, String officeNm);

    List<FrcnRentInfo> findAllByBatchDateAndInstitutionNm(String batchDate, String institutionNm);
    List<FrcnRentInfo> findAllByBatchDateAndInstitutionNmContaining(String batchDate, String institutionNm);

    List<FrcnRentInfo> findAllByBatchDateAndRdnmadrContaining(String batchDate, String rdnmadr);

    List<FrcnRentInfo> findAllByBatchDateAndLnmadrContaining(String batchDate, String lnmadr);

    List<FrcnRentInfo> findAllByBatchDateAndRdnmadrContainingOrLnmadrContaining(String batchDate, String rdnmadr, String lnmadr);

    @Query(value = "SELECT * FROM frcn_rent_info " +
            "WHERE ST_DWithin(" +
            "ST_Transform(geometry, 32633), " +
            "ST_Transform(ST_SetSRID(ST_MakePoint(:x, :y), 4326), 32633), " +
            ":distance)" +
            "AND batch_date = :todayDateString",
            nativeQuery = true)
    List<FrcnRentInfo> findAllByLocationWithin(@Param("todayDateString") String todayDateString, @Param("x") double x, @Param("y") double y, @Param("distance") Integer distance);
}
