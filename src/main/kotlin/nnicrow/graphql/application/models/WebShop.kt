package nnicrow.graphql.application.models

import javax.persistence.Column
import javax.persistence.Entity

@Entity
open class WebShop: AbstractEntity {
    @Column(nullable = false, unique = true, length = 512)
    open var name: String? = null

    @Column(nullable = true, length = 4096)
    open var description: String? = null

    @Column(nullable = false)
    open var availability: Boolean = false

    @Column(nullable = true, length = 512)
    open var link: String? = null

    protected constructor() {}
    constructor(name: String?, description: String? = null, availability: Boolean = false, link: String? = null) {
        this.name = name
        this.description = description
        this.availability = availability
        this.link = link
    }
}