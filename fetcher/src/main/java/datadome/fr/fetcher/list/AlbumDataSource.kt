package datadome.fr.fetcher.list

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import datadome.fr.fetcher.FetcherInfos
import datadome.fr.fetcher.data.AlbumModel
import datadome.fr.fetcher.data.StatusModel
import datadome.fr.fetcher.network.ClientService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject


class AlbumDataSource(private val albumClientService: ClientService): PageKeyedDataSource<Int, AlbumModel>() {
    var listAlbum = listOf<AlbumModel>()
    var fetcherStatus = MutableLiveData<StatusModel>()
    var sendingSuccess = BehaviorSubject.create<Boolean>()
    private var ALBUM_ID = 1

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, AlbumModel>
    ) {
        fetcherStatus.postValue(StatusModel.LOADING)
        albumClientService.getPhotoList(ALBUM_ID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    callback.onResult(it, null, ALBUM_ID+1)
                    fetcherStatus.postValue(StatusModel.SUCCESS)
                },
                {
                    fetcherStatus.postValue(StatusModel.FAIL)
                }
            )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, AlbumModel>) {
        fetcherStatus.postValue(StatusModel.LOADING)
        albumClientService.getPhotoList(params.key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    callback.onResult(it, params.key+1)
                    fetcherStatus.postValue(StatusModel.SUCCESS)
                },
                {
                    fetcherStatus.postValue(StatusModel.FAIL)
                }
            )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, AlbumModel>) {
    }

    fun sendAlbumApplicationInfos(appInfos: FetcherInfos.AppInfos) {
        mockApiSendInfos(appInfos)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { sentStatus ->
                    sendingSuccess.onNext(sentStatus)
                },
                {
                    sendingSuccess.onNext(false)
                }
            )
    }

    fun mockApiSendInfos(appInfos: FetcherInfos.AppInfos) = Single.just(true)
}