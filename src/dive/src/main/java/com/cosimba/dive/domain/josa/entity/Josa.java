package com.cosimba.dive.domain.josa.entity;

import com.cosimba.dive.global.entity.TrashType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Document(indexName = "josa_trash")
public class Josa {
    @Id
    private String id;

    @Field(name = "josa_name")
    private String josaName;

    @Field(name = "serial_number")
    private String serialNumber;

    @Field(name = "josa_date")
    private LocalDateTime josaDate;

    @Field(name = "coast_name")
    private String coastName;

    private double lat;

    private double lng;

    @Field(name = "coast_length")
    private Integer coastLength;

    @Field(name = "predict_val")
    private Integer predictVal;

    @Field(name = "image_url")
    private List<String> imageUrl;

    @Field(name = "trash_type")
    private TrashType trashType;

    @Field(name = "josa_status")
    private JosaStatus josaStatus;

}
