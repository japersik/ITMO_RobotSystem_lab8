package com.itmo.r3135.ViewClient.view.WorkView

import com.itmo.r3135.ViewClient.controller.CoolMapController
import tornadofx.*

class ProductsTable : View("My View") {
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

    fun size(w: Double, h: Double) {
        root.resize(w,h)
    }

}
