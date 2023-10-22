package com.anjo.starwarswikicompose.services.usecases.person

import com.anjo.GetPersonQuery
import com.anjo.starwarswikicompose.data.Repository
import javax.inject.Inject

class GetPersonUseCase@Inject constructor(
        private val dataFetcher: Repository
) {
    suspend operator fun invoke(id:String): GetPersonQuery.Person? {
        return dataFetcher.fetchOnePerson(id)
    }
}