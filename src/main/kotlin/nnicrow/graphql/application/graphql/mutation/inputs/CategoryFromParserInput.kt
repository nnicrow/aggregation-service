package nnicrow.graphql.application.graphql.mutation.inputs

data class CategoryFromParserInput (
    val id: String,
    var name: String,
    var innerId: Int
)
