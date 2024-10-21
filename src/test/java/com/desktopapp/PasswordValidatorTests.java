package com.desktopapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PasswordValidatorTests {

    @Test
    void validateSmallPass() {
        assertEquals(MyPasswordValidator.Validate("oi"), false);
        assertEquals(MyPasswordValidator.Validate("1234"), false);
        assertEquals(MyPasswordValidator.Validate("oioi"), false);
        assertEquals(MyPasswordValidator.Validate("minh"), false);
    }

    @Test
    void validateBigPasswords() {
        assertEquals(MyPasswordValidator.Validate("minhasenha123"), true);
        assertEquals(MyPasswordValidator.Validate("1234567890123"), true);
        assertEquals(MyPasswordValidator.Validate("minhasenhagigantesca"), true);
        assertEquals(MyPasswordValidator.Validate("senhaemoji 👻"), true);
    }

    @Test
    void validateMediumPasswordsWithNums() {
        assertEquals(MyPasswordValidator.Validate("senha123"), true);
        assertEquals(MyPasswordValidator.Validate("12345"), true);
        assertEquals(MyPasswordValidator.Validate("123456789012"), true);
        assertEquals(MyPasswordValidator.Validate("oi88oi88oi88"), true);
    }

    @Test
    void validateMediumPassowrdsWithoutNums() {
        assertEquals(MyPasswordValidator.Validate("minhasenha"), false);
        assertEquals(MyPasswordValidator.Validate("mysenhaquase"), false);
    }
}