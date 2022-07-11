package lsek.learning.jpablog.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String city;
    private String street;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this.city.equals(((Address) obj).city) && this.street.equals(((Address) obj).street)) {
            return true;
        }
        return false;
    }
}
