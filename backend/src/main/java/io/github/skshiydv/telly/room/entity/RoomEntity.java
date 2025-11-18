package io.github.skshiydv.telly.room.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.github.skshiydv.telly.message.entity.MessageEntity;
import io.github.skshiydv.telly.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "room_name")
    @NonNull
    private String roomName;
    @Column(name = "room_type")
    @NonNull
   private String roomType;
    @Column(name = "room_description")
    @NonNull
    private String roomDescription;
    @OneToMany
    private List<MessageEntity> messages = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "room_users",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_entity_id")
    )
    @JsonBackReference
    private List<UserEntity> users = new ArrayList<>();

}
