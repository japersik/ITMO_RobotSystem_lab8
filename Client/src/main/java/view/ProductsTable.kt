package view

import com.itmo.r3135.World.Product
import tornadofx.*

class ProductsTable : View("My View") {
    override var root = gridpane {
        
    }


//    override val root = tableview () {
//        column("Id", Product::getId)
//        column("Name", Person::nameProperty)
//        bindSelected(controller.selectedPerson)
//        smartResize()
//    }
}
