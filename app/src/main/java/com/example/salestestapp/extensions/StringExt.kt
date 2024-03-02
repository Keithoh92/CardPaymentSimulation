package com.example.salestestapp.extensions

const val SEAT_NUMBER_REGEX = "[^A-Za-z0-9]+|(?<=[A-Za-z])(?=[0-9])|(?<=[0-9])(?=[A-Za-z])"

fun String?.splitToSeatNumber(crewSeats: String? = null): Int {
    if (this == null || this == "null" || this == "CREW") return 0

    crewSeats?.split(",")?.find { it == this }?.run { return 0 }
    val splitResult = this.split(SEAT_NUMBER_REGEX.toRegex())
    val numberString = splitResult.firstOrNull() ?: return 0

    return numberString.toIntOrNull() ?: 0
}

fun String?.splitToSeatLetter(crewSeats: String? = null): String {
    if (this == null || this == "null" || this == "CREW") return ""

    crewSeats?.split(",")?.find { it == this }?.run { return this@splitToSeatLetter }
    val splitResult = this.split(SEAT_NUMBER_REGEX.toRegex())
    val letter = splitResult.lastOrNull() ?: ""

    return if (letter.all { it.isDigit() }) "" else letter
}