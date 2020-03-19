package com.kryptkode.template.app.base.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import com.kryptkode.template.app.base.activity.BaseActivity
import com.kryptkode.template.app.di.screen.ScreenComponent
import com.kryptkode.template.app.utils.AppToastCreator
import javax.inject.Inject


abstract class BaseFragment : Fragment() {

    lateinit var baseActivity: BaseActivity

    @Inject
    lateinit var appToastCreator: AppToastCreator

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

    fun showToast(message:String){
        appToastCreator.showToast(message)
    }
}