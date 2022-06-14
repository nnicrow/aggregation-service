package nnicrow.graphql.application.repository

import nnicrow.graphql.application.models.ExternalBrand
import nnicrow.graphql.application.models.ExternalCategory
import nnicrow.graphql.application.models.ExternalProduct
import nnicrow.graphql.application.models.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ExternalProductRepository : JpaRepository<ExternalProduct, Long> {
    fun findByName(name: String) : List<ExternalProduct>?

    fun findByCategory(externalCategory: ExternalCategory) : List<ExternalProduct>?

    fun findByBrand(externalBrand: ExternalBrand) : List<ExternalProduct>?

    fun findByProduct(product: Product) : List<ExternalProduct>?

    fun findByExternalIdAndName(externalId: Long, name: String): ExternalProduct?
}