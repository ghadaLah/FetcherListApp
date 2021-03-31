package datadome.fr.fetcher.quizz

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.util.Log
import datadome.fr.fetcher.list.FetcherViewModel.Companion.User
import datadome.fr.fetcher.list.FetcherViewModel.Companion.User.*
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class QuizzBox(val mContext: Context): Dialog(mContext) {
    var type = PublishSubject.create<User>()

    fun quizzQuestions(): List<QuizzModel> {
        val question1 = QuizzModel(
            "If you were to appoint a president of the internet, who would it be and why?",
            arrayOf("me", "you", "the boss"),
            "the boss"
        )
        val question2 = QuizzModel(
            "Name the coolest mouse?",
            arrayOf("Gerry", "Tom", "mouse"),
            "Gerry"
        )
        val question3 = QuizzModel(
            "Was the first person to test bulletproof vests suicidal?",
            arrayOf(
                "We never knew, he's dead ...",
                "If you are able to talk to a model, you can ask her"
            ),
            "If you are able to talk to a model, you can ask her"
        )
        val question4 = QuizzModel(
            "Why did Cinderella lose her shoe when it was exactly her size?",
            arrayOf(
                "Because she sweats a lot on her feet",
                "Probably because she was running down the stairs in high heels. Very difficult to achieve.",
                "Because the author has decided so."
            ),
            "Because the author has decided so."
        )
        val question5 = QuizzModel(
            "What is the major difference between a bird and a fly?",
            arrayOf("because birds believe they can fly", "A Bird can fly but a fly cannot bird!"),
            "A Bird can fly but a fly cannot bird!"
        )
        val question6 = QuizzModel(
            "Which of the following are astronauts not permitted to eat before going into space?",
            arrayOf("Peanut butter", "Seafood", "Beans"),
            "Seafood"
        )
        return listOf(question1, question2, question3, question4, question5, question6)
    }

    fun showQuizz(quizzTest: QuizzModel){
        var usertype = UNKNOWN
        val quizz = AlertDialog.Builder(mContext)
            .setTitle(quizzTest.question)
            .setSingleChoiceItems(
                quizzTest.hints,
                0
            ) { dialog, which ->
                if(quizzTest.hints[which] == quizzTest.answer)
                    usertype = HUMAIN
                else
                    usertype = BOT
            }
            .setPositiveButton("Submit") {_, _ ->
                type.onNext(usertype)
                dismiss()
            }
        quizz.create().show()
    }

    data class QuizzModel(
        val question: String,
        val hints: Array<CharSequence>,
        val answer: String
    )
}