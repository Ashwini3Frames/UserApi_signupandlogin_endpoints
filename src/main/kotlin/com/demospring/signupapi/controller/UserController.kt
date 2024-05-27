package com.demospring.signupapi.controller
import com.demospring.signupapi.model.LoginCredentials
import com.demospring.signupapi.model.User
import com.demospring.signupapi.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @PostMapping("/signup")
    fun signUp(@RequestBody user: User): ResponseEntity<Any> {
        if (!user.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user data")
        }

        val existingEmailUser = userService.findByEmail(user.email)
        if (existingEmailUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists")
        }

        val existingPhoneUser = userService.findByPhone(user.phone)
        if (existingPhoneUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone number already exists")
        }

        val createdUser = userService.createUser(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser)
    }

    @PostMapping("/login")
    fun login(@RequestBody credentials: LoginCredentials?): ResponseEntity<Any> {
        // Check if credentials is null
        if (credentials == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login credentials are missing")
        }

        val emailOrPhone = credentials.emailOrPhone
        val password = credentials.password

        if (emailOrPhone.isBlank() || password.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email/phone or password is empty")
        }

        val user = if (emailOrPhone.contains('@')) {
            userService.findByEmail(emailOrPhone)
        } else {
            userService.findByPhone(emailOrPhone)
        }

        if (user == null || user.password != password) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email/phone or password")
        }

        // Successfully logged in
        return ResponseEntity.ok(user)
    }

}

