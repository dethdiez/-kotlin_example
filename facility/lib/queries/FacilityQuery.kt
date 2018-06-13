package io.sibur.disquad.services.facility.lib.queries

import io.sibur.disquad.services.facility.lib.models.Facility
import javax.persistence.EntityManager
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class FacilityQuery(private var entityManager: EntityManager) {
    private var query: CriteriaQuery<Facility> = entityManager.entityManagerFactory.criteriaBuilder.createQuery(Facility::class.java)
    private var restrictionPredicates = mutableSetOf<Predicate>()
    private var selection: Root<Facility> = query.from(Facility::class.java)
    private var firstResult: Int? = null
    private var maxResults: Int? = null

    init {
        this.query.select(selection)
    }

    fun likeName(name: String): FacilityQuery {
        val expression = selection.get<String>("name")
        val predicate = entityManager.criteriaBuilder.like(expression, "%$name%")
        restrictionPredicates.add(predicate)

        return this
    }

    fun offset(offset: Int): FacilityQuery {
        this.firstResult = offset

        return this
    }

    fun length(length: Int): FacilityQuery {
        this.maxResults = length

        return this
    }

    fun findAll(): Iterable<Facility> {
        this.query.select(selection)

        if (restrictionPredicates.count() > 0) {
            query.where(*restrictionPredicates.toTypedArray())
            restrictionPredicates.clear()
        }

        val typedQuery = entityManager.createQuery(query)

        if (firstResult != null) {
            typedQuery.firstResult = firstResult as Int
        }

        if (maxResults != null) {
            typedQuery.maxResults = maxResults as Int
        }

        return typedQuery.resultList
    }
}