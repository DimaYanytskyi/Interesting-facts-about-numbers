package com.dima.test.presentation.main_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dima.test.common.Response
import com.dima.test.domain.interactor.NumberInteractor
import com.dima.test.domain.model.NumberInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val numberInteractor: NumberInteractor
) : ViewModel(){

    private val _numberInput = MutableLiveData<Int>()

    private val _numberInfo = MutableLiveData<NumberInfo>()
    val numberInfo: LiveData<NumberInfo> = _numberInfo

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _listOfRecent = MutableLiveData<List<NumberInfo>>(emptyList())
    val listOfRecent: LiveData<List<NumberInfo>> = _listOfRecent

    init {
        getRecent()
    }

    private fun getRecent() {
        viewModelScope.launch {
            numberInteractor.getRecent().collect { result ->
                when (result) {
                    is Response.Success -> {
                        _listOfRecent.value = result.data ?: emptyList()
                    }
                    is Response.Error -> {
                        _error.value = result.message ?: "Can't load recent numbers"
                    }
                }
            }
        }
    }

    fun setRecent(){
        _listOfRecent.value?.let { numberInteractor.insertRecent(it) }
    }

    private fun addNumberInfoToList(info: NumberInfo){
        if(_listOfRecent.value != null) {
            if(!_listOfRecent.value!!.map { it.fact }.contains(info.fact)){
                val newList = _listOfRecent.value!!.toMutableList()
                newList.add(info)
                _listOfRecent.value = newList.toList()
            }
        }
    }

    fun setNumberInputText(number: Int){
        _numberInput.value = number
    }

    fun fetchInfoByNumber(){
        if(_numberInput.value != null){
            viewModelScope.launch {
                numberInteractor.fetchInfoByNumber(_numberInput.value!!).collect { result ->
                    when(result){
                        is Response.Success -> {
                            _numberInfo.value = result.data ?: NumberInfo(
                                _numberInput.value!!,
                                _numberInput.value!!,
                                "An unexpected error was occurred",
                                false
                            )
                            addNumberInfoToList(_numberInfo.value!!)
                            clearInfo()
                        }
                        is Response.Error -> {
                            _error.value = result.message ?: "An unexpected error was occurred"
                        }
                    }
                }
            }
        } else {
            _error.value = "The number input is empty!"
        }
    }

    fun fetchInfoByRandomNumber(){
        viewModelScope.launch {
            numberInteractor.fetchInfoByRandomNumber().collect { result ->
                when(result){
                    is Response.Success -> {
                        _numberInfo.value = result.data ?: NumberInfo(
                            -1,
                            -1,
                            "An unexpected error was occurred",
                            false
                        )
                        addNumberInfoToList(_numberInfo.value!!)
                        clearInfo()
                    }
                    is Response.Error -> {
                        _error.value = result.message ?: "An unexpected error was occurred"
                    }
                }
            }
        }
    }

    private fun clearInfo(){
        _numberInfo.value = NumberInfo(
            -1,
            -1,
            "",
            false
        )
    }
}