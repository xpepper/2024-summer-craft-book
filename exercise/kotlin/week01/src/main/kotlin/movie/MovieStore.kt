package movie

import movie.Movie.MovieId
import movie.Movie.MovieTitle

class MovieStore {
    private val movies: HashMap<String, Movie> = HashMap()
    private val sales: StoreAccount = StoreAccount()

    val allMovies get() = HashMap(movies)

    fun buyMovie(customer: String, id: String) { // customer could be a value object (smell: primitive obsession)
        val movie = movies[id]
        if (movie == null) {
            println("Movie not available!")
            return
        }

        if (movie.totalCopies - movie.borrowedCopies <= 0) {
            println("All copies are currently borrowed.")
            return
        }
        if (!movie.canSell()) {
            println("Movie not for sale")
            return
        }

        movie.totalCopies--
        sales.sell(movie, customer)
    }

    fun addMovie(movie: Movie) {
        if (movies.containsKey(movie.movieId.value)) {
            println("Movie already exists! Updating total copies.")
            updateMovieCopies(movie.movieId.value, movie.totalCopies)
        } else {
            movies[movie.movieId.value] =
                Movie(movie.movieId, MovieTitle(movie.title).value, movie.director, movie.totalCopies, movie.unitPrice)
        }
    }

    private fun updateMovieCopies(id: String, newTotalCopies: Int) {
        val movie = movies[id]
        if (movie == null) {
            println("Movie not found!")
            return
        }

        if (newTotalCopies < movie.borrowedCopies) {
            println("Cannot reduce total copies below borrowed copies.")
        } else {
            movie.totalCopies = newTotalCopies
        }
    }

    fun removeMovie(id: String) {
        if (!movies.containsKey(id)) {
            println("Movie not found!")
            return
        }

        movies.remove(id)
    }

    fun borrowMovie(id: String) {
        val movie = movies[id]
        if (movie == null) {
            println("Movie not available!")
            return
        }

        if (movie.totalCopies - movie.borrowedCopies <= 0) {
            println("All copies are currently borrowed.")
            return
        }

        movie.borrowedCopies++
    }

    fun returnMovie(id: String) {
        val movie = movies[id]
        if (movie == null) {
            println("Invalid movie ID!")
            return
        }

        if (movie.borrowedCopies <= 0) {
            println("Error: No copies were borrowed.")
            return
        }

        movie.borrowedCopies--
    }

    fun displayMovies() {
        for ((_, m) in movies) {
            println("ID: ${m.movieId}, Title: ${m.title}, Director: ${m.director}, Available Copies: ${m.totalCopies - m.borrowedCopies}")
        }
    }

    fun findMoviesByTitle(title: String): List<Movie> =
        movies.values.filter { it.title.equals(title, ignoreCase = true) }.toList()
}
