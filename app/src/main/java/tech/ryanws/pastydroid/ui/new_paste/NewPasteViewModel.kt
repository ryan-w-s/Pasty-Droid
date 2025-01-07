package tech.ryanws.pastydroid.ui.new_paste

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.ryanws.pastydroid.utils.MockPastyAPI

class NewPasteViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    fun createPaste(content: String) {
        if (content.isBlank()) return
        
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val result = MockPastyAPI.createPaste(content)
            withContext(Dispatchers.Main) {
                _success.value = result
                _isLoading.value = false
            }
        }
    }
} 