package styles

import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.effect.DropShadow
import javafx.scene.effect.Effect
import tornadofx.*

class MainStyles : Stylesheet() {
    companion object {
        val rootContainer by cssid()
        val mainContent by cssid()
        val listMenu by cssclass()
        val block by cssclass()
        val lightBackgroundColor = c("#3F8FD2")
        val hoverColor = c("#EFEFEF")
        val selectedColor = c("#F3F3F3")
        val blockColor = c("#FFFFFF")
        val textColor = c("#111111")

        val defaultBorderRadius = box(10.px)
    }

    init {
        root {
            fontSize = 14.px
            fontFamily = "Roboto Medium"
            backgroundColor = multi(lightBackgroundColor)
        }

        val commonBlock = mixin {
            backgroundColor += blockColor
            backgroundRadius += box(10.px)
            effect = DropShadow(5.0, 0.0, 2.0, c(0, 0, 0, .2))
        }

        block {
            +commonBlock
            padding = box(10.px)
        }

        listMenu {
            +commonBlock
            backgroundRadius += defaultBorderRadius
            backgroundColor += blockColor

            s(".list-item") {
                padding = box(8.px, 15.px)
                backgroundColor += blockColor
                backgroundInsets += box(2.px)
                backgroundRadius += defaultBorderRadius

                and(hover) {
                    backgroundColor += hoverColor
                }
                and(selected, focused) {
                    backgroundColor += selectedColor
                }
            }
        }

        s(".text-field") {
            backgroundColor += c("#fff")
            backgroundRadius += box(10.px)
        }

        s(".button") {
            backgroundColor += c("#e5ebf1")
            backgroundRadius += box(10.px)
            effect = DropShadow(0.0, 0.0, 1.0, c(0, 0, 0, .1))
            and(hover) {
                cursor = Cursor.HAND
                backgroundColor += c("#e5ebf4")
            }
        }

        mainContent {
//            +commonBlock
        }

    }
}