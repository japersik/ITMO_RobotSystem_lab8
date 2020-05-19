package model

import com.itmo.r3135.World.Color
import javafx.beans.property.ObjectProperty
import javafx.beans.property.Property
import javafx.beans.property.StringProperty
import tornadofx.*
import java.time.LocalDate

class Customer {
    var ownername by property<String>()
    fun ownernameProperty() = getProperty(Customer::ownername)

    var birthday by property<LocalDate>()
    fun birthdayProperty() = getProperty(Customer::birthday)

    var eyecolor by property<Color>()
    fun eyecolorProperty() = getProperty(Customer::eyecolor)

    var haircolor by property<Color>()
    fun haircolorProperty() = getProperty(Customer::haircolor)

    var price by property<Number>()
    fun priceProperty() = getProperty(Customer::price)

    var ycoordinate by property<Number>()
    fun ycoordinatePropetry() = getProperty(Customer::ycoordinate)

    var xcoordinate by property<Number>()
    fun xcoordinatePropetry() = getProperty(Customer::xcoordinate)

    var partnumeber by property<String>()
    fun partnumeberProperty() = getProperty(Customer::partnumeber)

    var manufacturecost by property<Number>()
    fun manufacturecostProperty() = getProperty(Customer::manufacturecost)

    var name by property<String>()
    fun nameProperty() = getProperty(Customer::name)


    override fun toString() = ownername
}

class CustomerModel : ItemViewModel<Customer>(Customer()) {
    val ownername: StringProperty = bind { item?.ownernameProperty() }
    val birthday: Property<LocalDate> = bind { item?.birthdayProperty() }
    val eyeColor: Property<Color> = bind { item?.eyecolorProperty() }
    val haircolor: Property<Color> = bind { item?.haircolorProperty() }
    val price: ObjectProperty<Number> = bind { item?.priceProperty() }
    val ycoordinate : ObjectProperty<Number> = bind { item?.ycoordinatePropetry() }
    val xcoordinate : ObjectProperty<Number> = bind { item?.xcoordinatePropetry() }
    val partnumeber: StringProperty = bind { item?.partnumeberProperty() }
    val manufacturecost: ObjectProperty<Number> = bind { item?.manufacturecostProperty() }
    val name: StringProperty = bind { item?.nameProperty() }
}

