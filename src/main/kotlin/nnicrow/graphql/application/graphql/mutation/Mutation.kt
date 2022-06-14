package nnicrow.graphql.application.graphql.mutation

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import nnicrow.graphql.application.graphql.mutation.inputs.ParseProductListInput
import nnicrow.graphql.application.graphql.mutation.outputs.AddMutationResult
import nnicrow.graphql.application.graphql.mutation.inputs.ProductFromParserInput
import nnicrow.graphql.application.models.Images
import nnicrow.graphql.application.repository.*
import org.springframework.stereotype.Component

@Component
class Mutation(
    private val webShopRepository: WebShopRepository,
    private val modelsSynchronizer: ModelsSynchronizer,
    private val productRepository: ProductRepository,
    private val externalProductRepository: ExternalProductRepository,
    private val imagesRepository: ImagesRepository
): GraphQLMutationResolver {

    fun addProducts(parseProductListInput: ParseProductListInput): AddMutationResult {
        parseProductListInput.products.forEach { product: ProductFromParserInput? ->
            product?.let {
                val externalProduct = modelsSynchronizer.productSynchronization(
                    it, parseProductListInput.webShop) ?: return@forEach

                externalProduct.nameTranslit=it.nameTranslit
                externalProduct.modelName=it.modelName
                externalProduct.description=it.description
                externalProduct.url=it.url
                externalProduct.externalId=it.id.toLong()
                externalProductRepository.save(externalProduct)
                val productEntity = externalProduct.product ?: return@forEach
                productEntity.nameTranslit=it.nameTranslit
                productEntity.modelName=it.modelName
                productEntity.description=it.description
                productRepository.save(productEntity)

                product.images?.forEach {image ->
                    imagesRepository.save(Images(
                        image.url,
                        productEntity
                    ))
                }
            }
        }
        return AddMutationResult(true)
    }

    //    fun addWebShop(webShopInput: WebShopInput): AddMutationResult {
//        val entityList: Collection<WebShop>? = webShopRepository.findByName(webShopInput.name)
//        var entity: WebShop? = if (entityList?.isNotEmpty() == true) entityList.first() else null
//        entity?.let {
//            it.link = webShopInput.link
//            it.availability = webShopInput.availability
//            it.availability = webShopInput.availability
//            it.description = webShopInput.description
//            webShopRepository.save(it)
//            return AddMutationResult(false, it.Id, "Object already exist")
//        }
//        entity = webShopRepository.save(
//            WebShop(webShopInput.name, webShopInput.description, webShopInput.availability ?: false,
//                webShopInput.link)
//        )
//        return AddMutationResult(true, entity.Id)
//    }

}