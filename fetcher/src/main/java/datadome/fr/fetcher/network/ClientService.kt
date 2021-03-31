package datadome.fr.fetcher.network

import datadome.fr.fetcher.FetcherInfos
import datadome.fr.fetcher.data.AlbumModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ClientService {
    @GET("photos")
    fun getPhotoList(@Query("albumId") albumId: Int): Single<List<AlbumModel>>

//    @POST
//    fun sendAppInfos(@Query("infos") infos: FetcherInfos.AppInfos): Single<Boolean> = Single.just(true)
}