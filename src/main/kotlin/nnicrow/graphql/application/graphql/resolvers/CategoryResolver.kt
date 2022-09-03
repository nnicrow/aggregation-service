package nnicrow.graphql.application.graphql.resolvers

import com.coxautodev.graphql.tools.GraphQLResolver
import nnicrow.graphql.application.models.Category
import nnicrow.graphql.application.models.Product
import nnicrow.graphql.application.repository.ProductRepository
import org.springframework.stereotype.Component

@Component
class CategoryResolver(
    private val productRepository: ProductRepository,
) : GraphQLResolver<Category> {

    fun getProductList(category: Category): List<Product> {
        return productRepository.findByCategory(category)
    }
}