package com.github.brunotorrao.furious.domain.exceptions

sealed class MovieException {
    object MovieNotFoundException: MovieException()
}
