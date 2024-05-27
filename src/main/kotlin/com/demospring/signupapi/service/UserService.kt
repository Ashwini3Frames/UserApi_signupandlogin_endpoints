package com.demospring.signupapi.service
import com.demospring.signupapi.model.User
import com.demospring.signupapi.repository.UserRepositoryImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepositoryImpl) {

    @Transactional
    fun createUser(user: User): ResponseEntity<Any> {
        if (!user.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user data")
        }

        val existingEmailUser = userRepository.findByEmail(user.email)
        if (existingEmailUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists")
        }

        val existingPhoneUser = userRepository.findByPhone(user.phone)
        if (existingPhoneUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone number already exists")
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user))
    }

    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun findByPhone(phone: String): User? {
        return userRepository.findByPhone(phone)
    }
}
