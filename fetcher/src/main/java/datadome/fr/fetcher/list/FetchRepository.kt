package datadome.fr.fetcher.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import datadome.fr.fetcher.FetcherInfos
import datadome.fr.fetcher.data.AlbumModel
import datadome.fr.fetcher.data.StatusModel
import datadome.fr.fetcher.network.ClientService
import datadome.fr.fetcher.utils.BASE_URL
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FetchRepository {
    lateinit var pagedAlbumList: LiveData<PagedList<AlbumModel>>
    var sentInfosResponseLiveData = MutableLiveData<Boolean>()
    var albumDataSourceFactory: AlbumDataSourceFactory
    private var albumClientService: ClientService

    companion object {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    init {
        albumClientService = retrofit.create(ClientService::class.java)
        albumDataSourceFactory =
            AlbumDataSourceFactory(albumClientService)
        albumDataSourceFactory.sentInfosResponseMutableLiveData.subscribe{ sentInforesponse ->
            sentInfosResponseLiveData.postValue(sentInforesponse)
        }
    }

    fun loadAlbumList(): LiveData<PagedList<AlbumModel>> {
        val dataSourceMutableLiveData = albumDataSourceFactory.dataSourceMutableLiveData

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(50)
            .build()

        pagedAlbumList = LivePagedListBuilder(albumDataSourceFactory, config)
            .build()

        return pagedAlbumList
    }

    fun getFetchStatus(): LiveData<StatusModel> =
         Transformations.switchMap(albumDataSourceFactory.dataSourceMutableLiveData, AlbumDataSource::fetcherStatus)

    fun sendApplicationInfos (appInfos: FetcherInfos.AppInfos) = albumDataSourceFactory.sentAppInfosStatus(appInfos)
}