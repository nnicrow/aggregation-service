package nnicrow.graphql.application.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne


@Entity
open class ExternalBrand: AbstractEntity {
    @Column(nullable = false, unique = true, length = 512)
    open var name: String? = null

    @Column(nullable = true, length = 512)
    open var brandNameTranslit: String? = null

    @Column(nullable = true, length = 512)
    open var brandUrl: String? = null

    @Column(nullable = true, length = 512)
    open var brandLogoUrl: String? = null

    @Column(nullable = true)
    open var externalId: Long? = null

    @ManyToOne
    @JoinColumn(nullable=false)
    open var webShop: WebShop? = null

    @ManyToOne
    @JoinColumn(nullable=false)
    open var brand: Brand? = null

    protected constructor() {}
    constructor(name: String?, brandNameTranslit: String? = null, brandUrl: String? = null,
                brandLogoUrl: String? = null, webShop: WebShop? = null, brand: Brand? = null, externalId: Long? = null){
        this.name = name
        this.brandNameTranslit = brandNameTranslit
        this.brandUrl = brandUrl
        this.brandLogoUrl = brandLogoUrl
        this.webShop = webShop
        this.brand = brand
        this.externalId = externalId
    }
}