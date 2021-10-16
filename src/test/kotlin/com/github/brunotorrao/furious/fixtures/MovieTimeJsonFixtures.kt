package com.github.brunotorrao.furious.fixtures

fun jsonForAllMovieTimes() = """
                [
                  {
                    "id": 1,
                    "movieId": 1,
                    "price": 15.6,
                    "date": "15/10/2021 20:30"
                  }
                ]
            """.trimIndent()


fun jsonForMovieCreated() = """
                  {
                    "id": 2,
                    "movieId": 1,
                    "price": 15.6,
                    "date": "15/10/2021 20:45"
                  }
            """.trimIndent()

fun jsonForMovieConflict() = """
                  {
                    "id": 1,
                    "movieId": 1,
                    "price": 15.6,
                    "date": "15/10/2021 20:30"
                  }
            """.trimIndent()

fun jsonForUpdatePriceAndDateTime() = """
                  {
                    "price": 19.5,
                    "date": "15/10/2021 20:50"
                  }
            """.trimIndent()

fun jsonForUpdatePriceAndDateTimeResult() = """
                  {
                    "id": 1,
                    "movieId": 1,
                    "price": 19.5,
                    "date": "15/10/2021 20:50"
                  }
            """.trimIndent()
