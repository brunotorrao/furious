package com.github.brunotorrao.furious.fixtures

fun jsonForReviews() = """
    [
      {
        "id": 1,
        "movieId": 1,
        "customerId": 1,
        "rating": 5
      }
    ]
""".trimIndent()

fun jsonForReview() = """
    {
        "customerId": 1,
        "rating": 5
    }
""".trimIndent()

fun jsonForReviewCreated() = """
    {
        "id": 1,
        "movieId": 1,
        "customerId": 1,
        "rating": 5
    }
""".trimIndent()
