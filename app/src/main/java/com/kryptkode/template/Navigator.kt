package com.kryptkode.template

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.kryptkode.template.categories.model.CategoryForView
import com.kryptkode.template.privacypolicy.PrivacyPolicyActivity
import com.kryptkode.template.subcategories.SubcategoryActivity
import com.kryptkode.template.subcategories.model.SubCategoryForView

/**
 * Created by kryptkode on 3/5/2020.
 */
class Navigator(private val activity: FragmentActivity) {
    fun openFavorites() {
        //TODO
    }

    fun openOssLicences() {
        activity.startActivity(Intent(activity, OssLicensesMenuActivity::class.java))
    }

    fun openPrivacyPolicy() {
        activity.startActivity(Intent(activity, PrivacyPolicyActivity::class.java))
    }

    fun openSubCategories(categoryForView: CategoryForView, subCategoryForView: SubCategoryForView?=null) {
        SubcategoryActivity.start(activity, categoryForView, subCategoryForView)
    }
}