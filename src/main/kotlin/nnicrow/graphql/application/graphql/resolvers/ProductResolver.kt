package nnicrow.graphql.application.graphql.resolvers

import com.coxautodev.graphql.tools.GraphQLResolver
import nnicrow.graphql.application.models.ExternalProduct
import nnicrow.graphql.application.models.Images
import nnicrow.graphql.application.models.Product
import nnicrow.graphql.application.repository.ExternalProductRepository
import nnicrow.graphql.application.repository.ImagesRepository
import org.springframework.stereotype.Component

@Component
class ProductResolver(
    private val externalProductRepository: ExternalProductRepository,
    private val imagesRepository: ImagesRepository
) : GraphQLResolver<Product> {

    fun getExternalProductList(product: Product): List<ExternalProduct>? {
        return externalProductRepository.findByProduct(product)
    }

    fun getImagesList(product: Product): List<Images>? {
        return imagesRepository.findByProduct(product)
    }
}