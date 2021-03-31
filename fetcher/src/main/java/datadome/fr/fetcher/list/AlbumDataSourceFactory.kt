package datadome.fr.fetcher.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import datadome.fr.fetcher.FetcherInfos
import datadome.fr.fetcher.data.AlbumModel
import datadome.fr.fetcher.list.AlbumDataSource
import datadome.fr.fetcher.network.ClientService
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class AlbumDataSourceFactory(private val albumClientService: ClientService): DataSource.Factory<Int, AlbumModel>() {
    val dataSource  = AlbumDataSource(albumClientService)
    var dataSourceMutableLiveData = MutableLiveData<AlbumDataSource>()
    var sentInfosResponseMutableLiveData = dataSource.sendingSuccess.flatMap {
        Observable.just(it)
    }

    override fun create(): DataSource<Int, AlbumModel> {
        dataSourceMutableLiveData.postValue(dataSource)
        return dataSource
    }

    fun sentAppInfosStatus(appInfos: FetcherInfos.AppInfos) {
        dataSource.sendAlbumApplicationInfos(appInfos)
    }
}