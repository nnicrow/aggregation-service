package nnicrow.graphql.application.graphql.resolvers

import com.coxautodev.graphql.tools.GraphQLResolver
import nnicrow.graphql.application.models.ExternalBrand
import nnicrow.graphql.application.models.ExternalCategory
import nnicrow.graphql.application.models.WebShop
import nnicrow.graphql.application.repository.ExternalBrandRepository
import nnicrow.graphql.application.repository.ExternalCategoryRepository
import org.springframework.stereotype.Component

@Component
class WebShopResolver(
    private val externalBrandRepository: ExternalBrandRepository,
    private val externalCategoryRepository: ExternalCategoryRepository
) : GraphQLResolver<WebShop> {

    fun getCategoryList(webShop: WebShop): List<ExternalCategory>? {
        return externalCategoryRepository.findByWebShop(webShop)
    }

    fun getBrandList(webShop: WebShop): List<ExternalBrand>? {
        return externalBrandRepository.findByWebShop(webShop)
    }
}