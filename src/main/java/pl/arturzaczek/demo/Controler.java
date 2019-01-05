package pl.arturzaczek.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.arturzaczek.demo.user.UserDAO;

@Controller
public class Controler {
    @Autowired
    private UserDAO userDAO;

}
