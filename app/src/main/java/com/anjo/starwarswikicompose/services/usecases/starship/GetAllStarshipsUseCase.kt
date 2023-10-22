package com.anjo.starwarswikicompose.services.usecases.starship

import com.anjo.GetAllStarshipsQuery
import com.anjo.starwarswikicompose.data.Repository
import javax.inject.Inject

class GetAllStarshipsUseCase @Inject constructor(
        private val dataFetcher: Repository
) {
    suspend operator fun invoke(): List<GetAllStarshipsQuery.Starship?>? {
        return dataFetcher.fetchStarships()
    }
}