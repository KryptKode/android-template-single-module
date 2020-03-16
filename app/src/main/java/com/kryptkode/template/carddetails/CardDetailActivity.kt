package com.kryptkode.template.carddetails

import android.content.Context
import android.content.Intent
import com.kryptkode.template.app.base.activity.BaseFragmentActivity
import com.kryptkode.template.cardlist.model.CardForView

/**
 * Created by kryptkode on 3/14/2020.
 */
class CardDetailActivity : BaseFragmentActivity<CardDetailFragment>() {

    private val card by lazy {
        intent.getParcelableExtra<CardForView>(ARG_CARD)!!
    }

    override fun accessFragment(): CardDetailFragment {
        return CardDetailFragment.newInstance(card)
    }

    companion object {
        private const val ARG_SUBCATEGORY = "subcategory"
        private const val ARG_CARD = "card"

        fun start(
            context: Context,
            card: CardForView
        ) {
            val intent = Intent(context, CardDetailActivity::class.java)
            intent.putExtra(ARG_CARD, card)
            context.startActivity(intent)
        }
    }
}