package com.kryptkode.template.privacypolicy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.kryptkode.template.R
import com.kryptkode.template.databinding.FragmentPrivacyPolicyBinding


class PrivacyPolicyFragment : Fragment() {
    private lateinit var binding: FragmentPrivacyPolicyBinding

    companion object {

        @JvmStatic
        fun newInstance(): PrivacyPolicyFragment {
            val bundle = Bundle()
            val fragment = PrivacyPolicyFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_privacy_policy, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webView.settings.builtInZoomControls = true
        binding.webView.settings.displayZoomControls = false


        (activity as PrivacyPolicyActivity).showUpEnabled(true)
        (activity as PrivacyPolicyActivity).setActionBarTitle(getString(R.string.title_privacy_policy))
        binding.webView.loadUrl("file:///android_asset/PrivacyPolicy.html")

    }
}
