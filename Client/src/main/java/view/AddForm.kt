package view

import com.itmo.r3135.World.Color
import com.itmo.r3135.World.Product
import controller.ProductsController
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.USER
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import model.ProductsModel
import tornadofx.*

class AddForm : View("Register Customer") {
    val controller: ProductsController by inject()
    val model: ProductsModel by inject()
    val productsTable: ProductsTable by inject()
    val productsMap: ProductsMap by inject()


    override val root = form {
        fieldset("Owner", FontAwesomeIconView(USER)) {
            field("Owner name") {
                textfield(model.ownername).required()
            }
            field("Birthday") {
                datepicker(model.birthday).required()
            }
            field("Owner's eye color") {
                combobox<Color>(model.eyecolor, values = Color.values().toList()) {
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
//            field("ID") {
//                textfield(model.id).required()
//            }
            field("Name") {
                textfield(model.name).required()
            }
            field("Price") {
                textfield(model.price) {
                    filterInput { it.controlNewText.isDouble() && it.controlNewText.toDouble() >= 0 }
                    required()
                }
            }
            field("X / Y") {
                textfield(model.xcoordinate) {
                    filterInput { it.controlNewText.isDouble() }
                    required()
                }
                textfield(model.ycoordinate) {
                    filterInput { it.controlNewText.isDouble() }
                    required()
                }
            }
            field("Partnumeber") {
                textfield(model.partnumeber) {
                    required()
                }
            }
            field("Manufacture cost") {
                textfield(model.manufacturecost) {
                    filterInput { it.controlNewText.isDouble() && it.controlNewText.toDouble() >= 0 }
                    required()
                }
            }
        }

        button("Add") {
            action {
                model.commit {
                    val product = model.item
                    //вставить проверку от БД

                    controller.addProduct(product.toProduct())
                    productsMap.repaint()
                    //productsMap.root
//                    Notifications.create()
//                            .title("Customer saved!")
//                            .text("${customer.ownername} was born ${customer.birthday}\nand lives in\n${customer.xcoordinate}, ${customer.ycoordinate} ${customer.eyecolor}")
//                            .owner(this)
//                            .showInformation()
                }
            }

            enableWhen(model.valid)
        }
    }

}



