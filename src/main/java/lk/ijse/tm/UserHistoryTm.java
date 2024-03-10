package lk.ijse.tm;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserHistoryTm {

    private String bookTitle;
    private Date borrowDate;
    private Date returnDate;
    private String returnOrNot;
}
