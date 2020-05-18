package view

import tornadofx.*

class Interface : View("My View") {
    override val root = stackpane {
        prefHeight = 800.0
        prefWidth = 60.0
        gridpane {
            row {
                button("+") {
                    gridpaneConstraints {
                        marginTop = 10.0
                        marginLeft = 10.0
                    }
                    action {
                        //action
                    }
                }
            }
            row {
                button("+") {
                    gridpaneConstraints {
                        marginTop = 2.0
                        marginLeft = 10.0
                    }
                    action {
                        //action
                    }
                }
            }
            row {
                button("button") {
                    gridpaneConstraints {
                        marginTop = 2.0
                        marginLeft = 10.0
                    }
                    action {
                        //action
                    }
                }
            }
        }
    }
}
