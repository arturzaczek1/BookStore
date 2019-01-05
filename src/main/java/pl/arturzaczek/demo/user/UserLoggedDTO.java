package pl.arturzaczek.demo.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoggedDTO {
    private String login;

    public UserLoggedDTO(String login) {
        this.login = login;
    }
}
