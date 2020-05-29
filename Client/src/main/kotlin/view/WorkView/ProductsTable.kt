package view.WorkView

import com.itmo.r3135.app.Styles.Companion.table
import controller.CoolMapController
import controller.ProductsController
import tornadofx.*

class ProductsTable : View("My View") {
    //    val controller: CoolMapController by inject()
    val controller: CoolMapController by inject()

    override val root = tableview(controller.productssearh)
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
        column("Creator", Products::userNameProperty)
    }
}
