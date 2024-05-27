package com.demospring.signupapi.model
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        val name: String = "",
        val email: String = "",
        val phone: String = "",
        val password: String = "",
        val gender: String = ""
) {
        fun isValid(): Boolean {
                return isNameValid(name) &&
                        isEmailValid(email) &&
                        isPhoneValid(phone) &&
                        isPasswordValid(password) &&
                        isGenderValid(gender)
        }

        private fun isNameValid(name: String): Boolean {
                return name.isNotBlank()
        }

        private fun isEmailValid(email: String): Boolean {
                // Custom email validation logic
                return email.matches(Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"))
        }

        private fun isPhoneValid(phone: String): Boolean {
                // Custom phone validation logic
                return phone.matches(Regex("^\\+(?:[0-9] ?){6,14}[0-9]$"))
        }

        private fun isPasswordValid(password: String): Boolean {
                // Custom password validation logic
                return password.length >= 6
        }

        private fun isGenderValid(gender: String): Boolean {
                return gender.isNotBlank() && listOf("male", "female", "other").contains(gender.toLowerCase())
        }
}
