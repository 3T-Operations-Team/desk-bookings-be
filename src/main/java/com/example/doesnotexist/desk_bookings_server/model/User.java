package com.example.doesnotexist.desk_bookings_server.model;

import org.bson.types.ObjectId;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "users")
@Getter
@Builder
public class User implements UserDetails {

    private @Id @Field("_id") ObjectId id;
    private @Field("username") String username;
    private @Field("password") String password;
    @Builder.Default
    private @Field("authorities") Set<GrantedAuthority> authorities = new HashSet<>();
}