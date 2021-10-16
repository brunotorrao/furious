package com.github.brunotorrao.furious.domain

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.format.annotation.DateTimeFormat
import java.math.BigDecimal
import java.time.LocalDateTime


data class MovieTimeUpdate (
    val price: BigDecimal,
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    val date: LocalDateTime
)
