package lk.ijse.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

    private String user_email;
    private String user_name;
    private String user_password;
    private BranchDTO branchDTO;
}
