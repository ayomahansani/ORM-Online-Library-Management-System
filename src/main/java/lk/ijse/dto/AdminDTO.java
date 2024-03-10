package lk.ijse.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AdminDTO {

    private String admin_email;
    private String admin_name;
    private String admin_password;
}
