package view.WorkView

import controller.CoolMapController
import javafx.scene.layout.Pane
import tornadofx.*


class CoolMap : View("My View") {
    val controller: CoolMapController by inject()
    val p: Pane = pane {
        minWidth = 1500.0
        minHeight = 600.0
        maxWidth = 1500.0
        maxHeight = 600.0
    }
    override val root = p

    init {
        val border = controller.border
        var i = border
        while (i <= p.minWidth-border) {
            println(i)
            p.line(i, border, i, p.minHeight-border)
            i += 50
        }
        p.line(p.minWidth-border, border, p.minWidth-border, p.minHeight-border)
        i = border
        while (i <= p.minHeight-border) {
            println(i)
            p.line(border, i, p.minWidth-border, i)
            i += 50
        }
        p.line(border, p.minHeight-border, p.minWidth-border, p.minHeight-border)
    }

    fun clear() {
    }

}



