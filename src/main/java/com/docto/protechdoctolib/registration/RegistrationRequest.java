package com.docto.protechdoctolib.registration;


public class RegistrationRequest {
    private final String nom;
    private final String prenom;
    private final String email;
    private final String password;

    private final Double phonenumber;

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Double getPhonenumber() { return phonenumber; }

    public RegistrationRequest(String nom, String prenom, String email, String password, Double phonenumber) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.phonenumber = phonenumber;
    }




}
