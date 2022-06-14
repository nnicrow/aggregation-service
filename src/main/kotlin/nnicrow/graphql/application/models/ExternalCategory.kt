package nnicrow.graphql.application.models

import javax.persistence.*

@Entity
open class ExternalCategory: AbstractEntity {
    @Column(nullable = false, unique = true, length = 512)
    open var name: String? = null

    @Column(nullable = true, length = 4096)
    open var description: String? = null

    @Column(nullable = true)
    open var externalId: Int? = null

    @Column(nullable = false)
    open var availability: Boolean = false

    @ManyToOne
    @JoinColumn(nullable=false)
    open var webShop: WebShop? = null

    @ManyToOne
    @JoinColumn(nullable=false)
    open var category: Category? = null

    protected constructor() {}
    constructor(name: String?, description: String? = null, externalId: Int? = null, availability: Boolean = false, webShop: WebShop? = null, category: Category? = null, ) {
        this.name = name
        this.description = description
        this.externalId = externalId
        this.availability = availability
        this.webShop = webShop
        this.category = category
    }
}

