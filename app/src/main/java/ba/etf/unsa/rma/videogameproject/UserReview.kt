package ba.etf.unsa.rma.videogameproject

data class UserReview(
    override val userName: String,
    override val timestamp: Long,
    val review: String
): UserImpression(userName, timestamp)
