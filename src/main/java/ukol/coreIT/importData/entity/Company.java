package ukol.coreIT.importData.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
@Table(name = "companies")
public class Company {
    //  --Fields --
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="ico")
    private int ico;

    @Column(name="company_name")
    private String companyName;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="id_address")
    private Address address;

//    @OneToMany(mappedBy = "company",
//                cascade = CascadeType.ALL)
//    private List<Person> employe;

    //  -- Constructors --
    public Company() {}

    public Company(String ico, String companyName) throws Exception {
        if (ico.length() <= 8) {
            this.ico = Integer.parseInt(ico);
        } else {
            throw new Exception("ICO not valid");
        }
        this.companyName = companyName;
    }

    public boolean companyEquals(Company foundCompany) {
        return (foundCompany.getIco() == this.getIco() &&
            foundCompany.getCompanyName().equals(this.getCompanyName()));
    }
}
