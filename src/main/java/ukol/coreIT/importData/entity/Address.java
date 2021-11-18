package ukol.coreIT.importData.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
@Table(name = "address")
public class Address {

    //  --Fields --
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="addressee")
    private String addressee;

    @Column(name="street")
    private String street;

    @Column(name="building_number")
    private int buildingNumber;

    @Column(name="postal_code")
    private String postalCode;

    @Column(name="locality")
    private String locality;

    @Column(name="country_name")
    private String countryName;

    //  -- Constructor --
    public Address() {
    }

    public Address(String addressee, String street, String buildingNumber, String postalCode, String locality, String countryName) throws Exception {
        this.addressee = addressee;
        this.street = street;
        if (buildingNumber.length() < 20) {
            this.buildingNumber = Integer.parseInt(buildingNumber);
        } else {
            throw new Exception("building number not valid");
        }
        this.postalCode = postalCode;
        this.locality = locality;
        this.countryName = countryName;
    }

    public boolean addressEquals(Address foundAddress) {
        return  (foundAddress.getAddressee().equals(this.getAddressee()) &&
                foundAddress.getStreet().equals(this.getStreet()) &&
                foundAddress.getBuildingNumber() == this.getBuildingNumber() &&
                foundAddress.getLocality().equals(this.getLocality()) &&
                foundAddress.getPostalCode().equals(this.getPostalCode()) &&
                foundAddress.getCountryName().equals(this.getCountryName()));
    }
}
