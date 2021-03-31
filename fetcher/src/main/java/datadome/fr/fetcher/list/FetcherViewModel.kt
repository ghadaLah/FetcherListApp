package datadome.fr.fetcher.list

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import datadome.fr.fetcher.FetcherInfos
import datadome.fr.fetcher.ScrollDetector
import datadome.fr.fetcher.data.AlbumModel
import datadome.fr.fetcher.data.StatusModel
import datadome.fr.fetcher.quizz.QuizzBox


class FetcherViewModel(private val activity: Activity, private val recyclerView: RecyclerView): ViewModel(), AppFetcherInterface {
    private val fetchRepository: FetchRepository =
        FetchRepository()
    val pagedAlbumList: LiveData<PagedList<AlbumModel>> by lazy { fetchRepository.loadAlbumList() }
    val fetchStatus: LiveData<StatusModel> by lazy { fetchRepository.getFetchStatus()}
    val sentInfosResponse: LiveData<Boolean> by lazy { fetchRepository.sentInfosResponseLiveData }

    var scrollUserTypeLiveData = MutableLiveData<User>()
    var quizzUserTypeLiveData = MutableLiveData<User>()
    private val scrollDetector =  ScrollDetector(recyclerView)
    private val quizzBox =  QuizzBox(activity)

    companion object {
        enum class User {
            HUMAIN, BOT, UNKNOWN
        }
    }

    init {
        scrollDetector.detectScrolling()
        upfateScrollUserType()
        sendAppInfos()
    }

    override fun sendAppInfos() {
        val appInfos = FetcherInfos(activity).getAppInfos()
        Log.d("fetcherinfos", "appfetch has $appInfos")
        fetchRepository.sendApplicationInfos(appInfos)
    }

    override fun upfateScrollUserType() {
        // subscribe to a scroll observable, and update scrollUserTypeLiveData value
        scrollDetector.isScrolling.subscribe{
            scrollUserTypeLiveData.postValue(scrollDetector.detectUserType())
        }
    }

    override fun updateQuizzUserType() {
        val listQuizz = QuizzBox(activity.baseContext).quizzQuestions()
        quizzBox.showQuizz(listQuizz.random())
        quizzBox.type.subscribe {
            quizzUserTypeLiveData.postValue(it)
        }
    }
}

interface AppFetcherInterface {
    fun sendAppInfos()
    fun upfateScrollUserType()
    fun updateQuizzUserType()
}