package lk.ijse.tm;


import javafx.scene.control.Button;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyHistoryTm {

    private String transactionId;
    private String bookTitle;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String returnOrNot;
    private Button returnBtn;
}
