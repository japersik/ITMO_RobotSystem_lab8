package view.WorkView

import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.app.Styles
import controller.ConnectController
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.property.SimpleStringProperty
import tornadofx.*


class RemoveForm : View("Remove Element") {
    val connectController: ConnectController by inject()
    private val model = object : ViewModel() {
        val id = bind { SimpleStringProperty() }
    }
    override val root = form {
        addClass(Styles.addform)
        fieldset("Remove form", FontAwesomeIconView(FontAwesomeIcon.USER)) {
            field("ID of Product") {
                textfield(model.id) {
                    required()
                    filterInput {
                        it.controlNewText.isInt()
                    }
                }
            }
            button("Remove") {
                action {
                    model.commit {
                        connectController.sendReceiveManager.send(Command(CommandList.REMOVE_BY_ID, model.id.value.toInt()))
                        close()
                    }
                }
                enableWhen(model.valid)
            }
        }
    }
}