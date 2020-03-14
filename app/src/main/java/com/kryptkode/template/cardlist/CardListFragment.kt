package com.kryptkode.template.cardlist

import android.content.Context
import android.os.Bundle
import android.view.View
import com.kryptkode.template.R
import com.kryptkode.template.app.base.fragment.BaseViewModelFragment
import com.kryptkode.template.app.customviews.SpacesItemDecoration
import com.kryptkode.template.app.utils.Constants.LIST_SPACING
import com.kryptkode.template.app.utils.extensions.observe
import com.kryptkode.template.cardlist.adapter.CardAdapter
import com.kryptkode.template.cardlist.adapter.CardListener
import com.kryptkode.template.cardlist.model.CardForView
import com.kryptkode.template.databinding.FragmentCardListBinding
import com.kryptkode.template.subcategories.model.SubCategoryForView

/**
 * Created by kryptkode on 3/12/2020.
 */
class CardListFragment :
    BaseViewModelFragment<FragmentCardListBinding, CardListViewModel>(CardListViewModel::class) {

    private val subcategory by lazy {
        requireArguments().getParcelable<SubCategoryForView>(ARG_SUBCATEGORY)!!
    }

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

    override fun getLayoutResource() = R.layout.fragment_card_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupObservers()
        viewModel.loadCards(subcategory.id)
    }

    private fun initViews() {
        binding.swipe.setOnRefreshListener {
            viewModel.refresh(subcategory.id)
        }
        initList()
    }

    private fun initList() {
        binding.recyclerView.adapter = categoriesAdapter
        binding.recyclerView.addItemDecoration(SpacesItemDecoration(LIST_SPACING))
        binding.recyclerView.setEmptyView(binding.emptyStateLayout)
    }

    private fun setupObservers() {
        viewModel.cardList.observe(this) {
            categoriesAdapter.submitList(it)
        }

        viewModel.getLoadingValueEvent().observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                binding.swipe.isRefreshing = it
            }
        }
    }

    companion object {
        private const val ARG_SUBCATEGORY = "subcategory"
        fun newInstance(
            subcategory: SubCategoryForView
        ): CardListFragment {
            val fragment = CardListFragment()
            val args = Bundle()
            args.putParcelable(ARG_SUBCATEGORY, subcategory)
            fragment.arguments = args
            return fragment
        }
    }
}