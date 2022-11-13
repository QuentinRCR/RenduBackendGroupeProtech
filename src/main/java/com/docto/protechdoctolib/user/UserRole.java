package com.docto.protechdoctolib.user;

/**
 * When you create an account, it creates a user profile
 * Le compte de la psychologue sera admin. Ce n'est pas encore implémenté, mais ainsi seule elle
 * pourra utiliser le service de creation de créneaux
 */

public enum UserRole {
    USER,
    ADMIN,
}
