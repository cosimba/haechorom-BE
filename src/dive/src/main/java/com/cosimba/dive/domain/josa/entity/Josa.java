package com.cosimba.dive.domain.josa.entity;

import com.cosimba.dive.global.entity.BaseEntity;
import com.cosimba.dive.global.entity.TrashType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
public class Josa extends BaseEntity { // Josa 엔티티는 조사자 모드에서 사용할 데이터 테이블을 표현
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "josa_name")
    private String josaName;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "josa_date")
    private LocalDateTime josaDate;

    @Column(name = "coast_name")
    private String coastName;

    private double lat;

    private double lng;

    @Column(name = "coast_length")
    private Integer coastLength;

    @Column(name = "collect_bag")
    private Integer collectBag;

    @Column(name = "trash_type")
    private TrashType trashType;

    @Column(name = "josa_status")
    private JosaStatus josaStatus;

    // 조사 정보 생성
    public static Josa create(String josaName, String serialNumber, LocalDateTime josaDate,
                              String coastName, double lat, double lng, Integer coastLength,
                              Integer collectBag, TrashType trashType, JosaStatus josaStatus) {
        return Josa.builder()
                .josaName(josaName)
                .serialNumber(serialNumber)
                .josaDate(josaDate)
                .coastName(coastName)
                .lat(lat)
                .lng(lng)
                .coastLength(coastLength)
                .collectBag(collectBag)
                .trashType(trashType)
                .josaStatus(josaStatus)
                .build();
    }

    // 조사 정보 수정
    public void update(String josaName, String serialNumber, LocalDateTime josaDate,
                       String coastName, double lat, double lng, Integer coastLength,
                       Integer collectBag, TrashType trashType, JosaStatus josaStatus) {
        this.josaName = josaName;
        this.serialNumber = serialNumber;
        this.josaDate = josaDate;
        this.coastName = coastName;
        this.lat = lat;
        this.lng = lng;
        this.coastLength = coastLength;
        this.collectBag = collectBag;
        this.trashType = trashType;
        this.josaStatus = josaStatus;
    }
}
