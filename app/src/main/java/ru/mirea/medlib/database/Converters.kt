package ru.mirea.medlib.database

import androidx.room.TypeConverter
import ru.mirea.medlib.network.dto.Country
import ru.mirea.medlib.network.dto.Genre

class CountryConverter {

    @TypeConverter
    fun fromCountryList(countryList: List<Country>): String =
        countryList.joinToString(";") { it.country }

    @TypeConverter
    fun toCountryList(countryListString: String): List<Country> =
        countryListString.split(";").map { Country(it) }
}

class GenreConverter {

    @TypeConverter
    fun fromGenreList(genreList: List<Genre>): String =
        genreList.joinToString(";") { it.genre }

    @TypeConverter
    fun toGenreList(genreListString: String): List<Genre> =
        genreListString.split(";").map { Genre(it) }
}