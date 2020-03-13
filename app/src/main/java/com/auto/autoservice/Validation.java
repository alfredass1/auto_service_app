package com.auto.autoservice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static final String CREDENTIALS_PATTERN = "^[a-zA-Z]{3,20}$";
    /*
     * validuoja ivestus vartotojo duomenis prisijungimo varda ir slaptazodi
     * @param perduodamas ivestas vartotojo vardas ir slaptazodis
     * @return true-jei duomenys yra teisingi, false-jei neteisingi
     */
    public static boolean isCredentialsValid(String credentials){
        Pattern pattern = Pattern.compile(CREDENTIALS_PATTERN);
        Matcher matcher = pattern.matcher(credentials);
        return matcher.matches();
    }
}

