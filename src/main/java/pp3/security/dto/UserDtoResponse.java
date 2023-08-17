package pp3.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDtoResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String username;
}
