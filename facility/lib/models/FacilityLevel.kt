package io.sibur.disquad.services.facility.lib.models

import io.sibur.disquad.core.EntityValidatorListener
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.validation.constraints.Digits
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "facility_level", schema = "factory")
@EntityListeners(EntityValidatorListener::class)
class FacilityLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility")
    @NotEmpty
    lateinit var facility: Facility

    @Column(name = "level", nullable = false)
    @NotEmpty
    @Digits(integer = 3, fraction = 2)
    lateinit var level: BigDecimal

    @Column(name = "schema", nullable = false)
    @NotEmpty
    lateinit var schema: String
}