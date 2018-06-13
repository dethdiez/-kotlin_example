package io.sibur.disquad.services.facility.lib.models

import io.sibur.disquad.core.EntityValidatorListener
import io.sibur.disquad.services.apparatus.lib.models.Apparatus
import io.sibur.disquad.services.production.lib.models.Production
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.NotFound
import org.hibernate.annotations.NotFoundAction
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.OneToOne
import javax.persistence.JoinColumn
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "facility", schema = "factory")
@EntityListeners(EntityValidatorListener::class)
class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0

    @Column(name = "name", nullable = false)
    @NotEmpty
    lateinit var name: String

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "facility")
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    var levels: MutableSet<FacilityLevel> = mutableSetOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "facility")
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    var apparatuses: MutableSet<Apparatus> = mutableSetOf()

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production")
    @NotEmpty
    lateinit var production: Production
}