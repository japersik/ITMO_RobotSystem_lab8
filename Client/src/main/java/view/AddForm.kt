package view

import com.itmo.r3135.World.Color
import com.itmo.r3135.app.Styles.Companion.zip
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.HOME
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.USER
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import model.CustomerModel
import org.controlsfx.control.Notifications
import tornadofx.*
import javax.xml.bind.Unmarshaller

class AddForm : View("Register Customer") {
    val model: CustomerModel by inject()

    override val root = form {
        fieldset("Owner", FontAwesomeIconView(USER)) {
            field("Name") {
                textfield(model.name).required()
            }
            field("Birthday") {
                datepicker(model.birthday)
            }
            field("Owner's eye color") {
                combobox<Color>(model.eyeColor, values = Color.values().toList()) {
                    required()
                }
            }
            field("Owner's hair color") {
                combobox<Color>(model.haircolor, values = Color.values().toList()) {
                    required()
                }
            }
        }

        fieldset("Products", FontAwesomeIconView(FontAwesomeIcon.APPLE)) {
            field("Price") {
                textfield(model.price) {
                    filterInput { it.controlNewText.isDouble() && it.controlNewText.toDouble()>=0 }
                    required()
                }

            }

        }

        fieldset("Address", FontAwesomeIconView(HOME)) {
            field("Street") {
                textfield(model.street).required()
            }
            field("Zip / City") {
                textfield(model.zip) {
                    addClass(zip)
                    required()
                }
                textfield(model.city).required()
            }
        }

        button("Add") {
            action {
                model.commit {
                    val customer = model.item
                    Notifications.create()
                            .title("Customer saved!")
                            .text("${customer.name} was born ${customer.birthday}\nand lives in\n${customer.street}, ${customer.zip} ${customer.city}")
                            .owner(this)
                            .showInformation()
                }
            }

            enableWhen(model.valid)
        }
    }

}

