package nnicrow.graphql.application.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
open class Instructions: AbstractEntity() {
    @Column(nullable = false, unique = true, length = 512)
    open var url: String? = null

    @ManyToOne
    @JoinColumn(nullable=false)
    open var product: Product? = null
}