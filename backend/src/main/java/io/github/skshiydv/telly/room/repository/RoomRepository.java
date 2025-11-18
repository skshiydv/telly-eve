package io.github.skshiydv.telly.room.repository;

import io.github.skshiydv.telly.room.entity.RoomEntity;
import io.github.skshiydv.telly.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity,Long> {
    RoomEntity findByRoomName(String roomName);

    List<RoomEntity> findByUsers(UserEntity currUser);
}
