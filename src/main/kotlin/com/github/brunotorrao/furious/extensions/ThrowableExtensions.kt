package com.github.brunotorrao.furious.extensions

import org.springframework.dao.DataIntegrityViolationException

fun Throwable.isConflict() : Boolean {
    return this is DataIntegrityViolationException && this.message.orEmpty().contains ("Unique index or primary key violation")
}

fun Throwable.foreignKeyMissing(foreignKey: String) =
    this is DataIntegrityViolationException && this.message.orEmpty().contains ("Referential integrity", true) &&
        this.message.orEmpty().contains (foreignKey, true)
