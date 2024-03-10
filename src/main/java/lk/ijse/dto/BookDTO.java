package lk.ijse.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookDTO {

    private String book_id;
    private String book_title;
    private String book_author;
    private String book_genre;
    private boolean availability_status;
    private BranchDTO branchDTO;
}
