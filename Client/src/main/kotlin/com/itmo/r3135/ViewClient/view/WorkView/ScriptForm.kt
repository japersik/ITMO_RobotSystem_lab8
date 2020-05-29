import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.System.Tools.ScriptReader
import com.itmo.r3135.ViewClient.controller.ConnectController
import com.itmo.r3135.ViewClient.view.Styles.Companion.addform
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.property.SimpleStringProperty
import javafx.stage.FileChooser
import tornadofx.*


class ScriptForm : View("Script form") {
    val connectController: ConnectController by inject()
    private val model = object : ViewModel() {
        val filePath = bind { SimpleStringProperty() }
    }
    override val root = vbox {
        addClass(addform)
        fieldset("Select script file", FontAwesomeIconView(FontAwesomeIcon.FILE)) {
            hbox {
                text(model.filePath) {
                    wrappingWidth = 300.0
                }
                button("Open") {
                    setOnAction {
                        val fileChooser = FileChooser();
                        val file = fileChooser.showOpenDialog(this@ScriptForm.currentWindow)
                        model.filePath.value = file?.toString()
                    }

                }
            }

            hbox {
                button("Go") {
                    isDefaultButton = true
                    action {
                        if (model.filePath.value != null) {
                            val commands = ScriptReader.read(model.filePath.value)
                            if (commands.isNotEmpty()) {
                                val command = Command(CommandList.EXECUTE_SCRIPT, commands)
                                connectController.sendReceiveManager.send(command)
                            }
                        }
                    }
                    enableWhen(model.valid)
                }
                button("Cancel") {
                    isCancelButton = true
                    action {
                        close()
                    }
                }
            }

        }
    }
}
