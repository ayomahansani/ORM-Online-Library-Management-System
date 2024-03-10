package lk.ijse.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
public class Branch {

    @Id
    private String branchId;
    private String branchAddress;
    private String branchTelephone;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<User> branchUsers;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Book> branchBooks;
}
