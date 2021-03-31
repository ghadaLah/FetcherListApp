package datadome.fr.fetcher.data

import android.provider.Contacts.Photos
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName


data class AlbumModel(
    @SerializedName("albumId")
    val albumId:Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String
)