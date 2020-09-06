package grandi.bambino.mynote.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
//data генерирует геттеры и сеттеры
data class Note(
    val id: String = "",
    val title: String = "",
    val text: String = "",
    val color: Color = Color.WHITE,
    val lastChanged: Date = Date()
) : Parcelable {


    //переопределяем метод equals, что бы сравнивать Note по id
    override fun equals(other: Any?): Boolean {
        if (this === other) return true //=== для сравнивания ссылок, == позволяет сравниввать объекты
        if (javaClass != other?.javaClass) return false

        other as Note

        if (id != other.id) return false

        return true
    }

    enum class Color{
        WHITE,
        YELLOW,
        GREEN,
        BLUE,
        RED,
        VIOLET,
        PINK
    }
}

