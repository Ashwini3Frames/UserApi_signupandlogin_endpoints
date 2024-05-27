package com.demospring.signupapi.repository

import com.demospring.signupapi.model.User

interface UserRepository {
    fun findByEmail(email: String): User?
    fun findByPhone(phone: String): User?
    fun save(user: User): User
}
