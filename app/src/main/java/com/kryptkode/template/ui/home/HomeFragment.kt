package com.kryptkode.template.ui.home

import android.content.Context
import com.kryptkode.template.R
import com.kryptkode.template.app.base.fragment.BaseViewModelFragment
import com.kryptkode.template.databinding.FragmentHomeBinding

/**
 * Created by kryptkode on 2/19/2020.
 */
class HomeFragment :
    BaseViewModelFragment<FragmentHomeBinding, HomeViewModel>(HomeViewModel::class) {
    override fun getLayoutResource(): Int {
        return R.layout.fragment_home
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getScreenComponent().inject(this)
    }
}