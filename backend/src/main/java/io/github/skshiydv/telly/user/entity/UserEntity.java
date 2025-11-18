package io.github.skshiydv.telly.user.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.skshiydv.telly.room.entity.RoomEntity;
import io.github.skshiydv.telly.tasks.entity.TaskEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_entity")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_entity_id")
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "image_url")
    private String imageUrl;
    @OneToMany
    private List<TaskEntity> tasks = new ArrayList<>();
    @ManyToMany(mappedBy = "users")
    @JsonManagedReference
    private List<RoomEntity> rooms = new ArrayList<>();


}
