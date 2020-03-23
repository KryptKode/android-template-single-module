package com.kryptkode.template.app.data.repo

import android.content.Context
import com.kryptkode.template.R
import com.kryptkode.template.app.data.domain.model.Category
import com.kryptkode.template.app.data.local.Local
import timber.log.Timber

/**
 * Created by kryptkode on 3/22/2020.
 */
class LockedCategoryHelper (
    private val local: Local,
    context: Context) {
    private val defaultLockValue = context.resources.getBoolean(R.bool.category_all_positions_locked_initially)

    private val lockedCategoriesDefault = context.resources.getStringArray(R.array.category_locked_positions_by_default).map {
        when (it) {
            "true" -> {
                true
            }
            "false" -> {
                false
            }
            else -> {
                defaultLockValue
            }
        }
    }

    val lockedCategories = context.resources.getIntArray(R.array.category_locked_positions).toList().mapIndexed {index, position->
        LockedCategory(position, getLockedCategoryDefault(index))
    }

    private fun getLockedCategoryDefault(index: Int): Boolean {
        return if(lockedCategoriesDefault.size > index){
            lockedCategoriesDefault[index]
        }else{
            defaultLockValue
        }
    }
    fun lock(categoriesResult: List<Category>){
        lockedCategories.forEach {lockedCategory->
            Timber.e("Locked category: $lockedCategory")
            if(categoriesResult.size > lockedCategory.position && lockedCategory.position > 0){
                Timber.e("Locking category")
                val category = categoriesResult[lockedCategory.position-1]
                val dateWhenCategoryWasUnLocked = local.hasDateWhenCategoryWasUnlockedBeenLongEnough(category.id)
                if(dateWhenCategoryWasUnLocked){
                    category.locked = local.isCategoryLocked(category.id, lockedCategory.lockedByDefault)
                    Timber.d("Locked category $category")
                }else{
                    Timber.d("Category date has been long enough")
                }
            }
        }
    }

    fun applyLock(categoriesResult: List<Category>): List<Category> {
        lockedCategories.forEach {lockedCategory->
            Timber.e("Locked category: $lockedCategory")
            if(isValidPosition(lockedCategory.position, categoriesResult.size)){
                applyLockToCategory(categoriesResult, lockedCategory)
            }
        }
        return categoriesResult
    }

    private fun applyLockToCategory(
        categoriesResult: List<Category>,
        lockedCategory: LockedCategory) {
        Timber.e("Locking category")
        val category = categoriesResult[lockedCategory.position - 1]

        val hasPassed =
            local.hasDateWhenCategoryWasUnlockedBeenLongEnough(category.id)
        if (hasPassed) {
            category.locked = local.isCategoryLocked(category.id, getDefaultLockStatus(category, lockedCategory))
            Timber.d("Locked category $category")
        }
    }

    private fun getDefaultLockStatus(category: Category, lockedCategory: LockedCategory): Boolean {
        return if(local.getDateWhenCategoryWasUnlocked(category.id) == -1L){
            lockedCategory.lockedByDefault
        }else{
            true
        }
    }

    private fun isValidPosition(position:Int, totalSize:Int):Boolean{
        return position in 1 until totalSize
    }
}

data class LockedCategory(val position:Int, val lockedByDefault:Boolean)