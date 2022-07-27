package m2i.userauthentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "utilisateurs")
public class User {
    
    // Properties
     @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "firstname")
    private String firstName;
    private String role;
    private String email;
   @JsonProperty(value= "password", access=JsonProperty.Access.WRITE_ONLY)
    private String password;

    
    // Constructors
    public User() {
    }

    public User(String lastName, String firstName, String role, String email, String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.role = role;
        this.email = email;
        this.password = password;
    }
    

    // Getters
    public int getId() {
        return this.id;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getRole() {
        return this.role;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
    
    
    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", role=" + role + ", email=" + email + ", password=" + password + '}';
    }

     public boolean hasAFieldEmpty() {
        return getLastName() == null || getFirstName() == null || getEmail() == null || getPassword() == null || getRole() == null;
    }

   public void copy(User data) {
        if (data.getLastName() != null) {
            this.lastName = data.getLastName();
        }

        if (data.getFirstName() != null) {
            this.firstName = data.getFirstName();
        }

        if (data.getEmail() != null) {
            this.email = data.getEmail();
        }

        if (data.getPassword() != null) {
            this.password = data.getPassword();
        }

        if (data.getRole() != null) {
            this.role = data.getRole();
        }
    }
    
    
}
