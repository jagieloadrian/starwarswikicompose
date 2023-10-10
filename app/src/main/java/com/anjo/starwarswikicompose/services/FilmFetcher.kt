package com.anjo.starwarswikicompose.services

import com.anjo.GetAllFilmsQuery
import com.apollographql.apollo3.ApolloClient
import kotlinx.coroutines.runBlocking


class FilmFetcher(
    private val apolloClient: ApolloClient
) : IFilmFetcher {
    override fun fetchFilms(): GetAllFilmsQuery.AllFilms? = runBlocking {
        val getFilms = apolloClient.query(query = GetAllFilmsQuery()).execute()
        getFilms.data?.allFilms
    }
}