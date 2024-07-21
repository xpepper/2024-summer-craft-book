import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import movie.MovieStore

class MovieStoreTest : StringSpec({
    lateinit var store: MovieStore

    beforeTest {
        store = MovieStore()
        store.addMovie("001", "Inception", "Christopher Nolan", 10, 0.0)
        store.addMovie("002", "The Matrix", "Lana Wachowski, Lilly Wachowski", 8, 0.0)
        store.addMovie("003", "Dunkirk", "Christopher Nolan", 5, 0.0)
    }

    "testWhenCannotBuyMovieItsTotalCopiesShouldNotChange" {
        store.addMovie("004", "Any title", "anything", 1, 0.0)
        store.allMovies["004"]?.totalCopies shouldBe 1

        store.buyMovie("any customer", "004")

        store.allMovies["004"]?.totalCopies shouldBe 1
    }

    "testAddMovie" {
        store.addMovie("002", "The Matrix", "Lana Wachowski, Lilly Wachowski", 8, 0.0)
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
        val movie = store.allMovies["001"]
        movie?.unitPrice = 5.0

        store.buyMovie("Durant", "001")
        store.allMovies["001"]?.totalCopies shouldBe 9
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
