package pl.arturzaczek.demo.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
public class UserAddress implements Serializable {
    private static final long serialVersionUID = -8441949325468918622L;
    private String city;
    private String country;
    private String zipCode;
    private String street;
}
