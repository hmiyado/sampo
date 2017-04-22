package com.hmiyado.sampo.view.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.appKodein
import com.hmiyado.sampo.controller.map.ScoreViewController
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.UseScoreView
import com.hmiyado.sampo.usecase.result.interaction.ResultSummaryTag
import com.hmiyado.sampo.usecase.result.interaction.resultSummaryUseCaseModule
import com.hmiyado.sampo.view.result.ui.ResultSummaryFragmentUi
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.RxFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

/**
 * Created by hmiyado on 2017/03/01.
 */
class ResultSummaryFragment : RxFragment(), LazyKodeinAware {
    override val kodein: LazyKodein = LazyKodein {
        Kodein {
            extend(appKodein())
            import(resultSummaryUseCaseModule)

            bind<UseScoreView.Sink>(ResultSummaryTag.DailyScoreView) with provider { instance<ScoreViewController>(ResultSummaryTag.DailyScoreView) }
            bind<UseScoreView.Sink>(ResultSummaryTag.WeeklyScoreView) with provider { instance<ScoreViewController>(ResultSummaryTag.WeeklyScoreView) }

            bind<ScoreViewController>(ResultSummaryTag.DailyScoreView) with singleton { ScoreViewController(find(ui.dailyScoreTextViewId)) }
            bind<ScoreViewController>(ResultSummaryTag.WeeklyScoreView) with singleton { ScoreViewController(find(ui.weeklyScoreTextViewId)) }
        }
    }

    companion object {
        fun newInstance() = ResultSummaryFragment()
    }

    //    private val presenter = ResultSummaryFragmentPresenter(this)
    val ui = ResultSummaryFragmentUi()
    val interactions: List<Interaction<*>> by instance()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ui.createView(AnkoContext.create(activity.baseContext, this))
    }

    override fun onStart() {
        super.onStart()
        Interaction.Builder(this, FragmentEvent.STOP).buildAll(interactions)
    }
}