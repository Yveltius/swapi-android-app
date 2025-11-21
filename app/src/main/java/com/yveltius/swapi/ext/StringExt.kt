package com.yveltius.swapi.ext

fun String.upperFirstChar(): String {
    return if (this.isNotEmpty() && this.first().isLetter() && this.first().isLowerCase()) {
        this.replaceFirstChar { it.uppercaseChar() }
    } else {
        this
    }
}

fun List<String>.buildSeparatedString(separator: Char): String {
    val stringBuilder = StringBuilder()
    this.forEach {
        stringBuilder.append(it)
    }

    return stringBuilder.toString()
}