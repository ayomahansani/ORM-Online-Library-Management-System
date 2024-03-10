package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
public class Book {

    @Id
    private String bookId;
    private String title;
    private String author;
    private String genre;
    private boolean availabilityStatus;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;      // Book has at least one branch

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)       // Book has many transactions
    private List<Users_Borrowing_Books> usersBorrowingBooks;

}
