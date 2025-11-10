package k23cnt2_duongchidu_day04.service;

import java.util.ArrayList;
import java.util.List;
import k23cnt2_duongchidu_day04.dto.DcdUsersDTO;
import k23cnt2_duongchidu_day04.entity.DcdUsers;
import org.springframework.stereotype.Service;

@Service
public class DcdUsersService {

    private final List<DcdUsers> userList = new ArrayList<>();

    public List<DcdUsers> findAll() {
        return userList;
    }

    public void create(DcdUsersDTO dto) {
        DcdUsers newUser = new DcdUsers(
                (long) (userList.size() + 1),
                dto.getUsername(),
                dto.getPassword(),
                dto.getFullName(),
                dto.getBirthDay(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getAge(),
                dto.isStatus()
        );
        userList.add(newUser);
    }
}
