package nnicrow.graphql.application.graphql.mutation

import nnicrow.graphql.application.graphql.mutation.inputs.BrandFromParserInput
import nnicrow.graphql.application.graphql.mutation.inputs.ProductFromParserInput
import nnicrow.graphql.application.models.*
import nnicrow.graphql.application.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ModelsSynchronizer(
    private val brandRepository: BrandRepository,
    private val externalBrandRepository: ExternalBrandRepository,
    private val productRepository: ProductRepository,
    private val externalProductRepository: ExternalProductRepository,
    private val externalCategoryRepository: ExternalCategoryRepository,
    private val webShopRepository: WebShopRepository,
) {
    private fun brandSynchronization(brandInput: BrandFromParserInput, webShopId: Long): ExternalBrand {
        brandInput.innerId?.let { innerId ->
            return externalBrandRepository.findById(innerId.toLong()).get()
        }
        val externalBrandObject: ExternalBrand? = externalBrandRepository.findByExternalIdAndName(
            brandInput.id.toLong(), brandInput.brandName
        )
        if (externalBrandObject != null) return externalBrandObject

        // TODO: Поменять на более нормальный поиск на схожести в БД
        val brand: Brand = brandRepository.findByName(brandInput.brandName)
            ?: Brand(
                name = brandInput.brandName,
                brandNameTranslit = brandInput.brandNameTranslit,
                brandUrl = brandInput.brandUrl,
                brandLogoUrl = brandInput.brandLogoUrl,
                brandVisible = brandInput.isBrandVisible == true,
            )
        brandRepository.save(brand)

        val webShop: WebShop = webShopRepository.getById(webShopId)
        val externalBrand = ExternalBrand(
            name=brandInput.brandName,
            brandNameTranslit=brandInput.brandNameTranslit,
            brandUrl=brandInput.brandUrl,
            brandLogoUrl=brandInput.brandLogoUrl,
            webShop=webShop,
            brand=brand,
            externalId=brandInput.id.toLong()
        )
        externalBrandRepository.save(externalBrand)

        return externalBrand
    }

    fun productSynchronization(productInput: ProductFromParserInput, webShopId: Long): ExternalProduct? {
        productInput.innerId?.let { innerId: Long ->
            return externalProductRepository.findById(innerId).get()
        }

        val externalProductObject: ExternalProduct? = externalProductRepository.findByExternalIdAndName(
            productInput.id.toLong(), productInput.name
        )
        if (externalProductObject != null) return externalProductObject

        val externalBrand: ExternalBrand = brandSynchronization(productInput.brand,
            webShopId)
        val externalCategory: ExternalCategory = externalCategoryRepository.getById(
            productInput.category.innerId.toLong())

        // TODO: Поменять на более нормальный поиск на схожести в БД
        val product: Product = productRepository.findByName(productInput.name)
            ?: Product(
                name=productInput.name,
                availability=true,
                brand=externalBrand.brand,
                category=externalCategory.category
            )
        productRepository.save(product)
        val externalProduct = ExternalProduct(
            name=productInput.name,
            availability=true,
            brand=externalBrand,
            category=externalCategory,
            product=product
        )
        externalProductRepository.save(externalProduct)

        return externalProduct
    }
}