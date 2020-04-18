package com.kryptkode.template.cardlist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.kryptkode.template.Navigator
import com.kryptkode.template.R
import com.kryptkode.template.ads.AdConfig
import com.kryptkode.template.ads.NativeAdHelper
import com.kryptkode.template.ads.NativeAdRowHelper
import com.kryptkode.template.app.base.fragment.BaseViewModelFragment
import com.kryptkode.template.app.customviews.SpacesItemDecoration
import com.kryptkode.template.app.utils.Constants.LIST_SPACING
import com.kryptkode.template.app.utils.extensions.observe
import com.kryptkode.template.cardlist.adapter.CardListener
import com.kryptkode.template.cardlist.adapter.items.CardItem
import com.kryptkode.template.cardlist.adapter.items.NativeAdItem
import com.kryptkode.template.cardlist.adapter.nativead.NativeAdAdapterHelper
import com.kryptkode.template.cardlist.model.CardForView
import com.kryptkode.template.databinding.FragmentCardListBinding
import com.kryptkode.template.subcategories.model.SubCategoryForView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by kryptkode on 3/12/2020.
 */
class CardListFragment :
    BaseViewModelFragment<FragmentCardListBinding, CardListViewModel>(CardListViewModel::class) {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var nativeAdRowHelper: NativeAdRowHelper

    @Inject
    lateinit var nativeAdHelper: NativeAdHelper

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

    private val adapter = GroupAdapter<GroupieViewHolder>().apply {
        spanCount = 2
    }

    private val nativeAdAdapterHelper by lazy {
        NativeAdAdapterHelper(
            adapter,
            nativeAdRowHelper,
            subcategory.name
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getScreenComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }

    override fun getLayoutResource() = R.layout.fragment_card_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.loadCards(subcategory)
    }

    private fun initViews() {
        binding.swipe.setOnRefreshListener {
            viewModel.refresh(subcategory.id)
        }
        initList()
    }

    private fun initList() {
        val layoutManager by lazy {
            GridLayoutManager(requireContext(), adapter.spanCount).apply {
                spanSizeLookup = adapter.spanSizeLookup
            }
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration(SpacesItemDecoration(LIST_SPACING))
        binding.recyclerView.setEmptyView(binding.emptyStateLayout.emptyView)
    }

    private fun setupObservers() {
        viewModel.cardList.observe(this) {
            Timber.d("Subscribe...")
            populateCards(it ?: listOf())
        }

        viewModel.getLoadingValueEvent().observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                binding.swipe.isRefreshing = it
            }
        }

        viewModel.getGoToCardDetailsEvent().observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                openCardDetails(it)
            }
        }
    }

    private fun populateCards(items: List<CardForView>) {
        val itemSize = items.size
        nativeAdRowHelper.populateNativeAdRows(0, itemSize)

        adapter.updateAsync(items.mapIndexed { index, card ->
            if (nativeAdRowHelper.getNativeAdRows().contains(index)) {
                NativeAdItem(nativeAdHelper.getNativeAd())
            } else {
                CardItem(card, cardListListener)
            }
        }) {
            if(itemSize < AdConfig.NATIVE_AD_AFTER_POSTS){
                adapter.add(NativeAdItem(nativeAdHelper.getNativeAd()))
            }
            /*initNativeAds(0, items.size)*/
        }
    }

    private fun initNativeAds(positionStart: Int, itemCount: Int) {
        Timber.d("Item inserted ${subcategory.name}: $positionStart -- $itemCount")
        Timber.d("Inserting native ad...")
        insertNativeAds(positionStart, itemCount)
    }

    private fun insertNativeAds(startPosition: Int, itemCount: Int) {
        nativeAdHelper.loadNativeAds(object : NativeAdHelper.NativeAdListener {
            override fun onAdLoaded() {
                nativeAdAdapterHelper.updateAdapterWithNativeAds(
                    startPosition,
                    itemCount
                )
            }

            override fun onAdFailed() {
                Timber.e("Failed to load native ads")
                nativeAdAdapterHelper.updateAdapterWithNativeAds(
                    startPosition,
                    itemCount
                )
            }

            override fun addNativeAd(unifiedNativeAd: UnifiedNativeAd) {
                nativeAdAdapterHelper.addNativeAd(unifiedNativeAd)
            }
        })
    }

    private fun openCardDetails(card: CardForView) {
        navigator.openCardDetails(card)
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