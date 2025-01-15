package com.example.doesnotexist.desk_bookings_server.controller;

import com.example.doesnotexist.desk_bookings_server.dto.group.GroupOutDto;
import com.example.doesnotexist.desk_bookings_server.model.Desk;
import com.example.doesnotexist.desk_bookings_server.model.Group;
import com.example.doesnotexist.desk_bookings_server.services.GroupDeskService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/groups")
@Validated
public class GroupDeskController {
    private final GroupDeskService service;

    public GroupDeskController(GroupDeskService service) {
        this.service = service;
    }
    @GetMapping("")
    public List<GroupOutDto> getList() {
        return toGroupList(service.getGroupsWithDesks());
    }

    private static List<GroupOutDto> toGroupList(Map<Group, List<Desk>> map) {
        return map.entrySet().stream().sorted(Comparator.comparingInt(entry -> entry.getKey().getId()))
                .map(entry ->
            GroupOutDto.fromGroupAndDeskList(entry.getKey(), entry.getValue())
        ).toList();
    }
}
