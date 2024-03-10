package lk.ijse.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookTm {

    private String book_id;
    private String title;
    private String author;
    private String genre;
    private String branchAddress;
    private String availability_status;
}
