package io.sibur.disquad.services.facility.actions.converters

import Api.Sibur.Disquad.Disquad
import io.sibur.disquad.services.facility.lib.models.Facility
import io.sibur.disquad.services.facility.lib.models.FacilityLevel

fun Facility.toExternalModel(): Disquad.Facility {
    val facility = Disquad.Facility.newBuilder()

    facility.identifier = Disquad.Facility.Identifier.newBuilder().setId(this.id).build()
    facility.name = this.name

    this.apparatuses.forEach {
        facility
                .addApparatus(Disquad
                        .Apparatus
                        .Identifier
                        .newBuilder()
                        .setId(it.id)
                        .build())
    }

    this.levels.forEach { facility.addLevel(it.toExternalModel()) }

    return facility.build()
}

fun FacilityLevel.toExternalModel(): Disquad.Facility.Level {
    val level = Disquad.Facility.Level.newBuilder()
    level.level = Disquad.Structures.LengthUnit.Meter.newBuilder().setValue(this.level.toDouble()).build()
    level.schema = this.schema

    return level.build()
}