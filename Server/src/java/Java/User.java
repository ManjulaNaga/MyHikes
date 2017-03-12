package Java;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
/**
 * @manju 2/10/2015
 */
@Stateless
@ManagedBean(name="user")
@SessionScoped
public class User {
    private String firstname;
    private String username;
    private String lastname;
    private String password;
    private String email;
    private String phone;
    private String gender;
    private String country;
    private String address;
    private String zipcode;
    private String hikename;
            public String getHikename()
            {
              return hikename;
            }
            public void setHikename(String hname)
            {
                hikename = hname;
            }
     public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
 
   
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastName) {
        this.lastname = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return firstname;
    }
     public void setUsername(String firstname) {
        this.username = username;
    }
    
    }
