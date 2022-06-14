package nnicrow.graphql.application.repository

import nnicrow.graphql.application.models.Brand
import nnicrow.graphql.application.models.Category
import nnicrow.graphql.application.models.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findByName(name: String): Product?

    fun findByBrand(brand: Brand): List<Product>

    fun findByCategory(category: Category): List<Product>
}