package datadome.fr.fetcherlistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import datadome.fr.fetcher.data.StatusModel
import datadome.fr.fetcher.list.FetcherViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var adapter: AlbumAdapter
    var shouldStopUser = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpAdapter()
    }

    override fun onResume() {
        super.onResume()
        setObservables()
    }

    fun setObservables() {
        val appFetcher = FetcherViewModel(this, recyclerView)
        appFetcher.pagedAlbumList.observe(this, Observer {
            adapter.submitList(it)
        })
        appFetcher.fetchStatus.observe(this, Observer {
            when(it) {
                StatusModel.LOADING -> progressBar.visibility = View.VISIBLE
                StatusModel.SUCCESS -> progressBar.visibility = View.GONE
                StatusModel.FAIL -> progressBar.visibility = View.GONE
            }
        })
        // shows an alert box with random questions
        dialogBtn.setOnClickListener {
            appFetcher.updateQuizzUserType()
        }
        appFetcher.scrollUserTypeLiveData.observe(this, Observer { userType ->
            if(userType == FetcherViewModel.Companion.User.BOT)
                shouldStopUser = true
        })
        appFetcher.quizzUserTypeLiveData.observe(this, Observer { userType ->
            if(userType == FetcherViewModel.Companion.User.BOT)
                shouldStopUser = true
        })
        appFetcher.sentInfosResponse.observe(this, Observer {
            // we could compare the received data with received previously, if that characteristics are recurrent than stop user
        })
    }

    fun setUpAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AlbumAdapter()
        recyclerView.adapter = adapter
    }
}