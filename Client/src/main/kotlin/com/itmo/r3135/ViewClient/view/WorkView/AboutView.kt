package com.itmo.r3135.ViewClient.view.WorkView

import com.itmo.r3135.ViewClient.view.Styles
import tornadofx.*

class AboutView : View("Info") {


    override val root =
            vbox {
                addClass(Styles.loginScreen)
                text("Email: noreply.itmorobot@gmail.com")
                separator()
                text("Telegram: @Vvlad1slavV")
                text("Telegram: @japersik")
                separator()
                text("Git: https://github.com/marukhlenkodaniil/ITMO_RobotSystem_lab8")
                separator()
                text("ITMO university 2020")
            }
}
