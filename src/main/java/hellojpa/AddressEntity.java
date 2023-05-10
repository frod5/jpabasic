package hellojpa;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS1")
public class AddressEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Address1 address1;

    public AddressEntity() {
    }

    public AddressEntity(String city, String street, String zipcode) {
        this.address1 = new Address1(city,street,zipcode);
    }

    public AddressEntity(Address1 address1) {
        this.address1 = address1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address1 getAddress() {
        return address1;
    }

    public void setAddress(Address1 address1) {
        this.address1 = address1;
    }
}
