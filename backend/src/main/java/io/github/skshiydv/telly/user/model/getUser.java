package io.github.skshiydv.telly.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@Setter
public class getUser {
    private String userName;
private List<Long> roomIds;
}
