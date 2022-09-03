package nnicrow.graphql.application.graphql.resolvers

import com.coxautodev.graphql.tools.GraphQLResolver
import nnicrow.graphql.application.models.ExternalCategory
import nnicrow.graphql.application.models.ExternalProduct
import nnicrow.graphql.application.repository.ExternalProductRepository
import org.springframework.stereotype.Component

@Component
class ExternalCategoryResolver(
    private val externalProductRepository: ExternalProductRepository,
) : GraphQLResolver<ExternalCategory> {

    fun getProductList(externalCategory: ExternalCategory): List<ExternalProduct>? {
        return externalProductRepository.findByCategory(externalCategory)
    }
}