package pl.sda.DTO;

import lombok.*;

@ToString
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UserDto {
    private Integer id;
    private String username;
    private String roles;
    private String email;
}
