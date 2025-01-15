package com.example.doesnotexist.desk_bookings_server.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("desks")
@Builder
@Data
public class Desk {
    @Id
    @Field("_id")
    private Integer id;
    @Field("deskGroupId")
    private Integer deskGroupId;
    @Field("name")
    private String name;
    @Field("x")
    private Integer x;
    @Field("y")
    private Integer y;
    @Field("vertical")
    private Boolean vertical;
    @Field("selectable")
    private Boolean selectable;
    @Field("adjustable")
    private Boolean adjustableDesk;
    @Field("rotation")
    private Integer rotation;
}