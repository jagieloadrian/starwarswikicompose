package com.anjo.starwarswikicompose.services.data.database.swmodels

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.domain.model.sw.ModelChunkCrossRef
import com.anjo.starwarswikicompose.domain.model.sw.Movie
import com.anjo.starwarswikicompose.domain.model.sw.MovieEntity
import com.anjo.starwarswikicompose.domain.model.sw.Person
import com.anjo.starwarswikicompose.domain.model.sw.PersonEntity
import com.anjo.starwarswikicompose.domain.model.sw.Planet
import com.anjo.starwarswikicompose.domain.model.sw.PlanetEntity
import com.anjo.starwarswikicompose.domain.model.sw.Specie
import com.anjo.starwarswikicompose.domain.model.sw.SpecieEntity
import com.anjo.starwarswikicompose.domain.model.sw.Starship
import com.anjo.starwarswikicompose.domain.model.sw.StarshipEntity
import com.anjo.starwarswikicompose.domain.model.sw.UniversalChunk
import com.anjo.starwarswikicompose.domain.model.sw.Vehicle
import com.anjo.starwarswikicompose.domain.model.sw.VehicleEntity
import com.anjo.starwarswikicompose.utils.Constants.MODEL_CHUNK_CROSS_REF_TABLE
import com.anjo.starwarswikicompose.utils.Constants.MOVIE_TABLE
import com.anjo.starwarswikicompose.utils.Constants.PERSON_TABLE
import com.anjo.starwarswikicompose.utils.Constants.PLANET_TABLE
import com.anjo.starwarswikicompose.utils.Constants.SPECIE_TABLE
import com.anjo.starwarswikicompose.utils.Constants.STARSHIP_TABLE
import com.anjo.starwarswikicompose.utils.Constants.UNIVERSAL_CHUNK_TABLE
import com.anjo.starwarswikicompose.utils.Constants.VEHICLE_TABLE

@Dao
interface SwModelsDao {

    //UNIVERSAL CHUNK
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChunk(chunk: UniversalChunk)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChunkCrossRef(modelChunkCrossRef: ModelChunkCrossRef)

    @Transaction
    @Query("DELETE FROM $MODEL_CHUNK_CROSS_REF_TABLE where modelId = :modelId")
    suspend fun deleteChunkCrossRefByModelId(modelId: String)

    @Transaction
    @Query("DELETE FROM $MODEL_CHUNK_CROSS_REF_TABLE where chunkId = :chunkId")
    suspend fun deleteChunkCrossRefByChunkId(chunkId: String)

    @Query("Select * from $UNIVERSAL_CHUNK_TABLE where category = :category")
    fun getUniversalChunkByCategory(category: Category): List<UniversalChunk>

    @Query("Select * from $UNIVERSAL_CHUNK_TABLE where id = :id")
    fun getUniversalChunkById(id: String): UniversalChunk?

    @Transaction
    @Query("Delete from $UNIVERSAL_CHUNK_TABLE where id = :universalChunkId")
    fun deleteUniversalChunkById(universalChunkId: String)

    //PEOPLE
    @Transaction
    @Query("Select * from $PERSON_TABLE where id = :id")
    fun getPersonById(id: String): Person

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: PersonEntity)

    @Transaction
    @Query("DELETE FROM $PERSON_TABLE where id = :id")
    suspend fun deletePersonById(id: String)

    //MOVIE
    @Transaction
    @Query("Select * from $MOVIE_TABLE where id = :id")
    fun getMovieById(id: String): Movie

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Transaction
    @Query("DELETE FROM $MOVIE_TABLE where id = :id")
    suspend fun deleteMovieById(id: String)

    //PLANET
    @Transaction
    @Query("Select * from $PLANET_TABLE where id = :id")
    fun getPlanetById(id: String): Planet

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanet(planet: PlanetEntity)

    @Transaction
    @Query("DELETE FROM $PLANET_TABLE where id = :id")
    suspend fun deletePlanetById(id: String)

    //SPECIE
    @Transaction
    @Query("Select * from $SPECIE_TABLE where id = :id")
    fun getSpecieById(id: String): Specie

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpecie(specie: SpecieEntity)

    @Transaction
    @Query("DELETE FROM $SPECIE_TABLE where id = :id")
    suspend fun deleteSpecieById(id: String)

    //VEHICLE
    @Transaction
    @Query("Select * from $VEHICLE_TABLE where id = :id")
    fun getVehicleById(id: String): Vehicle

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVehicle(vehicle: VehicleEntity)

    @Transaction
    @Query("DELETE FROM $VEHICLE_TABLE where id = :id")
    suspend fun deleteVehicleById(id: String)

    //STARSHIP
    @Transaction
    @Query("Select * from $STARSHIP_TABLE where id = :id")
    fun getStarshipById(id: String): Starship

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStarship(starship: StarshipEntity)

    @Transaction
    @Query("DELETE FROM $STARSHIP_TABLE where id = :id")
    suspend fun deleteStarshipById(id: String)
}