package nnicrow.graphql.application.models

import javax.persistence.*

@MappedSuperclass
open class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    open var Id: Long? = null
}