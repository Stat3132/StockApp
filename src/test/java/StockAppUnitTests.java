import csc180.perez.diego.stockappjavafx.Controller.Regex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author eboyle
 * @createdOn 8/13/2024 at 4:26 PM
 * @projectName StockApp
 * @packageName PACKAGE_NAME;
 */

public class StockAppUnitTests {
    private String apiKey = "6yr9w0ir7v0gGtri_v4LXi1ehRoAMpQd";
    @Test
    public void testValidFirstNameMethod(){
        String validHumanName = "John";
        Regex solver = new Regex();

        assertTrue(solver.isValidFirstName(validHumanName));
    }
    @Test
    public void testInvalidFirstNameMethod(){
        String validHumanName = "12345";
        Regex solver = new Regex();

        assertFalse(solver.isValidFirstName(validHumanName));
    }
    @Test
    public void testInvalidEmptyNameMethod(){
        String validHumanName = "";
        Regex solver = new Regex();

        assertFalse(solver.isValidFirstName(validHumanName));
    }
    @Test
    public void testValidLastNameMethod(){
        String validHumanName = "Ferguson";
        Regex solver = new Regex();

        assertTrue(solver.isValidLastName(validHumanName));
    }
    @Test
    public void testInvalidLastNameMethod(){
        String validHumanName = "12345";
        Regex solver = new Regex();

        assertFalse(solver.isValidLastName(validHumanName));
    }
    @Test
    public void emailValidTest() {
        String validEmail = "test@test.com";

        Regex solver = new Regex();

        assertTrue(solver.isValidEmailAddress(validEmail));
    }

    @Test
    public void emailInvalidTestFromLackOfAlphaCharacters() {
        String validEmail = "3213@test.com";

        Regex solver = new Regex();

        assertFalse(solver.isValidEmailAddress(validEmail));
    }

    @Test
    public void emailInvalidTestFromLackOfTopDomainCharacters() {
        String validEmail = "test@test.ce";

        Regex solver = new Regex();

        assertFalse(solver.isValidEmailAddress(validEmail));
    }

    @Test
    public void emailInvalidTestFromLackOfMiddleDomainCharacters() {
        String validEmail = "test@.com";

        Regex solver = new Regex();

        assertFalse(solver.isValidEmailAddress(validEmail));
    }

    @Test
    public void emailInvalidTestFromNonAlphaStartingDomain() {
        String validEmail = "test@1test.com";

        Regex solver = new Regex();

        assertFalse(solver.isValidEmailAddress(validEmail));
    }

    @Test
    public void emailInvalidTestFromNonAlphaStartingCharacter() {
        String validEmail = "1fwfs@test.com";

        Regex solver = new Regex();

        assertFalse(solver.isValidEmailAddress(validEmail));
    }
    @Test
    public void testValidPasswordComplexity(){
        String validPassword = "mYNamEisELI31111@";
        Regex solver = new Regex();
        assertTrue(solver.validatePasswordComplexity(validPassword, 2, 1, 1, 1, 1));
    }
    @Test
    public void testInValidPasswordComplexityNoSymbol(){
        String invalidPassword = "mYNamEisELI31111";
        Regex solver = new Regex();
        assertFalse(solver.validatePasswordComplexity(invalidPassword, 2, 1, 1, 1, 1));
    }
    @Test
    public void testInValidPasswordComplexityNoUpperCaseLetters(){
        String invalidPassword = "mamis31111@";
        Regex solver = new Regex();
        assertFalse(solver.validatePasswordComplexity(invalidPassword, 2, 1, 1, 1, 1));
    }
    @Test
    public void testInValidPasswordComplexityTooShort() {
        String invalidPassword = "mYNamEisELI3@";
        Regex solver = new Regex();
        assertFalse(solver.validatePasswordComplexity(invalidPassword, 25, 1, 1, 1, 1));
    }

    @Test
    public void isValidAge(){
        String validAge = "21";
        Regex solver = new Regex();
        assertTrue(solver.isValidAge(validAge));
    }
    @Test
    public void invalidAgeTooHigh(){
        String validAge = "200";
        Regex solver = new Regex();
        assertFalse(solver.isValidAge(validAge));
    }
    @Test
    public void invalidAgeTooLow(){
        String validAge = "0";
        Regex solver = new Regex();
        assertFalse(solver.isValidAge(validAge));
    }
    @Test
    public void validUserName(){
        String validUserName = "eboyle-31";
        Regex solver = new Regex();
        assertTrue(solver.isValidUserName(validUserName));
    }
    @Test
    public void invalidUserNameTooLong(){
        String validUserName = "eboyle-3111111111111";
        Regex solver = new Regex();
        assertFalse(solver.isValidUserName(validUserName));
    }
    @Test
    public void invalidUserNameTooShort(){
        String validUserName = "e";
        Regex solver = new Regex();
        assertFalse(solver.isValidUserName(validUserName));
    }
    @Test
    public void testProperAPIKey(){
        String APIKey = "6yr9w0ir7v0gGtri_v4LXi1ehRoAMpQd";
        assertEquals(apiKey, APIKey);
    }
}
