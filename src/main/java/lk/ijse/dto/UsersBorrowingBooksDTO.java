package lk.ijse.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UsersBorrowingBooksDTO {

    private String transaction_id;
    private LocalDate borrow_date;
    private LocalDate due_date;
    private LocalDate return_date;
    private boolean is_return;
    private UserDTO userDTO;
    private BookDTO bookDTO;
}
