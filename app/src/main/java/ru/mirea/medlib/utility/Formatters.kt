package ru.mirea.medlib.utility

import ru.mirea.medlib.network.dto.Genre

object Formatters {
    fun formattedGenres(genres: List<Genre>): String = genres.joinToString(", ") { it.genre }
}