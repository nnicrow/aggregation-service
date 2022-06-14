package nnicrow.graphql.application.repository

import nnicrow.graphql.application.models.ExternalCategory
import nnicrow.graphql.application.models.WebShop
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ExternalCategoryRepository : JpaRepository<ExternalCategory, Long> {
    fun findByName(name: String): List<ExternalCategory>?

    fun findByWebShop(webShop: WebShop): List<ExternalCategory>?
}