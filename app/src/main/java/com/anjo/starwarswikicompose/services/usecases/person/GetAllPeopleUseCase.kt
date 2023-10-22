package com.anjo.starwarswikicompose.services.usecases.person

import com.anjo.GetAllPeoplesQuery
import com.anjo.starwarswikicompose.data.Repository
import javax.inject.Inject

class GetAllPeopleUseCase @Inject constructor(
        private val repository: Repository
) {
    suspend operator fun invoke(): List<GetAllPeoplesQuery.Person?>? {
        return repository.fetchPeoples()
    }
}