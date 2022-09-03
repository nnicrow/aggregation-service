package nnicrow.graphql.application.repository

import nnicrow.graphql.application.models.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByName(name: String): List<Category>?
}