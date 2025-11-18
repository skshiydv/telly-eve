package io.github.skshiydv.telly.tasks.model;

import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class CreateTaskRq {
    @NonNull
    private String task;
    @NonNull
    private String assignedTo;
    @NonNull
    private Date dueDate;
}
