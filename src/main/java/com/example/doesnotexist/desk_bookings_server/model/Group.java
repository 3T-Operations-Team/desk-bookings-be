package com.example.doesnotexist.desk_bookings_server.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("desk-groups")
@Data
@Builder
public class Group {
    @Id
    @Field("_id")
    private Integer id;

    @Field("x")
    private Integer x;

    @Field("y")
    private Integer y;
}
