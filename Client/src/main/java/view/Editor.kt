package com.itmo.r3135.view


import javafx.scene.control.TextField
import model.CatScheduleScope
import tornadofx.*

class Editor: Fragment() {

    // cast scope
    override val scope = super.scope as CatScheduleScope

    // extract our view model from the scope
    private val model = scope.model
    var ownerNameField : TextField by singleAssign()
    var catNameField : TextField by singleAssign()
    var timeField: TextField by singleAssign()

    override val root = hbox {
        form {
            fieldset("Edit person") {
                field("Owner") {
                    textfield(model.ownerName) {
                        ownerNameField = this
                    }
                }
                field("Cat") {
                    textfield(model.catName) {
                        catNameField = this
                    }
                }
                field("Time") {
                    textfield(model.time) {
                        timeField = this
                    }
                }
                button("Save") {
                    enableWhen(model.dirty)
                    action {
                        save()
                    }
                }
            }
        }
    }

    private fun save() {
        // flush changes from the text field into the model
        model.commit()

        // edited cat schedule is contained in the model
        val catSchedule = model.item
        println("Saving Changes: ${catSchedule.ownerName} / ${catSchedule.catName} / ${catSchedule.time}")
        close()
    }

}