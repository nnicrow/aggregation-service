package nnicrow.graphql.application.models

import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Slider: AbstractEntity {
    @Column(nullable = false, unique = true, length = 64)
    open var name: String? = null

    @Column(nullable = true, length = 128)
    open var description: String? = null

    @Column(nullable = true, length = 512)
    open var path: String? = null

    protected constructor() {}
    constructor(name: String?, path: String? = null, description: String? = null) {
        this.name = name
        this.path = path
        this.description = description
    }
//   Asus
//   ROG Strix G15 Advantage Edition
//   https://www.notebookcheck-ru.com/fileadmin/_processed_/f/9/csm_nbUPIEKI_36151e179a.jpg
//   MSI
//   Raider GE76 - 12U
//   https://storage-asset.msi.com/ru/picture/banner/banner_16528836562972e0bf144d2510de9629f421e16163.jpeg
}