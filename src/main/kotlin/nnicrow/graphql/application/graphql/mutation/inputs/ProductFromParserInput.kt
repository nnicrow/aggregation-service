package nnicrow.graphql.application.graphql.mutation.inputs

data class ProductFromParserInput(
    val id: String,
    var name: String,
    var brand: BrandFromParserInput,
    var nameTranslit: String? = null,
    var images: Set<ImagesFromParserInput>? = null,
    var category: CategoryFromParserInput,
    var description: String? = null,
    var modelName: String? = null,
//    var properties: Set<PropertiesGroup>? = null,
//    var certificates: Set<String>? = null,
//    var instructions: Set<String>? = null,
    var url: String? = null,
    var innerId: Long? = null
)