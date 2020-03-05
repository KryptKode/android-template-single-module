package com.kryptkode.template.privacypolicy

import com.kryptkode.template.app.base.activity.BaseFragmentActivity

class PrivacyPolicyActivity : BaseFragmentActivity<PrivacyPolicyFragment>() {
    override fun accessFragment(): PrivacyPolicyFragment {
        return PrivacyPolicyFragment.newInstance()
    }
}