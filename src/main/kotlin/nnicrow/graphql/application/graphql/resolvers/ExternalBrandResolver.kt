package nnicrow.graphql.application.graphql.resolvers

import com.coxautodev.graphql.tools.GraphQLResolver
import nnicrow.graphql.application.models.ExternalBrand
import nnicrow.graphql.application.models.ExternalProduct
import nnicrow.graphql.application.repository.ExternalProductRepository
import org.springframework.stereotype.Component

@Component
class ExternalBrandResolver(
    private val externalProductRepository: ExternalProductRepository,
) : GraphQLResolver<ExternalBrand> {

    fun getProductList(externalBrand: ExternalBrand): List<ExternalProduct>? {
        return externalProductRepository.findByBrand(externalBrand)
    }
}