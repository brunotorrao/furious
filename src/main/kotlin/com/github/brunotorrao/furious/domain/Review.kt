package com.github.brunotorrao.furious.domain

import org.springframework.data.annotation.Id

data class Review (
    @Id
    val id: Long = 0L,
    val movieId: Long = 0L,
    val customerId: Long,
    val rating: Int
)
