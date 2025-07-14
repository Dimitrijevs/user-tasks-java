package testjwt.demo.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private int id;

    private String title;

    private String description;

    private Status status;

    private int user_id;

    private String user_name;
}
