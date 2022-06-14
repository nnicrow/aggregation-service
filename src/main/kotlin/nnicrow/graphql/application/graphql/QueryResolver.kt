package nnicrow.graphql.application.graphql

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import nnicrow.graphql.application.models.*
import nnicrow.graphql.application.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class QueryResolver @Autowired constructor(
    private val brandRepository: BrandRepository,
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository,
    private val webShopRepository: WebShopRepository,
    private val externalCategoryRepository: ExternalCategoryRepository,
    private val externalBrandRepository: ExternalBrandRepository,
    private val externalProductRepository: ExternalProductRepository,
    private val sliderRepository: SliderRepository
    ): GraphQLQueryResolver {


    fun version(): String {
        return "Pre-Alpha"
    }

    fun getBrandList(): MutableIterable<Brand> {
        return brandRepository.findAll()
    }

    fun getBrandById(id: Long): Optional<Brand> {
        return brandRepository.findById(id)
    }

    fun getCategoryList(): MutableIterable<Category> {
        return categoryRepository.findAll()
    }

    fun getCategoryById(id: Long): Optional<Category> {
        return categoryRepository.findById(id)
    }

    fun getProductList(): MutableIterable<Product> {
        return productRepository.findAll()
    }

    fun getProductById(id: Long): Optional<Product> {
        return productRepository.findById(id)
    }

    fun getWebShopList(): MutableIterable<WebShop> {
        return webShopRepository.findAll()
    }

    fun getWebShopById(id: Long): Optional<WebShop> {
        return webShopRepository.findById(id)
    }

    fun getWebShopByName(name: String): WebShop? {
        return webShopRepository.findByName(name)
    }

    fun getExternalCategoryList(): MutableIterable<ExternalCategory> {
        return externalCategoryRepository.findAll()
    }

    fun getExternalCategoryById(id: Long): Optional<ExternalCategory> {
        return externalCategoryRepository.findById(id)
    }

    fun getExternalBrandList(): MutableIterable<ExternalBrand> {
        return externalBrandRepository.findAll()
    }

    fun getExternalBrandById(id: Long): Optional<ExternalBrand> {
        return externalBrandRepository.findById(id)
    }

    fun getExternalProductList(): MutableIterable<ExternalProduct> {
        return externalProductRepository.findAll()
    }

    fun getExternalProductById(id: Long): Optional<ExternalProduct> {
        return externalProductRepository.findById(id)
    }

    fun getSliderList(): MutableIterable<Slider> {
        return sliderRepository.findAll()
    }
}