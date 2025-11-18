package k23cnt2_duongchidu_day08.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class dcdAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String code;
    private String name;
    private String description;
    private String imgUrl;
    private String email;
    private String phone;
    private String address;
    private boolean isActive;

    // Tạo mối quan hệ với bảng dcdBook
    @ManyToMany(mappedBy = "authors")
    private List<dcdBook> books = new ArrayList<>();
}
