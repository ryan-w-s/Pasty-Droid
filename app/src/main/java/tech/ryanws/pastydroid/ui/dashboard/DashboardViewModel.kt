package tech.ryanws.pastydroid.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.ryanws.pastydroid.utils.Paste
import tech.ryanws.pastydroid.utils.MockPastyAPI

class DashboardViewModel : ViewModel() {

    private val _paste = MutableLiveData<Paste?>()
    val paste: LiveData<Paste?> = _paste
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    fun fetchPaste(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val result = MockPastyAPI.getPaste(id)
            withContext(Dispatchers.Main) {
                _paste.value = result
                _isLoading.value = false
            }
        }
    }
}