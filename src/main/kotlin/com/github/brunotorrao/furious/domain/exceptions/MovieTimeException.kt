package com.github.brunotorrao.furious.domain.exceptions

sealed class MovieTimeException {
    object MovieTimeConflict: MovieTimeException()
    object MovieTimeGenericException: MovieTimeException()
    object MovieTimeNotFoundException: MovieTimeException()
}
