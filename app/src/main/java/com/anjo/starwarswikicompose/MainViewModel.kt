package com.anjo.starwarswikicompose

import androidx.lifecycle.ViewModel
import com.anjo.starwarswikicompose.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {

    val films = repository.getFilms().toString()

}
