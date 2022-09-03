package nnicrow.graphql.application.repository

import nnicrow.graphql.application.models.Certificates
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CertificatesRepository : JpaRepository<Certificates, Long>