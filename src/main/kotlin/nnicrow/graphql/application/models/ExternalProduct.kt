package nnicrow.graphql.application.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
open class ExternalProduct: AbstractEntity {
    @Column(nullable = false, unique = true, length = 512)
    open var name: String? = null

    @Column(nullable = true, length = 256)
    open var nameTranslit: String? = null

    @Column(nullable = true, length = 256)
    open var modelName: String? = null

    @Column(nullable = true, length = 4096)
    open var description: String? = null

    @Column(nullable = false)
    open var availability: Boolean = false

    @Column(nullable = true, length = 512)
    open var url: String? = null

    @Column(nullable = true, length = 512)
    open var price: Int? = null

    @Column(nullable = true)
    open var externalId: Long? = null

    @ManyToOne
    @JoinColumn(nullable=false)
    open var brand: ExternalBrand? = null

    @ManyToOne
    @JoinColumn(nullable=false)
    open var category: ExternalCategory? = null

    @ManyToOne
    @JoinColumn(nullable=false)
    open var product: Product? = null

    protected constructor() {}
    constructor(name: String?, nameTranslit: String? = null, modelName: String? = null, description: String? = null,
                availability: Boolean = false, url: String? = null, price: Int? = null, brand: ExternalBrand? = null,
                category: ExternalCategory? = null, product: Product? = null, externalId: Long? = null) {
        this.name = name
        this.nameTranslit = nameTranslit
        this.modelName = modelName
        this.description = description
        this.availability = availability
        this.url = url
        this.price = price
        this.brand = brand
        this.category = category
        this.product = product
        this.externalId = externalId
    }
}