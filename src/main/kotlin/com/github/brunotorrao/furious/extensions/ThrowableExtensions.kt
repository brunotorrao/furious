package com.github.brunotorrao.furious.extensions

import org.springframework.dao.DataIntegrityViolationException

fun Throwable.isConflict() : Boolean {
    return this is DataIntegrityViolationException && this.message.orEmpty().contains ("Unique index or primary key violation")
}
