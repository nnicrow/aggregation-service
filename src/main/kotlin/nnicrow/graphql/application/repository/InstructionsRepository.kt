package nnicrow.graphql.application.repository

import nnicrow.graphql.application.models.Instructions
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InstructionsRepository : JpaRepository<Instructions, Long>