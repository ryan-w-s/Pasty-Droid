package tech.ryanws.pastydroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.ryanws.pastydroid.utils.Paste
import tech.ryanws.pastydroid.utils.MockPastyAPI

class HomeViewModel : ViewModel() {

    private val _pastes = MutableLiveData<List<Paste>>()
    val pastes: LiveData<List<Paste>> = _pastes
    
    private var currentPage = 1
    private val perPage = 20
    private var isLoading = false
    
    init {
        loadPastes()
    }
    
    fun loadPastes() {
        if (isLoading) return
        isLoading = true
        
        viewModelScope.launch(Dispatchers.IO) {
            val result = MockPastyAPI.listPastes(currentPage, perPage)
            withContext(Dispatchers.Main) {
                val currentList = _pastes.value ?: emptyList()
                _pastes.value = currentList + result
                currentPage++
                isLoading = false
            }
        }
    }
}