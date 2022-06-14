package nnicrow.graphql.application.repository

import nnicrow.graphql.application.models.Brand
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BrandRepository : JpaRepository<Brand, Long> {
    fun findByName(name: String): Brand?
}