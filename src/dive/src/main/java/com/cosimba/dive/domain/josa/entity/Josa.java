package com.cosimba.dive.domain.josa.entity;

import com.cosimba.dive.global.entity.TrashType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Josa {
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
    private Integer predictVal;

    @Column(name = "trash_type")
    private TrashType trashType;

    @Column(name = "josa_status")
    private JosaStatus josaStatus;

}
