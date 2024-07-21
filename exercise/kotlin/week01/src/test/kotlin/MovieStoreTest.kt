import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import movie.Movie
import movie.Movie.MovieId
import movie.Movie.MovieTitle
import movie.MovieStore

private val id = anyMovieId()

class MovieStoreTest : StringSpec({
    val store = MovieStore()

    beforeTest {
        store.allMovies.forEach { store.removeMovie(it.key) }
    }

    "testWhenCannotBuyMovieItsTotalCopiesShouldNotChange" {
        val freeMovie = Movie(anyMovieId(), MovieTitle(randomString()).value, randomString(), 1, 0.0)

        store.addMovie(freeMovie)
        store.allMovies[freeMovie.movieId.value]?.totalCopies shouldBe 1

        store.buyMovie("any customer", freeMovie.movieId.value)

        store.allMovies[freeMovie.movieId.value]?.totalCopies shouldBe 1
    }

    "testAddMovie" {
        val movie = anyMovie()

        store.addMovie(movie)

        store.allMovies[movie.movieId.value] shouldBe movie
    }

    "testRemoveMovie" {
        val movie = anyMovie()
        store.addMovie(movie)

        store.removeMovie(movie.movieId.value)

        store.allMovies[movie.movieId.value] shouldBe null
    }

    "testBorrowMovie" {
        val movie = anyMovie()
        store.addMovie(movie)

        store.borrowMovie(movie.movieId.value)

        store.allMovies[movie.movieId.value]?.borrowedCopies shouldBe 1
    }

    "testBuyMovie" {
        val movie = anyMovie(totalCopies = 2)
        store.addMovie(movie)

        store.buyMovie("any customer", movie.movieId.value)

        store.allMovies[movie.movieId.value]?.totalCopies shouldBe 1
    }

    "testReturnMovie" {
        val movie = anyMovie()
        store.addMovie(movie)

        store.returnMovie(movie.movieId.value)
        store.allMovies[movie.movieId.value]?.borrowedCopies shouldBe 0
    }

    "testFindMoviesByTitle" {
        val title = MovieTitle("Inception")
        store.addMovie(Movie(anyMovieId(), title.value, "Christopher Nolan", 10, 1.0))

        val movies = store.findMoviesByTitle(title.value)

        movies shouldHaveSize 1
        movies[0].title shouldBe "Inception"
    }
})

private fun anyMovie(totalCopies: Int = 1) =
    Movie(
        movieId = anyMovieId(),
        title = MovieTitle(randomString()).value,
        director = randomString(),
        totalCopies = totalCopies,
        unitPrice = 10.0
    )

private fun anyMovieId() = MovieId(randomString())

fun randomString(): String = (1..8).map { ('a'..'z').random() }.joinToString("")
