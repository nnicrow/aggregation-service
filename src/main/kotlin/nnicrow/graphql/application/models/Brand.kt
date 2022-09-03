package nnicrow.graphql.application.models

import nnicrow.graphql.application.models.AbstractEntity
import javax.persistence.*

@Entity
open class Brand: AbstractEntity {
    @Column(nullable = false, unique = true, length = 512)
    open var name: String? = null

    @Column(nullable = true, length = 512)
    open var brandNameTranslit: String? = null

    @Column(nullable = true, length = 512)
    open var brandUrl: String? = null

    @Column(nullable = true, length = 512)
    open var brandLogoUrl: String? = null

    @Column(nullable = false)
    open var brandVisible: Boolean = false

    protected constructor() {}
    constructor(name: String?, brandNameTranslit: String? = null, brandUrl: String? = null,
                brandLogoUrl: String? = null, brandVisible: Boolean = false) {
        this.name = name
        this.brandNameTranslit = brandNameTranslit
        this.brandUrl = brandUrl
        this.brandLogoUrl = brandLogoUrl
        this.brandVisible = brandVisible
    }
}