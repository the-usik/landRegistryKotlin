package extensions

import tornadofx.ListMenu

fun ListMenu.selectItem(index: Int) {
    activeItem = items[index]
    activeItem?.requestFocus()
}