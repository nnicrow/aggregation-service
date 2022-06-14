package nnicrow.graphql.application.repository

import nnicrow.graphql.application.models.Certificates
import nnicrow.graphql.application.models.ExternalBrand
import nnicrow.graphql.application.models.ExternalCategory
import nnicrow.graphql.application.models.WebShop
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ExternalBrandRepository : JpaRepository<ExternalBrand, Long> {
    fun findByName(name: String): List<ExternalBrand>?

    fun findByWebShop(webShop: WebShop): List<ExternalBrand>?

    fun findByExternalIdAndName(externalId: Long, name: String): ExternalBrand?
}