package io.sibur.disquad.services.facility.lib.repositories

import io.sibur.disquad.services.facility.lib.models.FacilityLevel
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FacilityLevelRepository : CrudRepository<FacilityLevel, Long>