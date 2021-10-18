package com.github.brunotorrao.furious.extensions

import org.springframework.dao.DataIntegrityViolationException

fun Throwable.isConflict() : Boolean {
    return this is DataIntegrityViolationException && this.message.orEmpty().contains ("duplicate key")
}

fun Throwable.foreignKeyMissing(foreignKey: String) =
    this is DataIntegrityViolationException && this.message.orEmpty().contains ("violates foreign key", true) &&
        this.message.orEmpty().contains (foreignKey, true)
