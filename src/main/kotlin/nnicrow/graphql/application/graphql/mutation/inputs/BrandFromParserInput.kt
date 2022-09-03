package nnicrow.graphql.application.graphql.mutation.inputs

class BrandFromParserInput(
    val id: String,
    var brandName: String,
    var brandNameTranslit: String? = null,
    var brandUrl: String? = null,
    var brandLogoUrl: String? = null,
    var isBrandVisible: Boolean? = null,
    var innerId: Int? = null
)
