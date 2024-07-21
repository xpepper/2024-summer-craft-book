package movie

import movie.Movie.MovieTitle

class MovieStore {
    private val movies: HashMap<String, Movie> = HashMap()
    private val sales: StoreAccount = StoreAccount()

    val allMovies get(): Map<String, Movie> = HashMap(movies)

    private fun getMovie(id: String): Movie? = movies[id] ?: run { println("Movie not found!"); null }

    fun buyMovie(customer: String, id: String) { // customer could be a value object (smell: primitive obsession)
        val movie = getMovie(id) ?: return

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
        val movie = getMovie(id) ?: return

        if (newTotalCopies < movie.borrowedCopies) {
            println("Cannot reduce total copies below borrowed copies.")
        } else {
            movie.totalCopies = newTotalCopies
        }
    }

    fun removeMovie(id: String) {
        getMovie(id)?.let {
            movies.remove(it.movieId.value)
        }
    }

    fun borrowMovie(id: String) {
        val movie = getMovie(id) ?: return

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
