package com.kryptkode.template.startnav

import android.os.Bundle
import android.view.View
import com.kryptkode.template.R
import com.kryptkode.template.app.base.fragment.BaseViewModelFragment
import com.kryptkode.template.databinding.FragmentStartNavBinding

/**
 * Created by kryptkode on 3/4/2020.
 */
class StartNavFragment :
    BaseViewModelFragment<FragmentStartNavBinding, StartNavViewModel>(StartNavViewModel::class) {
    override fun getLayoutResource() = R.layout.fragment_start_nav

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}