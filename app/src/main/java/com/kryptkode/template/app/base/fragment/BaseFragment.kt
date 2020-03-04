package com.kryptkode.template.app.base.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import com.kryptkode.template.app.base.activity.BaseActivity
import com.kryptkode.template.app.di.screen.ScreenComponent


abstract class BaseFragment : Fragment() {

    lateinit var baseActivity: BaseActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity: BaseActivity = context
            this.baseActivity = activity
        }
    }

    fun getScreenComponent(): ScreenComponent {
        return baseActivity.getScreenComponent()
    }
}