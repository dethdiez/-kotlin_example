package io.sibur.disquad.services.facility.actions

import Api.Sibur.Disquad.Disquad.FacilityPacket.GetRequest
import Api.Sibur.Disquad.Disquad.FacilityPacket.GetResponse
import io.sibur.disquad.core.GrpcAction
import io.sibur.disquad.services.facility.actions.converters.toExternalModel
import io.sibur.disquad.services.facility.lib.queries.FacilityQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.persistence.EntityManager

@Component("FacilityService.GetAction")
class GetAction : GrpcAction<GetRequest, GetResponse> {
    private lateinit var facilityQuery: FacilityQuery

    @Autowired
    lateinit var entityManager: EntityManager

    override fun execute(request: GetRequest): GetResponse {
        facilityQuery = FacilityQuery(entityManager)

        val response = GetResponse.newBuilder()

        try {
            if (request.offset > 0) {
                facilityQuery.offset(request.offset.toInt())
            }

            if (request.length > 0) {
                facilityQuery.length(request.length.toInt())
            }

            if (request.scopeCount > 0) {
                request.scopeList.map { applyScope(it) }
            }

            val facilities = facilityQuery.findAll().map { it.toExternalModel() }
            val batch = GetResponse.Batch.newBuilder()
            facilities.forEach { batch.addFacility(it) }
            response.batch = batch.build()
        } catch (exp: Throwable) {
            response.error = GetResponse.Error.INTERNAL_ERROR
        }

        return response.build()
    }

    private fun applyScope(scope: GetRequest.Scope) {
        when (scope.conditionCase) {
            GetRequest.Scope.ConditionCase.LIKE_NAME -> applyByIdentifier(scope.likeName)
            else -> {
                return
            }
        }
    }

    private fun applyByIdentifier(scope: GetRequest.Scope.LikeName) {
        facilityQuery.likeName(scope.name)
    }
}