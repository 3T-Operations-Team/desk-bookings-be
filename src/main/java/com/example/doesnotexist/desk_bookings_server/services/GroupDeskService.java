package com.example.doesnotexist.desk_bookings_server.services;

import com.example.doesnotexist.desk_bookings_server.model.Desk;
import com.example.doesnotexist.desk_bookings_server.model.Group;
import com.example.doesnotexist.desk_bookings_server.repostory.DeskRepository;
import com.example.doesnotexist.desk_bookings_server.repostory.GroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class GroupDeskService {

    private final DeskRepository deskRepository;
    private final GroupRepository groupRepository;

    public GroupDeskService(DeskRepository deskRepository,
                            GroupRepository groupRepository) {
        this.deskRepository = deskRepository;
        this.groupRepository = groupRepository;
    }

    @PostConstruct
    public void init() {
        setUpInitialData();
    }

    public Map<Group, List<Desk>> getGroupsWithDesks() {
        List<Desk> desks = deskRepository.findAll();
        List<Group> groups = groupRepository.findAll();
        return groups.stream().collect(Collectors.toMap(Function.identity(),
                g -> desks.stream()
                        .filter(d -> d.getDeskGroupId().equals(g.getId()))
                        .toList()));
    }

    public void setUpInitialData() {
        if (getGroupsWithDesks().isEmpty()) {
            groupRepository.saveAll(USUAL_GROUPS);
            deskRepository.saveAll(USUAL_DESKS);
        }
    }

    private record DeskValues(
            int id,
            String name,
            int x,
            int y,
            boolean selectable,
            boolean vertical,
            boolean adjustableDesk,
            int deskGroup,
            int rotation
    ) {
        Desk toModel() {
            return Desk.builder().id(id).name(name)
                    .x(x)
                    .y(y)
                    .selectable(selectable)
                    .vertical(vertical)
                    .adjustableDesk(adjustableDesk)
                    .deskGroupId(deskGroup)
                    .rotation(rotation)
                    .build();
        }
    }

    private static final List<Group> USUAL_GROUPS = List.of(
            Group.builder().id(1).x(700).y(470).build(),
            Group.builder().id(2).x(450).y(470).build(),
            Group.builder().id(3).x(30).y(600).build(),
            Group.builder().id(4).x(30).y(350).build(),
            Group.builder().id(5).x(30).y(100).build()
    );

    private static final List<Desk> USUAL_DESKS = Stream.of(
            new DeskValues(1,
                    "Office manager 1",
                    1,
                    2,
                    false,
                    true,
                    false,
                    1,
                    90
            )
            ,
            new DeskValues(2,
                    "Flex 2",
                    1,
                    1,
                    true,
                    true,
                    false,
                    1,
                    90
            ),
            new DeskValues(3,
                    "Flex 3",
                    1,
                    0,
                    true,
                    true,
                    false,
                    1,
                    90
            ),
            new DeskValues(4,
                    "Flex 4",
                    0,
                    2,
                    true,
                    true,
                    false,
                    1,
                    270
            ),
            new DeskValues(5,
                    "Flex 5",
                    0,
                    1,
                    true,
                    true,
                    false,
                    1,
                    270
            ),
            new DeskValues(6,
                    "Flex 6",
                    0,
                    0,
                    true,
                    true,
                    true,
                    1,
                    270
            ),
            new DeskValues(7,
                    "Flex 7",
                    1,
                    2,
                    true,
                    true,
                    false,
                    2,
                    90
            ),
            new DeskValues(8,
                    "Flex 8",
                    1,
                    1,
                    true,
                    true,
                    false,
                    2,
                    90
            ),
            new DeskValues(9,
                    "Flex 9",
                    1,
                    0,
                    true,
                    true,
                    false,
                    2,
                    90
            ),
            new DeskValues(10,
                    "Flex 10",
                    0,
                    2,
                    true,
                    true,
                    false,
                    2,
                    270
            ),
            new DeskValues(11,
                    "Flex 11",
                    0,
                    1,
                    true,
                    true,
                    true,
                    2,
                    270
            ),
            new DeskValues(12,
                    "Flex 12",
                    0,
                    0,
                    true,
                    true,
                    false,
                    2,
                    270
            ),
            new DeskValues(13,
                    "Flex 13",
                    0,
                    1,
                    true,
                    false,
                    false,
                    3,
                    180
            ),
            new DeskValues(14,
                    "Flex 14",
                    1,
                    1,
                    true,
                    false,
                    false,
                    3,
                    180
            ),
            new DeskValues(15,
                    "Flex 15",
                    2,
                    1,
                    true,
                    false,
                    true,
                    3,
                    180
            ),
            new DeskValues(16,
                    "Flex 16",
                    0,
                    0,
                    true,
                    false,
                    false,
                    3,
                    0
            ),
            new DeskValues(17,
                    "Flex 17",
                    1,
                    0,
                    true,
                    false,
                    false,
                    3,
                    0
            ),
            new DeskValues(18,
                    "Flex 18",
                    2,
                    0,
                    true,
                    false,
                    false,
                    3,
                    0
            ),
            new DeskValues(19,
                    "Flex 19",
                    0,
                    1,
                    true,
                    false,
                    false,
                    4,
                    180
            ),
            new DeskValues(20,
                    "Flex 20",
                    1,
                    1,
                    true,
                    false,
                    false,
                    4,
                    180
            ),
            new DeskValues(21,
                    "Flex 21",
                    2,
                    1,
                    true,
                    false,
                    false,
                    4,
                    180
            ),
            new DeskValues(22,
                    "Flex 22",
                    0,
                    0,
                    true,
                    false,
                    false,
                    4,
                    0
            ),
            new DeskValues(23,
                    "Flex 23",
                    1,
                    0,
                    true,
                    false,
                    false,
                    4,
                    0
            ),
            new DeskValues(24,
                    "Flex 24",
                    2,
                    0,
                    true,
                    false,
                    true,
                    4,
                    0
            ),
            new DeskValues(25,
                    "Flex 25",
                    0,
                    1,
                    true,
                    false,
                    false,
                    5,
                    180
            ),
            new DeskValues(26,
                    "Flex 26",
                    1,
                    1,
                    true,
                    false,
                    false,
                    5,
                    180
            ),
            new DeskValues(27,
                    "Flex 27",
                    2,
                    1,
                    true,
                    false,
                    true,
                    5,
                    180
            ),
            new DeskValues(28,
                    "Flex 28",
                    0,
                    0,
                    true,
                    false,
                    true,
                    5,
                    0
            ),
            new DeskValues(29,
                    "Flex 29",
                    1,
                    0,
                    true,
                    false,
                    false,
                    5,
                    0
            ),
            new DeskValues(30,
                    "Flex 30",
                    2,
                    0,
                    true,
                    false,
                    false,
                    5,
                    0
            )
    ).map(DeskValues::toModel).toList();
}
