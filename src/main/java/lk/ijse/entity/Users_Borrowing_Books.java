package lk.ijse.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
public class Users_Borrowing_Books {    // Transaction table (associated table between User & Book )

    @Id
    private String transactionId;
    private Date borrowDate;
    private Date returnDate;
    private boolean isReturn;

    @ManyToOne  // Transaction has at least one user
    @JoinColumn(name = "user_email")
    private User user;

    @ManyToOne  // Transaction has at least one book
    @JoinColumn(name = "book_id")
    private Book book;
}
