package com.kryptkode.template.app.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by kryptkode on 2/19/2020.
 */
@Entity
data class SampleModel(
    @PrimaryKey(autoGenerate = true) val id: Int
)