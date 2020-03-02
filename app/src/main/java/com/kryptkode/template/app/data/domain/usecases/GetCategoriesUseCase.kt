package com.kryptkode.template.app.data.domain.usecases

import com.kryptkode.template.app.data.domain.model.Category
import com.kryptkode.template.app.data.domain.repository.CategoryRepository

/**
 * Created by kryptkode on 3/2/2020.
 */
class GetCategoriesUseCase (private val repository: CategoryRepository) : UseCase<List<Category>, Unit>() {
    override suspend fun buildUseCase(params: Unit?): List<Category> {
        return repository.getAllCategories()
    }
}