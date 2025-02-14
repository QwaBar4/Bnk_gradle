package QwaBar4.bank.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import QwaBar4.bank.Model.UserModel;
import QwaBar4.bank.Model.UserModelRepository;

@RestController
public class RegistrationController {
    
    @Autowired
    private UserModelRepository myAppUserRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping(value = "/req/signup", consumes = "application/json")
    public UserModel createUser(@RequestBody UserModel user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return myAppUserRepository.save(user);
    }
    
}
