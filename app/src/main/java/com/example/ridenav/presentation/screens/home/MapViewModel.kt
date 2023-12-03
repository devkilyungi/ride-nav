package com.example.ridenav.presentation.screens.home

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ridenav.common.Resource
import com.example.ridenav.domain.repository.MapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val mapRepository: MapRepository,
    private val application: Application
) : ViewModel() {

    // Notice visibility state
    var noticeVisible by mutableStateOf(false)
    var informationNotice by mutableStateOf(false)

    var informationNoticeContent by mutableStateOf("")
    private var informationNoticeButtonText by mutableStateOf("Close")

    var counter by mutableStateOf(0)

    fun getRoutes(destination: String, origin: String, key: String, shouldShow: Boolean) = viewModelScope.launch {

//        showInformationNotice("We've got some recommendations for you!")

        mapRepository.getRoutes(destination, origin, key).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("TAG", "loading routes: ${result.message}")
                }

                is Resource.Success -> {
//                    if (shouldShow) {
//                        showInformationNotice("We've got some recommendations for you!")
//                    }
                    Log.d("TAG", "${result.data}")
                    Toast.makeText(application, "Recommendations loaded", Toast.LENGTH_SHORT)
                        .show()
                }

                is Resource.Error -> {
                    Log.d("TAG", "error for routes: ${result.message}")
                }

            }
        }
    }

    private fun showInformationNotice(content: String, buttonText: String = "Close") {
        informationNoticeContent = content
        informationNoticeButtonText = buttonText
        noticeVisible = true
        informationNotice = true
    }

    // Function to close notice
    fun closeNotice() {
        noticeVisible = false
        informationNotice = false
    }
}