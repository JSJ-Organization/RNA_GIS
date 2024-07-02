package com.jsj.backend.batch;

import com.jsj.backend.search.frcnRentInfo.FrcnRentInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * FrcnRentInfo 데이터를 데이터베이스에 배치로 삽입하는 ItemWriter 클래스.
 * 이 클래스는 Spring Batch에서 사용하는 ItemWriter 인터페이스를 구현합니다.
 */
@RequiredArgsConstructor
public class FrcnRentInfoWriter implements ItemWriter<FrcnRentInfo> {

    private final JdbcTemplate jdbcTemplate; // JdbcTemplate 객체
    private static final String SQL_INSERT;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO frcn_rent_info ");
        sb.append("(office_nm, office_phone_number, rdnmadr, lnmadr, geometry, trctor_hold_co, ");
        sb.append("cultvt_hold_co, manage_hold_co, harvest_hold_co, thresher_hold_co, ");
        sb.append("planter_hold_co, transplant_hold_co, rcepnt_hold_co, etc_rent_hold_co, ");
        sb.append("phone_number, institution_nm, instt_code, reference_date, batch_date) ");
        sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        SQL_INSERT = sb.toString();
    }

    /**
     * 주어진 청크의 FrcnRentInfo 데이터를 데이터베이스에 삽입합니다.
     *
     * @param chunk 청크의 FrcnRentInfo 데이터
     * @throws Exception 데이터베이스 삽입 중 발생할 수 있는 예외
     */
    @Override
    public void write(Chunk<? extends FrcnRentInfo> chunk) {
        List<? extends FrcnRentInfo> items = chunk.getItems();

        jdbcTemplate.batchUpdate(SQL_INSERT, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                FrcnRentInfo item = items.get(i);
                setPreparedStatement(ps, item);
            }

            @Override
            public int getBatchSize() {
                return items.size();
            }
        });
    }

    /**
     * 주어진 FrcnRentInfo 객체의 데이터를 PreparedStatement에 설정합니다.
     *
     * @param ps PreparedStatement 객체
     * @param item FrcnRentInfo 객체
     * @throws SQLException PreparedStatement 설정 중 발생할 수 있는 예외
     */
    private void setPreparedStatement(PreparedStatement ps, FrcnRentInfo item) throws SQLException {
        ps.setString(1, item.getOfficeNm());
        ps.setString(2, item.getOfficePhoneNumber());
        ps.setString(3, item.getRdnmadr());
        ps.setString(4, item.getLnmadr());
        ps.setObject(5, item.getGeometry(), Types.OTHER);
        ps.setString(6, item.getTrctorHoldCo());
        ps.setString(7, item.getCultvtHoldCo());
        ps.setString(8, item.getManageHoldCo());
        ps.setString(9, item.getHarvestHoldCo());
        ps.setString(10, item.getThresherHoldCo());
        ps.setString(11, item.getPlanterHoldCo());
        ps.setString(12, item.getTransplantHoldCo());
        ps.setString(13, item.getRcepntHoldCo());
        ps.setString(14, item.getEtcRentHoldCo());
        ps.setString(15, item.getPhoneNumber());
        ps.setString(16, item.getInstitutionNm());
        ps.setString(17, item.getInstt_code());
        ps.setString(18, item.getReferenceDate());
        ps.setString(19, item.getBatchDate());
    }
}
