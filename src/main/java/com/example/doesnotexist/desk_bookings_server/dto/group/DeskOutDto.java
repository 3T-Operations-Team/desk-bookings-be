package com.example.doesnotexist.desk_bookings_server.dto.group;

import com.example.doesnotexist.desk_bookings_server.model.Desk;

public record DeskOutDto(
        Integer id,
        String name,
        Integer x,
        Integer y,
        Boolean vertical,
        Boolean selectable,
        Boolean adjustableDesk,
        Integer rotation) {

    static DeskOutDto fromDesk(Desk desk) {
        return new DeskOutDto(desk.getId(), desk.getName(), desk.getX(), desk.getY(), desk.getVertical(), desk.getSelectable(), desk.getAdjustableDesk(), desk.getRotation());
    }
}
