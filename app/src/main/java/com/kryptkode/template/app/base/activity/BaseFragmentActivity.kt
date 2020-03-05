package com.kryptkode.template.app.base.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

abstract class BaseFragmentActivity<T : Fragment> : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var frag = supportFragmentManager
                .findFragmentById(android.R.id.content)

        if (frag == null) {
            frag = accessFragment()
        }

        addFragmentToActivity(supportFragmentManager, frag, android.R.id.content)
    }


    abstract fun accessFragment(): T

    fun addFragmentToActivity(fragmentManager: FragmentManager,
                              fragment: Fragment, frameId: Int) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(frameId, fragment)
        transaction.commit()
    }

    fun showUpEnabled(enabled: Boolean) {

        supportActionBar?.setDisplayHomeAsUpEnabled(enabled)

    }


    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }


    override fun onBackPressed() {
        supportFinishAfterTransition()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (supportFragmentManager.findFragmentById(android.R.id.content) != null) {
            supportFragmentManager.findFragmentById(android.R.id.content)?.onActivityResult(requestCode, resultCode, data)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}