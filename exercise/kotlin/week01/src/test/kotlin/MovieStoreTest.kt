import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import movie.Movie.MovieId
import movie.Movie.MovieTitle
import movie.MovieStore

class MovieStoreTest : StringSpec({
    lateinit var store: MovieStore

    beforeTest {
        store = MovieStore()
    }

    "testWhenCannotBuyMovieItsTotalCopiesShouldNotChange" {
        val id = anyMovieId()
        store.addMovie(id, MovieTitle("Any title"), "anything", 1, 0.0)
        store.allMovies[id.value]?.totalCopies shouldBe 1

        store.buyMovie("any customer", "004")

        store.allMovies[id.value]?.totalCopies shouldBe 1
    }

    "testAddMovie" {
        val id = anyMovieId()
        store.addMovie(id, MovieTitle("The Matrix"), "Lana Wachowski, Lilly Wachowski", 8, 0.0)
        store.allMovies[id.value]?.totalCopies shouldBe 8
    }

    "testRemoveMovie" {
        val id = anyMovieId()
        store.addMovie(id, MovieTitle("Inception"), "Christopher Nolan", 10, 1.0)

        store.removeMovie(id.value)
        store.allMovies[id.value] shouldBe null
    }

    "testBorrowMovie" {
        val id = anyMovieId()
        store.addMovie(id, MovieTitle("Inception"), "Christopher Nolan", 10, 1.0)

        store.borrowMovie(id.value)
        store.allMovies[id.value]?.borrowedCopies shouldBe 1
    }

    "testBuyMovie" {
        val id = anyMovieId()
        store.addMovie(id, MovieTitle("Any title"), "anything", 2, 10.0)

        store.buyMovie("any customer", id.value)

        store.allMovies[id.value]?.totalCopies shouldBe 1
    }

    "testReturnMovie" {
        val id = anyMovieId()
        store.addMovie(id, MovieTitle("Inception"), "Christopher Nolan", 10, 1.0)

        store.returnMovie(id.value)
        store.allMovies[id.value]?.borrowedCopies shouldBe 0
    }

    "testFindMoviesByTitle" {
        val title = MovieTitle("Inception")
        store.addMovie(anyMovieId(), title, "Christopher Nolan", 10, 1.0)

        val movies = store.findMoviesByTitle(title.value)

        movies shouldHaveSize 1
        movies[0].title shouldBe "Inception"
    }
})

private fun anyMovieId() = MovieId(randomString())

fun randomString(): String = (1..8).map { ('a'..'z').random() }.joinToString("")
