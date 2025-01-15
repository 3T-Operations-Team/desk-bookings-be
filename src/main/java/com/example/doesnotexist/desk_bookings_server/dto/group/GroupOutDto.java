package com.example.doesnotexist.desk_bookings_server.dto.group;

import com.example.doesnotexist.desk_bookings_server.model.Desk;
import com.example.doesnotexist.desk_bookings_server.model.Group;

import java.util.List;

public record GroupOutDto(Integer id,
                          Integer x,
                          Integer y,
                          List<DeskOutDto> desks) {
    public static GroupOutDto fromGroupAndDeskList(Group group, List<Desk> desks) {
        return new GroupOutDto(group.getId(), group.getX(), group.getY(),
                desks.stream().map(DeskOutDto::fromDesk).toList());
    }
}
