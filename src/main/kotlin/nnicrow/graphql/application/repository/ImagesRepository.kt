package nnicrow.graphql.application.repository

import nnicrow.graphql.application.models.ExternalProduct
import nnicrow.graphql.application.models.Images
import nnicrow.graphql.application.models.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImagesRepository : JpaRepository<Images, Long> {
    fun findByProduct(product: Product) : List<Images>?
}