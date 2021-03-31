package datadome.fr.fetcher

import android.view.MotionEvent
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import datadome.fr.fetcher.list.FetcherViewModel.Companion.User
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ScrollDetector(val recyclerView: RecyclerView) {

    var isScrolling: PublishSubject<Boolean> = PublishSubject.create()

    init {
        isScrolling.onNext(false)
    }

    // calculate time interval between every scroll movement
    fun detectScrolling() {
        recyclerView.setOnTouchListener { view, motionEvent ->
            val action = motionEvent.action
            if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN){
                calculateInterval()
                isScrolling.onNext(true)
                return@setOnTouchListener true
            }
            return@setOnTouchListener false
        }
    }

    private val moveIntervals = mutableListOf<Long>()
    private var lastMove = -1L
    private fun calculateInterval() {
        val currentTime = System.currentTimeMillis()
        if(lastMove < 0) {
            moveIntervals.add(currentTime)
        } else {
            moveIntervals.add(currentTime - lastMove)
        }
        lastMove = currentTime
    }

    // if the interval between every interaction is equal, than user is probably is a bot
    fun detectUserType(): User {
        var index = 1
        if(moveIntervals.size > 1) {
            while(index <= moveIntervals.size && moveIntervals[index]== moveIntervals[index-1]) {
                index++
            }
            if(index > moveIntervals.size)
                return User.BOT
            else
                return User.HUMAIN
        }
        return User.UNKNOWN
    }

}
