package com.demospring.signupapi.repository

import com.demospring.signupapi.model.User
import org.springframework.stereotype.Repository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext

@Repository
class UserRepositoryImpl(
        @PersistenceContext private val entityManager: EntityManager
) : UserRepository {

    override fun findByEmail(email: String): User? {
        val query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User::class.java)
        query.setParameter("email", email)
        val resultList = query.resultList
        return resultList.firstOrNull()
    }

    override fun findByPhone(phone: String): User? {
        val query = entityManager.createQuery("SELECT u FROM User u WHERE u.phone = :phone", User::class.java)
        query.setParameter("phone", phone)
        val resultList = query.resultList
        return resultList.firstOrNull()
    }

    override fun save(user: User): User {
        entityManager.persist(user)
        return user
    }
}
