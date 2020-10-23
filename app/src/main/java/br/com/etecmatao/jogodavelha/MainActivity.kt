package br.com.etecmatao.jogodavelha

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var vm: MainViewModel
    private lateinit var items: List<ImageView>
    private val actions: MutableList<Item> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm = ViewModelProvider(this).get(MainViewModel::class.java)
        vm.start()

        vm.endGame.observe(this, {
            it?.let { gameOver ->
                if (gameOver) {
                    val winner = vm.winner.value!!

                    val msg = if (winner == MainViewModel.NONE){
                        getString(R.string.no_winner)
                    } else {
                        getString(R.string.winner, winner)
                    }

                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

                    vm.start()
                    actions.forEach { item ->
                        item.reset()
                    }
                }
            }
        })

        items = listOf(
            topLeftItem,
            topCenterItem,
            topRightItem,
            middleLeftItem,
            middleCenterItem,
            middleRightItem,
            bottomLeftItem,
            bottomCenterItem,
            bottomRightItem
        )

        var line = 0
        var column = 0

        items.forEach { item ->
            val newItem = Item(item, line, column, false)
            newItem.start(vm)

            actions.add(newItem)

            column++

            if (column > 2) {
                line++
                column = 0
            }
        }
    }
}