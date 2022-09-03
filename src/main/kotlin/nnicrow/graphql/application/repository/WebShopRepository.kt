package nnicrow.graphql.application.repository

import nnicrow.graphql.application.models.WebShop
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface WebShopRepository : JpaRepository<WebShop, Long> {
    fun findByName(name: String): WebShop?
}