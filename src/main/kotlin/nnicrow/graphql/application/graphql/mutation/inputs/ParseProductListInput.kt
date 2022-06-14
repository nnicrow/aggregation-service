package nnicrow.graphql.application.graphql.mutation.inputs

data class ParseProductListInput (
    val products: MutableSet<ProductFromParserInput?>,
    val webShop: Long
)