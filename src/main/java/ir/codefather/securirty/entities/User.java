package ir.codefather.securirty.entities;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private int active;
    private String roles;
    private String authorities;

    public User(String username, String password, String roles, String authorities) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.authorities = authorities;
        active = 1;
    }

    protected User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }


    public List<String> getAuthoritiesList() {
        if (!this.authorities.isBlank())
            return Arrays.asList(this.authorities.split(","));

        return new ArrayList<>();
    }


    public List<String> getRolesList() {
        if (!this.roles.isBlank())
            return Arrays.asList(this.roles.split(","));

        return new ArrayList<>();
    }
}
