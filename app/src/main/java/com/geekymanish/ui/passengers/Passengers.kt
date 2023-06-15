package com.geekymanish.ui.passengers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.geekymanish.adapters.paging.FooterLoadAdapter
import com.geekymanish.adapters.paging.PagingGenericAdapter
import com.geekymanish.dto.models.NotificationModel
import com.geekymanish.myapplication.R
import com.geekymanish.myapplication.databinding.ItemPassengerBinding
import com.geekymanish.myapplication.databinding.PassengersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class Passengers : Fragment(R.layout.passengers) {

    private var _binding: PassengersBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<PassengersVM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = PassengersBinding.bind(view)
        implementationPaging()
    }

    private fun implementationPaging() {
        val adapter = object : PagingGenericAdapter<NotificationModel>(
            layoutId = R.layout.item_passenger, placeHolder = R.layout.item_passenger_placeholder
        ) {
            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val item = getItem(holder.absoluteAdapterPosition) ?: return
                ItemPassengerBinding.bind(holder.itemView).let { rvBinding ->
                    rvBinding.tvPassenger.text = item.name?.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    }
                    Glide.with(requireContext()).load(item.airline?.firstOrNull()?.logo ?: "")
                        .into(rvBinding.ivUserOne)
                }
            }
        }

        binding.rvPassengers.adapter = adapter.withLoadStateFooter(FooterLoadAdapter {
            adapter.retry()
        })

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pager.collectLatest { list ->
                    adapter.submitData(list)
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}
