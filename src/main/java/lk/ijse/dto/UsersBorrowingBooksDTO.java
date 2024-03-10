package lk.ijse.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UsersBorrowingBooksDTO {

    private String transaction_id;
    private Date borrow_date;
    private Date return_date;
    private boolean is_return;
    private UserDTO userDTO;
    private BookDTO bookDTO;
}
