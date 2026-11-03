package br.com.bjjtracker.data.local

import androidx.room.TypeConverter
import br.com.bjjtracker.domain.model.TechnicsEnum
import java.time.LocalDate

class Converters {

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? = date?.toString()

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let(LocalDate::parse)

    @TypeConverter
    fun fromTechnicsEnum(technic: TechnicsEnum?): String? = technic?.name

    @TypeConverter
    fun toTechnicsEnum(value: String?): TechnicsEnum? = value?.let(TechnicsEnum::valueOf)
}