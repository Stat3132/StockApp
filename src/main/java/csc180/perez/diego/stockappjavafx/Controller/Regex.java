/**
 * @author eboyle
 * @createdOn 8/13/2024 at 4:29 PM
 * @projectName StockApp
 * @packageName csc180.perez.diego.stockappjavafx.Controller;
 */
package csc180.perez.diego.stockappjavafx.Controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    private Matcher matchyMatchy(String strLine, String strRegex) {
        Pattern p = null;
        Matcher m = null;

        p = Pattern.compile(strRegex);
        m = p.matcher(strLine);
        return m;
    }

    public boolean isValidFirstName(String name) {
        Matcher matcher = null;
        String humanNameRegex = "\"([A-Z]{1}+[a-z]{0,19})\"";

        matcher = matchyMatchy(name, humanNameRegex);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public boolean isValidLastName(String name) {
        Matcher matcher = null;
        String humanNameRegex = "([A-Z]{1}+[a-z]{0,19})+($|\\s)+( |[A-Z]{1}+[a-z]{0,19})?";

        matcher = matchyMatchy(name, humanNameRegex);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }


    public boolean isValidEmailAddress(String email) {
        Matcher matcher = null;

        String regexEmail = "([A-z]{1}+[A-Za-z0-9\\_\\.]+@)+([A-z]{1,}\\.)+([A-z]{3,4})";
        matcher = matchyMatchy(email, regexEmail);
        if (matcher.matches()) {
            return true;
        } else{
            return false;
        }
    }

    public boolean validatePasswordComplexity(String password, int minLength, int minUpper, int minLower, int minNumeric, int minSymbols) {
        Matcher matcher = null;
        String regex = null;

        regex = "[A-Z]"; //Min uppercase
        matcher = matchyMatchy(password, regex);
        if (countGroups(matcher) < minUpper) return false;

        regex = "[a-z]"; //Min lowercase
        matcher = matchyMatchy(password, regex);
        if (countGroups(matcher) < minLower) return false;

        regex = "[^ ]"; //Min length
        matcher = matchyMatchy(password, regex);
        if (countGroups(matcher) < minLength) return false;

        regex = "[\\d]"; // number count
        matcher = matchyMatchy(password, regex);
        if (countGroups(matcher) < minNumeric) return false;

        regex = "[\\W]"; //Min symbols
        matcher = matchyMatchy(password, regex);
        if (countGroups(matcher) < minSymbols) return false;

        return true;
    }

    public boolean isValidAge(String age){
        Matcher matcher = null;
        String ageRegex = "\\b(1[0-2][0-9]|13[0-5]|[1-9][0-9]?|[1-9])\\b";

        matcher = matchyMatchy(age, ageRegex);
        if (matcher.matches()) {
            return true;
        } else{
            return false;
        }
    }

    public boolean isValidUserName(String userName){
        Matcher matcher = null;
        String userNameRegex = "^(?=(.*\\w)).{5,12}$";
        matcher = matchyMatchy(userName, userNameRegex);
        if (matcher.matches()) {
            return true;
        }else {
            return false;
        }
    }
    private static int countGroups(Matcher matcher){
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }
}
