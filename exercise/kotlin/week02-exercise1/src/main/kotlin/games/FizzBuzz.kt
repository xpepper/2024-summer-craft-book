package games

object FizzBuzz {
    fun convert(input: Int): String {
        if (input <= 0 || input > 100) {
            throw OutOfRangeException()
        }

        if (input % 3 == 0 && input % 5 == 0) {
            return "FizzBuzz"
        }
        if (input % 3 == 0) {
            return "Fizz"
        }
        if (input % 5 == 0) {
            return "Buzz"
        }

        return input.toString()
    }
}

