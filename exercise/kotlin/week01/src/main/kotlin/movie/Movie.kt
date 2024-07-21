package movie

class Movie(
    val movieID: String,
    val title: String,
    val director: String,
    var totalCopies: Int,
    val unitPrice: Double
) {
    var borrowedCopies: Int = 0

    fun canSell(): Boolean {
        return unitPrice != 0.0
    }
}
