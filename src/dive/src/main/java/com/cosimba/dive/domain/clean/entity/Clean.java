package com.cosimba.dive.domain.clean.entity;

import com.cosimba.dive.global.entity.TrashType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Document(indexName = "josa_trash")
public class Clean {
    @Id
    private String id;

    @Field(name = "clean_name")
    private String cleanName;

    @Field(name = "serial_number")
    private String serialNumber;

    @Field(name = "clean_date")
    private LocalDateTime cleanDate;

    @Field(name = "coast_name")
    private String coastName;

    private double lat;

    private double lng;

    @Field(name = "coast_length")
    private Integer coastLength;

    @Field(name = "collect_bag")
    private Integer collectBag;

    @Field(name = "collect_val")
    private Integer collectVal;

    @Field(name = "trash_type")
    private TrashType trashType;

    @Field(name = "clean_status")
    private CleanStatus cleanStatus;
}
