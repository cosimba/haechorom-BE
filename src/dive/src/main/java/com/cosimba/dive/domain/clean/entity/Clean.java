package com.cosimba.dive.domain.clean.entity;

import com.cosimba.dive.global.entity.BaseEntity;
import com.cosimba.dive.global.entity.TrashType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
public class Clean extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clean_name")
    private String cleanName;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "clean_date")
    private LocalDateTime cleanDate;

    @Column(name = "coast_name")
    private String coastName;

    private double lat;

    private double lng;

    @Column(name = "coast_length")
    private Integer coastLength;

    @Column(name = "collect_bag")
    private Integer collectBag;

    @Column(name = "collect_val")
    private Integer collectVal;

    @Column(name = "trash_type")
    private TrashType trashType;

    @Column(name = "clean_status")
    private CleanStatus cleanStatus;

    public static Clean create(String cleanName, String serialNumber, LocalDateTime cleanDate,
                               String coastName, double lat, double lng, Integer coastLength,
                               Integer collectBag, Integer collectVal, TrashType trashType,
                               CleanStatus cleanStatus) {
        return Clean.builder()
                .cleanName(cleanName)
                .serialNumber(serialNumber)
                .cleanDate(cleanDate)
                .coastName(coastName)
                .lat(lat)
                .lng(lng)
                .coastLength(coastLength)
                .collectBag(collectBag)
                .collectVal(collectVal)
                .trashType(trashType)
                .cleanStatus(cleanStatus)
                .build();
    }

    public void update(String cleanName, String serialNumber, LocalDateTime cleanDate, String coastName, double lat, double lng, Integer coastLength, Integer collectBag, Integer collectVal, TrashType trashType, CleanStatus cleanStatus) {
        this.cleanName = cleanName;
        this.serialNumber = serialNumber;
        this.cleanDate = cleanDate;
        this.coastName = coastName;
        this.lat = lat;
        this.lng = lng;
        this.coastLength = coastLength;
        this.collectBag = collectBag;
        this.collectVal = collectVal;
        this.trashType = trashType;
        this.cleanStatus = cleanStatus;
    }
}
