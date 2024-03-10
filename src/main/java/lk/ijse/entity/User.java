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
public class User {

    @Id
    private String userEmail;
    private String userName;
    private String userPassword;

    @ManyToOne      // User has at least one branch
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)       // User has many transactions
    private List<Users_Borrowing_Books> usersBorrowingBooks;

}
