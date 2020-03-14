package com.kryptkode.template.favorites

import android.os.Bundle
import com.kryptkode.template.R
import com.kryptkode.template.app.base.activity.BaseFragmentActivity

/**
 * Created by kryptkode on 3/13/2020.
 */
class FavoritesActivity : BaseFragmentActivity<FavoritesFragment>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showUpEnabled(true)
        setActionBarTitle(getString(R.string.title_favorites))
    }

    override fun accessFragment(): FavoritesFragment {
        return FavoritesFragment()
    }
}