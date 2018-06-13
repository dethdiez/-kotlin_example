package io.sibur.disquad.services.facility.lib.repositories

import io.sibur.disquad.services.facility.lib.models.Facility
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FacilityRepository : CrudRepository<Facility, Long>