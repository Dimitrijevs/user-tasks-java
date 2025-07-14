package testjwt.demo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// from lombok, generated getters and setters to fields
@Data

// from lombok, adds .builder() -> ... -> .build() method to create an object with fields
@Builder

// from lombok, adds constructor with no args
@NoArgsConstructor

// from lombok, adds counstructor with all of the args
@AllArgsConstructor
public class UserDTO {

    private int id;

    private String first_name;

    private String last_name;

    private String email; 

    private Role role;
}
