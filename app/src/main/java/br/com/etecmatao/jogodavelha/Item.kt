package br.com.etecmatao.jogodavelha

import android.widget.ImageView

data class Item(
    val imageView: ImageView,
    val line: Int,
    val column: Int,
    var selected: Boolean
) {
    fun start(action: ItemAction) {
        imageView.setOnClickListener {
            if (!selected) {
                (it as ImageView).setImageResource(action.currentImage())
                action.select(line, column)
                selected = true
            }
        }
    }

    fun reset() {
        imageView.setImageResource(R.drawable.ic_no_image)
        selected = false
    }
}