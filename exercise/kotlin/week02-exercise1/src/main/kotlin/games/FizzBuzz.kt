package games

object FizzBuzz {
    fun convert(input: Int): String {
        if (input <= 0 || input > 100) {
            throw OutOfRangeException()
        }

        return when {
            input.divisibleBy(5*3) -> "FizzBuzz"
            input.divisibleBy(3) -> "Fizz"
            input.divisibleBy(5) -> "Buzz"

            else -> input.toString()
        }
    }

    private fun Int.divisibleBy(number: Int) = this % number == 0
}

