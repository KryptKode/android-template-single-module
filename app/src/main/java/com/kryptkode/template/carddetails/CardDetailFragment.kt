package com.kryptkode.template.carddetails

import android.content.Context
import android.os.Bundle
import android.view.View
import com.kryptkode.template.R
import com.kryptkode.template.app.base.fragment.BaseViewModelFragment
import com.kryptkode.template.app.utils.extensions.observe
import com.kryptkode.template.carddetails.adapter.CardDetailsAdapter
import com.kryptkode.template.carddetails.adapter.CardDetailsListener
import com.kryptkode.template.cardlist.model.CardForView
import com.kryptkode.template.databinding.FragmentCardDetailBinding
import timber.log.Timber

/**
 * Created by kryptkode on 3/14/2020.
 */
class CardDetailFragment :
    BaseViewModelFragment<FragmentCardDetailBinding, CardDetailViewModel>(CardDetailViewModel::class) {

    private val card by lazy {
        arguments?.getParcelable<CardForView>(ARG_CARD)!!
    }

    private val cardDetailsListener = object : CardDetailsListener {
        override fun onShareWhatsAppClick(cardForView: CardForView?) {
            viewModel.onShareWhatsApp(cardForView)
        }

        override fun onShareTwitterClick(cardForView: CardForView?) {
            viewModel.onShareTwitter(cardForView)
        }

        override fun onShareFacebookClick(cardForView: CardForView?) {
            viewModel.onShareFacebook(cardForView)
        }

        override fun onShareOtherAppClick(cardForView: CardForView?) {
            viewModel.onShareOtherApps(cardForView)
        }

        override fun onItemFavoriteClick(isFavorite: Boolean, item: CardForView?) {
            viewModel.toggleFavorite(item, isFavorite)
        }
    }

    private val adapter = CardDetailsAdapter(cardDetailsListener)
    private var firstTime = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getScreenComponent().inject(this)
    }

    override fun getLayoutResource() = R.layout.fragment_card_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupObservers()
        loadData()
    }

    private fun loadData() {
        viewModel.loadData(card.subcategoryId)
    }

    private fun initViews() {
        initToolbar()
        initSwipeRefresh()
        initViewPager()
    }

    private fun initToolbar() {
        (activity as? CardDetailActivity)?.setSupportActionBar(binding.toolbar)
        (activity as? CardDetailActivity)?.setActionBarTitle(getString(R.string.card_detail_title))
        (activity as? CardDetailActivity)?.showUpEnabled(true)
    }

    private fun initSwipeRefresh() {
        binding.swipe.isEnabled = false
        binding.swipe.setOnRefreshListener {
            viewModel.loadData(card.subcategoryId)
        }
    }

    private fun initViewPager() {
        binding.viewPager.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getLoadingValueEvent().observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                binding.swipe.isRefreshing = it
            }
        }

        viewModel.cardList.observe(this) {
            adapter.submitList(it)
            scrollToTabIfFirstTimeAndCardPresent(it)
        }
    }

    private fun scrollToTabIfFirstTimeAndCardPresent(cards: List<CardForView>?) {
        if (firstTime) {
            val cardIndex = cards?.indexOfFirst { item ->
                card.id == item.id
            }
            Timber.d("CARD INDEX: $cardIndex")
            binding.viewPager.setCurrentItem(cardIndex ?: 0, false)
            firstTime = false
        }
    }


    companion object {
        private const val ARG_CARD = "arg.card"
        fun newInstance(cardForView: CardForView): CardDetailFragment {
            val fragment = CardDetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_CARD, cardForView)
            fragment.arguments = args
            return fragment
        }
    }

}