package ukol.coreIT.importData.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Getter
@Setter
@ToString
@Table(name = "people")
public class Person {

    //  --Fields --
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_company")
    private Company company;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    //  -- Constructor --
    public Person() {
    }

    public Person(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(Company company, String email, String firstName, String lastName) {
        this.company = company;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean personEquals(Person foundPerson) {
        return  (foundPerson.getEmail().equals(this.getEmail()) &&
                foundPerson.getFirstName().equals(this.getFirstName()) &&
                foundPerson.getLastName().equals(this.getLastName()));
    }
}





