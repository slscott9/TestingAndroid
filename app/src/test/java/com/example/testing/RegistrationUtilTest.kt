package com.example.testing

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest {

    /*
        The input is not valid if
            the user name or password is empty
            the username is already taken
            the confirmed password is not the same as real password
            the password contains less than 2 digits
     */

    @Test
    fun `empty user name returns false ` () {
        val result = RegistrationUtil.validateRegistrationInput(
            "",
            "123",
        "123"
        )

        assertThat(result).isFalse()
    }


    @Test
    fun `valid username and correctly repeated password returns true ` () {
        val result = RegistrationUtil.validateRegistrationInput(
            "Stuart",
            "123",
            "123"
        )

        assertThat(result).isTrue()
    }


    @Test
    fun `confirmed password is not the same as password ` () {
        val result = RegistrationUtil.validateRegistrationInput(
            "Carl",
            "123",
            "1234"
        )

        assertThat(result).isFalse()
    }

    @Test
    fun `the password is less than two digits ` () {
        val result = RegistrationUtil.validateRegistrationInput(
            "Carl",
            "1",
            "123"
        )

        assertThat(result).isFalse()
    }

}