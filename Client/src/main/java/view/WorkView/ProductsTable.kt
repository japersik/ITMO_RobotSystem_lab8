package view.WorkView

import controller.ProductsController
import tornadofx.*

class ProductsTable : View("My View") {
    val controller: ProductsController by inject()

    override val root = tableview(controller.products)
    {
        bindSelected(controller.selectedProduct)

        column("Id", Products::idProperty)
        column("Name", Products::nameProperty)
        column("Price", Products::priceProperty)
        column("X coordinate", Products::xcoordinatePropetry)
        column("Y coordinate", Products::ycoordinatePropetry)
        column("Partnumber", Products::partnumeberProperty)
        column("Unit of measure", Products::unitOfMeasureProperty)
        column("Manufacture cost", Products::manufacturecostProperty)
        column("Owner's name", Products::ownernameProperty)
        column("Owner's birthday", Products::birthdayProperty)
        column("Eye color", Products::eyecolorProperty)
        column("Hair color", Products::haircolorProperty)
    }
}
