package com.anjo.starwarswikicompose.services.usecases.starship

import com.anjo.GetStarshipQuery
import com.anjo.starwarswikicompose.data.Repository
import javax.inject.Inject

class GetStarshipUseCase@Inject constructor(
        private val dataFetcher: Repository
) {
    suspend operator fun invoke(id:String): GetStarshipQuery.Starship? {
        return dataFetcher.fetchOneStarship(id)
    }
}