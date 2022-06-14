package nnicrow.graphql.application.repository

import nnicrow.graphql.application.models.Slider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface SliderRepository : JpaRepository<Slider, Long> {
    fun findByName(name: String): Slider?
}