package com.anjo.starwarswikicompose.domain.model.sw

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class Movie(
        @Embedded val entity: MovieEntity = MovieEntity(),
        @Relation(
                parentColumn = "id",
                entityColumn = "id",
                associateBy = Junction(ModelChunkCrossRef::class, parentColumn = "modelId", entityColumn = "chunkId")
        )
        val characterChunks: List<UniversalChunk> = listOf(),
        @Relation(
                parentColumn = "id",
                entityColumn = "id",
                associateBy = Junction(ModelChunkCrossRef::class, parentColumn = "modelId", entityColumn = "chunkId")
        )
        val planetChunks: List<UniversalChunk> = listOf(),
        @Relation(
                parentColumn = "id",
                entityColumn = "id",
                associateBy = Junction(ModelChunkCrossRef::class, parentColumn = "modelId", entityColumn = "chunkId")
        )
        val starshipChunks: List<UniversalChunk> = listOf(),
        @Relation(
                parentColumn = "id",
                entityColumn = "id",
                associateBy = Junction(ModelChunkCrossRef::class, parentColumn = "modelId", entityColumn = "chunkId")
        )
        val vehicleChunks: List<UniversalChunk> = listOf(),
        @Relation(
                parentColumn = "id",
                entityColumn = "id",
                associateBy = Junction(ModelChunkCrossRef::class, parentColumn = "modelId", entityColumn = "chunkId")
        )
        val specieChunks: List<UniversalChunk> = listOf(),
)

data class Person(
        @Embedded val entity: PersonEntity = PersonEntity(),
        @Relation(
                parentColumn = "homeworldId",
                entityColumn = "id",
                entity = UniversalChunk::class,
        )
        val homeworld: UniversalChunk = UniversalChunk(category = Category.PLANETS),
        @Relation(
                parentColumn = "specieId",
                entityColumn = "id",
                entity = UniversalChunk::class
        )
        val specie: UniversalChunk = UniversalChunk(category = Category.SPECIES),
        @Relation(
                parentColumn = "id",
                entityColumn = "id",
                associateBy = Junction(ModelChunkCrossRef::class,
                        parentColumn = "modelId", entityColumn = "chunkId")
        )
        val movieChunks: List<UniversalChunk> = listOf(),
        @Relation(
                parentColumn = "id",
                entityColumn = "id",
                associateBy = Junction(ModelChunkCrossRef::class,
                        parentColumn = "modelId", entityColumn = "chunkId")
        )
        val starshipChunks: List<UniversalChunk> = listOf(),
        @Relation(
                parentColumn = "id",
                entityColumn = "id",
                associateBy = Junction(ModelChunkCrossRef::class,
                        parentColumn = "modelId", entityColumn = "chunkId")
        )
        val vehicleChunks: List<UniversalChunk> = listOf(),
)

data class Planet(
        @Embedded val entity: PlanetEntity = PlanetEntity(),
        @Relation(
                parentColumn = "id",
                entityColumn = "id",
                associateBy = Junction(ModelChunkCrossRef::class,
                        parentColumn = "modelId", entityColumn = "chunkId")
        )
        val characterChunks: List<UniversalChunk> = listOf(),
        @Relation(
                parentColumn = "id",
                entityColumn = "id",
                associateBy = Junction(ModelChunkCrossRef::class,
                        parentColumn = "modelId", entityColumn = "chunkId")
        )
        val movieChunks: List<UniversalChunk> = listOf(),
)

data class Specie(
        @Embedded val entity: SpecieEntity = SpecieEntity(),
        @Relation(
                parentColumn = "homeworldId",
                entityColumn = "id",
                entity = UniversalChunk::class
        )
        val homeworld: UniversalChunk = UniversalChunk(category = Category.PLANETS),
        @Relation(
                parentColumn = "id",
                entityColumn = "id",
                associateBy = Junction(ModelChunkCrossRef::class,
                        parentColumn = "modelId", entityColumn = "chunkId")
        )
        val characterChunks: List<UniversalChunk> = listOf(),
        @Relation(
                parentColumn = "id",
                entityColumn = "id",
                associateBy = Junction(ModelChunkCrossRef::class,
                        parentColumn = "modelId", entityColumn = "chunkId")
        )
        val movieChunks: List<UniversalChunk> = listOf(),
)

data class Vehicle(
        @Embedded val entity: VehicleEntity = VehicleEntity(),
        @Relation(
                parentColumn = "id",
                entityColumn = "id",
                associateBy = Junction(ModelChunkCrossRef::class,
                        parentColumn = "modelId", entityColumn = "chunkId")
        )
        val characterChunks: List<UniversalChunk> = listOf(),
        @Relation(
                parentColumn = "id",
                entityColumn = "id",
                associateBy = Junction(ModelChunkCrossRef::class,
                        parentColumn = "modelId", entityColumn = "chunkId")
        )
        val movieChunks: List<UniversalChunk> = listOf(),
)

data class Starship(
        @Embedded val entity: StarshipEntity = StarshipEntity(),
        @Relation(
                parentColumn = "id",
                entityColumn = "id",
                associateBy = Junction(ModelChunkCrossRef::class,
                        parentColumn = "modelId", entityColumn = "chunkId")
        )
        val characterChunks: List<UniversalChunk> = listOf(),
        @Relation(
                parentColumn = "id",
                entityColumn = "id",
                associateBy = Junction(ModelChunkCrossRef::class,
                        parentColumn = "modelId", entityColumn = "chunkId")
        )
        val movieChunks: List<UniversalChunk> = listOf(),
)