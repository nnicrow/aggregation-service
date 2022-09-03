package nnicrow.graphql.application.graphql.mutation.outputs

data class AddMutationResult (
    val status: Boolean,
    val id: Long? = null,
    val error: String? = null
    )