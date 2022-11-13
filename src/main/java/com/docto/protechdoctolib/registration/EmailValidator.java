package com.docto.protechdoctolib.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

/**
 * Permet d'ajouter une contrainte sur l'email que l'utilisateur rentre à l'inscription
 */
@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s){
//          TODO: Regex to validate email
        return true;
    }
}
