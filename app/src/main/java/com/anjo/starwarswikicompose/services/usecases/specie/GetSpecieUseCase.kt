package com.anjo.starwarswikicompose.services.usecases.specie

import com.anjo.GetSpecieQuery
import com.anjo.starwarswikicompose.data.Repository
import javax.inject.Inject

class GetSpecieUseCase@Inject constructor(
        private val dataFetcher: Repository
) {
    suspend operator fun invoke(id:String): GetSpecieQuery.Species? {
        return dataFetcher.fetchOneSpecie(id)
    }
}