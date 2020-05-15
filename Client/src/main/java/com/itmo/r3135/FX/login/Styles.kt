package no.tornado.fxsample.login

import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val loginScreen by cssclass()
    }

    init {
        loginScreen {
            padding = box(15.px)
            vgap = 50.px
            hgap = 50.px
        }
    }
}