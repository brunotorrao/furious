package com.github.brunotorrao.furious.domain

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModelProperty
import org.springframework.data.annotation.Id
import org.springframework.format.annotation.DateTimeFormat
import java.math.BigDecimal
import java.time.LocalDateTime

data class MovieTime(
    @Id
    val id: Long = 0,
    val movieId: Long,
    val price: BigDecimal,
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    @ApiModelProperty(example = "25/06/2021 15:45")
    val date: LocalDateTime
)
