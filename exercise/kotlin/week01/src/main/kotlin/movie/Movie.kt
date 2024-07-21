package movie

data class Movie(
    val movieId: MovieId,
    val title: String,
    val director: String,
    var totalCopies: Int,
    val unitPrice: Double
) {
    var borrowedCopies: Int = 0

    fun canSell(): Boolean {
        return unitPrice != 0.0
    }

    data class MovieId(val value: String)
    data class MovieTitle(val value: String)
}

