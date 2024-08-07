package games

object FizzBuzz {
    fun convert(input: Int): String {
        if (input <= 0 || input > 100) {
            throw OutOfRangeException()
        }

        return when {
            input % 3 == 0 && input % 5 == 0 -> "FizzBuzz"
            input % 3 == 0 -> "Fizz"
            input % 5 == 0 -> "Buzz"

            else -> input.toString()
        }
    }
}

