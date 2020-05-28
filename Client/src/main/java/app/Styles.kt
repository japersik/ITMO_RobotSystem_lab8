package com.itmo.r3135.app

import javafx.scene.paint.Color.color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import jdk.nashorn.internal.objects.NativeDebug.getClass
import tornadofx.*
import tornadofx.WizardStyles.Companion.heading
import view.WorkView.ProductsSearch

class Styles : Stylesheet() {
    companion object {
        val main by cssclass()
        val loginScreen by cssclass()
        val xy by cssclass()
        val productsSearch by cssclass()
        val addform by cssclass()
        val backgroundcolor = c("#a5ff9b")
        val greencolor = c("#4e9830")


        val comfortaa =   //loadFont(getClass().getResource("Comfortaa-Regular.ttf"),16)
    }

    init {

        label and heading{
            padding = box(100.px)
            fontSize = 100.px
            fontWeight = FontWeight.BOLD
        }

        addform{
            padding = box(50.px)
            prefWidth = 450.px
            backgroundColor += backgroundcolor
            s(xy) {
                maxWidth = 60.px
                minWidth = maxWidth
            }
        }
        loginScreen {
            backgroundColor += backgroundcolor
            padding = box(15.px)
            vgap = 50.px
            hgap = 50.px
            font = Font.font()
        }
        productsSearch {
            padding = box(15.px)
            vgap = 10.px
            hgap = 10.px
        }
        main {
            backgroundColor += backgroundcolor
        }
    }
}