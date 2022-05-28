package ru.mirea.medlib.database

import androidx.room.TypeConverter
import ru.mirea.medlib.network.dto.Country
import ru.mirea.medlib.network.dto.Genre

class CountryConverter {

    @TypeConverter
    fun fromCountryList(countryList: List<Country>): String {
        return countryList.joinToString(separator = ";")
    }

    @TypeConverter
    fun toCountryList(countryListString: String): List<Country> {
        return countryListString.split(";").map { it -> Country(it) }
    }
}

class GenreConverter {

    @TypeConverter
    fun fromCountryList(countryList: List<Genre>): String {
        return countryList.joinToString(separator = ";")
    }

    @TypeConverter
    fun toCountryList(countryListString: String): List<Genre> {
        return countryListString.split(";").map { it -> Genre(it) }
    }
}