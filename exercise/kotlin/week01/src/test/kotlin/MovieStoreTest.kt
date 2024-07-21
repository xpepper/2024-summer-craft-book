import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import movie.Movie
import movie.Movie.MovieId
import movie.Movie.MovieTitle
import movie.MovieStore

class MovieStoreTest : StringSpec({
    lateinit var store: MovieStore

    beforeTest {
        store = MovieStore()
        store.addMovie(MovieId("001"), MovieTitle("Inception"), "Christopher Nolan", 10, 0.0)
        store.addMovie(MovieId("002"), MovieTitle("The Matrix"), "Lana Wachowski, Lilly Wachowski", 8, 0.0)
        store.addMovie(MovieId("003"), MovieTitle("Dunkirk"), "Christopher Nolan", 5, 0.0)
    }

    "testWhenCannotBuyMovieItsTotalCopiesShouldNotChange" {
        store.addMovie(MovieId("004"), MovieTitle("Any title"), "anything", 1, 0.0)
        store.allMovies["004"]?.totalCopies shouldBe 1

        store.buyMovie("any customer", "004")

        store.allMovies["004"]?.totalCopies shouldBe 1
    }

    "testAddMovie" {
        store.addMovie(MovieId("002"), MovieTitle("The Matrix"), "Lana Wachowski, Lilly Wachowski", 8, 0.0)
        store.allMovies["002"]?.totalCopies shouldBe 8
    }

    "testRemoveMovie" {
        store.removeMovie("001")
        store.allMovies["001"] shouldBe null
    }

    "testBorrowMovie" {
        store.borrowMovie("001")
        store.allMovies["001"]?.borrowedCopies shouldBe 1
    }

    "testBuyMovie" {
        store.addMovie(MovieId("005"), MovieTitle("Any title"), "anything", 2, 10.0)

        store.buyMovie("any customer", "005")

        store.allMovies["005"]?.totalCopies shouldBe 1
    }

    "testReturnMovie" {
        store.returnMovie("001")
        store.allMovies["001"]?.borrowedCopies shouldBe 0
    }

    "testFindMoviesByTitle" {
        val movies = store.findMoviesByTitle("Inception")
        movies shouldHaveSize 1
        movies[0].title shouldBe "Inception"
    }
})
