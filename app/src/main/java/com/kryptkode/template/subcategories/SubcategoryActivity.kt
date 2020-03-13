package com.kryptkode.template.subcategories

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kryptkode.template.app.base.activity.BaseFragmentActivity
import com.kryptkode.template.categories.model.CategoryForView
import com.kryptkode.template.subcategories.model.SubCategoryForView

class SubcategoryActivity : BaseFragmentActivity<SubcategoriesFragment>() {

    private val category by lazy {
        intent.getParcelableExtra<CategoryForView>(ARG_CATEGORY)!!
    }

    private val subcategory by lazy {
        intent.getParcelableExtra<SubCategoryForView>(ARG_SUBCATEGORY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showUpEnabled(true)
        setActionBarTitle(category.name.capitalize())
    }

    override fun accessFragment(): SubcategoriesFragment {
        return SubcategoriesFragment.newInstance(category, subcategory)
    }

    companion object {
        private const val ARG_CATEGORY = "category"
        private const val ARG_SUBCATEGORY = "subcategory"

        fun start(
            context: Context,
            category: CategoryForView,
            subcategory: SubCategoryForView? = null
        ) {
            val intent = Intent(context, SubcategoryActivity::class.java)
            intent.putExtra(ARG_CATEGORY, category)
            intent.putExtra(ARG_SUBCATEGORY, subcategory)
            context.startActivity(intent)
        }
    }
}