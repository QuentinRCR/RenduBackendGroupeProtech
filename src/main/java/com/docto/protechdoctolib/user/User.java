package com.docto.protechdoctolib.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name="RUser")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    Long Id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Double phonenumber;
    @Enumerated(EnumType.STRING)
    private UserRole appUserRole;
    private Boolean locked;
    private Boolean enabled;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(Double phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority= new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public User(){

    }

    public User( String nom, String prenom, String email, String password, Double phonenumber, UserRole appUserRole) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.phonenumber = phonenumber;
        this.appUserRole = appUserRole;
    }
}
