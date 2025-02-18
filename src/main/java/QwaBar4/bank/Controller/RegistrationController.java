package QwaBar4.bank.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import QwaBar4.bank.Model.UserModel;
import QwaBar4.bank.Model.AccountModel;
import QwaBar4.bank.Model.UserModelRepository;
import QwaBar4.bank.Model.AccountModelRepository; // Add this import

@RestController
public class RegistrationController {

    @Autowired
    private UserModelRepository userModelRepository;

    @Autowired
    private AccountModelRepository accountModelRepository; // Add this repository

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/req/signup", consumes = "application/json")
    public ResponseEntity<?> createUser(@RequestBody UserModel user) {
        // Check if username already exists
        if (userModelRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        // Check if email already exists
        if (userModelRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Create a new account for the user
        AccountModel account = new AccountModel();
        account.setAccountNumber(generateAccountNumber()); // Generate a unique account number
        account.setBalance(0.0); // Set initial balance
        account.setUser(user); // Link the account to the user

        // Link the account to the user
        user.setAccount(account);

        // Save the user (and account due to CascadeType.ALL)
        UserModel savedUser = userModelRepository.save(user);

        return ResponseEntity.ok(savedUser);
    }

    // Helper method to generate a unique account number
    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis(); // Example: ACC123456789
    }
}
