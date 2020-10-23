package br.com.etecmatao.jogodavelha

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application), ItemAction{
    private val currentImage: MutableLiveData<Int> = MutableLiveData(R.drawable.ic_x)
    val winner: MutableLiveData<String> = MutableLiveData(NONE)
    val endGame: MutableLiveData<Boolean> = MutableLiveData(false)

    private var board = arrayOf(
        arrayOf(NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE),
        arrayOf(NONE, NONE, NONE),
    )

    override fun select(line: Int, column: Int) {
        val fill = if(currentImage.value == R.drawable.ic_x){
            X
        } else {
            O
        }

        board[line][column] = fill

        check(fill)
        next()
    }

    override fun currentImage(): Int {
        return currentImage.value!!
    }

    fun check(element: String) = viewModelScope.launch(Dispatchers.IO) {
        val hasOption = board.filter { line ->
            line.filter { it == NONE }.count() > 0
        }.count() > 0

        if (!hasOption){
            endGame.postValue(true)
        }

        if (win(element)){
            winner.postValue(element)
            endGame.postValue(true)
        }
    }

    fun win(option: String): Boolean {
        for (line in 0..2){
            if (board[line][0] == option &&
                board[line][1] == option &&
                board[line][2] == option){
                return true
            }
        }

        for (column in 0..2){
            if (board[0][column] == option &&
                board[1][column] == option &&
                board[2][column] == option){
                return true
            }
        }

        return (board[0][0] == option && board[1][1] == option && board[2][2] == option) ||
                (board[2][0] == option && board[1][1] == option && board[0][2] == option)
    }

    fun start() = viewModelScope.launch {
        board = arrayOf(
            arrayOf(NONE, NONE, NONE),
            arrayOf(NONE, NONE, NONE),
            arrayOf(NONE, NONE, NONE),
        )

        winner.postValue(NONE)
        endGame.postValue(false)
        currentImage.postValue(R.drawable.ic_x)
    }

    fun next() = viewModelScope.launch(Dispatchers.IO) {
        val resource = if(currentImage.value == R.drawable.ic_x){
            R.drawable.ic_o
        } else {
            R.drawable.ic_x
        }

        currentImage.postValue(resource)
    }

    companion object{
        const val X = "X"
        const val O = "O"
        const val NONE = ""
    }


}