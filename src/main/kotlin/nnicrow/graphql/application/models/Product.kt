package nnicrow.graphql.application.models

import javax.persistence.*

@Entity
open class Product: AbstractEntity {
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

    @ManyToOne
    @JoinColumn(nullable=false)
    open var brand: Brand? = null

    @ManyToOne
    @JoinColumn(nullable=false)
    open var category: Category? = null

//    @ManyToOne
//    @JoinColumn(nullable = false)
//    open var externalCategory: ExternalCategory? = null

//    @ManyToOne
//    @JoinColumn(nullable = false)
//    open var externalBrand: ExternalBrand? = null

//    @OneToMany(mappedBy = "product")
//    @Column(nullable = true)
//    open var images: MutableSet<Images>? = null
//
//    @OneToMany(mappedBy = "product")
//    @Column(nullable = true)
//    open var certificates: MutableSet<Certificates>? = null
//
//    @OneToMany(mappedBy = "product")
//    @Column(nullable = true)
//    open var instructions: MutableSet<Instructions>? = null

//    var properties: Set<PropertiesGroup>? = null
//    var url: Set<URL>? = null
//    var price: Set<Price>? = null
    protected constructor() {}
    constructor(name: String?, nameTranslit: String? = null, modelName: String? = null, description: String? = null,
                availability: Boolean = false, url: String? = null, price: Int? = null, brand: Brand? = null,
                category: Category? = null) {
        this.name = name
        this.nameTranslit = nameTranslit
        this.modelName = modelName
        this.description = description
        this.availability = availability
        this.brand = brand
        this.category = category
    }
}