package com.kryptkode.template.favoritecards

import android.content.Context
import android.os.Bundle
import android.view.View
import com.kryptkode.template.Navigator
import com.kryptkode.template.R
import com.kryptkode.template.app.base.fragment.BaseViewModelFragment
import com.kryptkode.template.app.customviews.SpacesItemDecoration
import com.kryptkode.template.app.utils.Constants
import com.kryptkode.template.app.utils.extensions.observe
import com.kryptkode.template.cardlist.adapter.CardAdapter
import com.kryptkode.template.cardlist.adapter.CardListener
import com.kryptkode.template.cardlist.model.CardForView
import com.kryptkode.template.databinding.FragmentFavoriteCardsBinding
import javax.inject.Inject

/**
 * Created by kryptkode on 3/13/2020.
 */
class FavoriteCardsFragment : BaseViewModelFragment<FragmentFavoriteCardsBinding, FavoriteCardsViewModel>(FavoriteCardsViewModel::class) {

    @Inject
    lateinit var navigator: Navigator

    private val cardListListener = object : CardListener {
        override fun onItemClick(item: CardForView?) {
            viewModel.handleCardItemClick(item)
        }

        override fun onItemFavoriteClick(isFavorite: Boolean, item: CardForView?) {
            viewModel.handleCardFavoriteClick(item, isFavorite)
        }
    }
    private val categoriesAdapter = CardAdapter(cardListListener)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getScreenComponent().inject(this)
    }

    override fun getLayoutResource() = R.layout.fragment_favorite_cards

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupObservers()
    }

    private fun initViews() {
        binding.swipe.setOnRefreshListener {
            viewModel.refresh()
        }
        initList()
    }

    private fun initList() {
        binding.recyclerView.adapter = categoriesAdapter
        binding.recyclerView.addItemDecoration(SpacesItemDecoration(Constants.LIST_SPACING))
        binding.recyclerView.setEmptyView(binding.emptyStateLayout.emptyView)
    }

    private fun setupObservers() {
        viewModel.favoriteCardList.observe(this) {
            categoriesAdapter.submitList(it)
        }

        viewModel.getLoadingValueEvent().observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                binding.swipe.isRefreshing = it
            }
        }

        viewModel.getGoToCardDetailsEvent().observe(viewLifecycleOwner){event ->
            event?.getContentIfNotHandled()?.let {
                openCardDetails(it)
            }
        }

        viewModel.getLoadingValueEvent().observe(viewLifecycleOwner){event ->
            event?.getContentIfNotHandled()?.let {
                binding.swipe.isRefreshing = it
            }
        }

        viewModel.getErrorMessageEvent().observe(viewLifecycleOwner){event ->
            event?.getContentIfNotHandled()?.let {
                showToast(it)
                binding.emptyStateLayout.emptyViewTv.text = getString(R.string.swipe_down_msg, it)
            }
        }
    }

    private fun openCardDetails(card: CardForView) {
        navigator.openCardDetails(card)
    }
}