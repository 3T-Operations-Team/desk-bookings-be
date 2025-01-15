package com.example.doesnotexist.desk_bookings_server.model;

import org.bson.types.ObjectId;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Builder
@With
@Data
@Document("bookings")
@CompoundIndexes({
        @CompoundIndex(name = "only_one_per_email_and_day",
                def = "{'bookingDate': 1, 'email': 1}", unique = true ),
        @CompoundIndex(name = "only_one_per_desk_and_day",
                def = "{'deskNumber': 1, 'bookingDate': 1}", unique = true )
})
public class Booking {
    @Id
    @Field("_id")
    private ObjectId id;
    @Field("bookingDate")
    private LocalDate bookingDate;
    @Field("email")
    private String email;
    @Field("deskNumber")
    private Integer deskNumber;
    @Field("createdAt")
    @Builder.Default
    private Instant createdAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
}
