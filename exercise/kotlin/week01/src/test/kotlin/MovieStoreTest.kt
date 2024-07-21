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
        store.addMovie(Movie(id, MovieTitle("Inception").value, "Christopher Nolan", 10, 1.0))

        store.removeMovie(id.value)
        store.allMovies[id.value] shouldBe null
    }

    "testBorrowMovie" {
        store.addMovie(Movie(id, MovieTitle("Inception").value, "Christopher Nolan", 10, 1.0))

        store.borrowMovie(id.value)
        store.allMovies[id.value]?.borrowedCopies shouldBe 1
    }

    "testBuyMovie" {
        store.addMovie(Movie(id, MovieTitle("Any title").value, "anything", 2, 10.0))

        store.buyMovie("any customer", id.value)

        store.allMovies[id.value]?.totalCopies shouldBe 1
    }

    "testReturnMovie" {
        store.addMovie(Movie(id, MovieTitle("Inception").value, "Christopher Nolan", 10, 1.0))

        store.returnMovie(id.value)
        store.allMovies[id.value]?.borrowedCopies shouldBe 0
    }

    "testFindMoviesByTitle" {
        val title = MovieTitle("Inception")
        store.addMovie(Movie(anyMovieId(), title.value, "Christopher Nolan", 10, 1.0))

        val movies = store.findMoviesByTitle(title.value)

        movies shouldHaveSize 1
        movies[0].title shouldBe "Inception"
    }
})

private fun anyMovie() = Movie(anyMovieId(), MovieTitle(randomString()).value, randomString(), 1, 10.0)

private fun anyMovieId() = MovieId(randomString())

fun randomString(): String = (1..8).map { ('a'..'z').random() }.joinToString("")
