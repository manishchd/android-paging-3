package com.geekymanish.ui.passengers

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.geekymanish.adapters.paging.CreatePagingSource
import com.geekymanish.remote.APIRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PassengersVM @Inject constructor(
    private val apiRequest: APIRequest,
) : ViewModel() {

    val pager by lazy {
        Pager(
            config = PagingConfig(
                pageSize = 5, enablePlaceholders = true, initialLoadSize = 10
            )
        ) {
            CreatePagingSource(requestHandler = {
                apiRequest.notificationHistory(it, 5)
            })
        }.flow
    }
}