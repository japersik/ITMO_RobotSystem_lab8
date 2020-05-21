package com.itmo.r3135.app

import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
        val main by cssclass()
        val neighborhood by cssclass()
        val street by cssclass()
        val schedule by cssclass()
        val catPicture by cssclass()
        val transparentOverlay by cssclass()
        val loginScreen by cssclass()
        val zip by cssclass()
        val xy by cssclass()
    }

    init {
        s(form) {
            padding = box(50.px)
            prefWidth = 450.px

            s(xy) {
                maxWidth = 60.px
                minWidth = maxWidth
            }
        }
    }

    init {
        loginScreen {
            padding = box(15.px)
            vgap = 50.px
            hgap = 50.px
        }
    }

    init {

        main {
            backgroundColor += c("222222")
        }

        neighborhood {
            backgroundColor += c("4E9830")
            prefWidth = 470.px
            prefHeight = 590.px
        }

        street {
            backgroundColor += c("919191")
        }

        schedule {
            prefWidth = 600.px
        }

        label and heading {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }

        catPicture {
            prefWidth = 200.px
            prefHeight = 100.px
        }

        transparentOverlay {
            backgroundColor += c(0, 100, 100, 0.05)
        }
    }
}