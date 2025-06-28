package kfu.itis.arseniy.entity;

import javax.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "stage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stage_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "stage")
    private List<Order> orders;

    public Stage(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
