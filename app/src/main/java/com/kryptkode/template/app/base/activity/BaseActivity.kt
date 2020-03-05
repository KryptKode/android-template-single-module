package com.kryptkode.template.app.base.activity

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.kryptkode.template.app.coreComponent
import com.kryptkode.template.app.di.screen.ScreenComponent
import com.kryptkode.template.app.di.screen.modules.ScreenModule


abstract class BaseActivity : AppCompatActivity() {


    fun getScreenComponent(): ScreenComponent {
        return coreComponent().screenComponent(
            ScreenModule(
                this
            )
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}