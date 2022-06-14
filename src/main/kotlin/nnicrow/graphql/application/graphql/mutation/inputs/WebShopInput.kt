package nnicrow.graphql.application.graphql.mutation.inputs

data class WebShopInput (
    val name: String,
    val description: String?,
    val availability: Boolean = false,
    val link: String?
    )

