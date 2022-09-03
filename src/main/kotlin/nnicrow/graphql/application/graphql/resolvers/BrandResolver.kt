package nnicrow.graphql.application.graphql.resolvers

import com.coxautodev.graphql.tools.GraphQLResolver
import nnicrow.graphql.application.models.Brand
import nnicrow.graphql.application.models.Product
import nnicrow.graphql.application.repository.ProductRepository
import org.springframework.stereotype.Component

@Component
class BrandResolver(
    private val productRepository: ProductRepository,
) : GraphQLResolver<Brand> {

    fun getProductList(brand: Brand): List<Product> {
        return productRepository.findByBrand(brand)
    }
}