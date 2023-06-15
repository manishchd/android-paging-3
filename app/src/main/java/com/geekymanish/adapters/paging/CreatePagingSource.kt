package com.geekymanish.adapters.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.geekymanish.remote.PaginationHandler

class CreatePagingSource<T : Any>(private val requestHandler: PaginationHandler<List<T>>) :
    PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val page = params.key ?: INITIAL_PAGE
            val response = requestHandler.sendRequest(page)
            val list = response.body()?.data.orEmpty()

            val total = response.body()?.totalPages ?: 0
            val newCount = list.size
            val itemsBefore = page * params.loadSize
            val itemsAfter = total - (itemsBefore + newCount)

            LoadResult.Page(
                data = list,
                prevKey = if (page == INITIAL_PAGE) null else page - 1,
                nextKey = if (list.isEmpty()) null else page + 1,
                itemsAfter = itemsAfter
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val INITIAL_PAGE = 1
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}