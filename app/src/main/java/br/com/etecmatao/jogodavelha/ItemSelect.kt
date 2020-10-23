package br.com.etecmatao.jogodavelha

interface ItemAction{
    fun select(line: Int, column: Int)
    fun currentImage(): Int
}

interface ItemSelect{
    fun onItemSelected(action: ItemAction)

}