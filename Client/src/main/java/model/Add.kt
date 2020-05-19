package model

import com.itmo.r3135.World.Color
import javafx.beans.property.DoubleProperty
import javafx.beans.property.ObjectProperty
import javafx.beans.property.Property
import javafx.beans.property.StringProperty
import tornadofx.*
import java.time.LocalDate

class Customer {
    var name by property<String>()
    fun nameProperty() = getProperty(Customer::name)

    var birthday by property<LocalDate>()
    fun birthdayProperty() = getProperty(Customer::birthday)

    var street by property<String>()
    fun streetProperty() = getProperty(Customer::street)

    var zip by property<String>()
    fun zipProperty() = getProperty(Customer::zip)

    var city by property<String>()
    fun cityProperty() = getProperty(Customer::city)

    var eyecolor by property<Color>()
    fun eyecolorProperty() = getProperty(Customer::eyecolor)

    var haircolor by property<Color>()
    fun haircolorProperty() = getProperty(Customer::haircolor)

    var price by property<Number>()
    fun priceProperty() = getProperty(Customer::price)

    override fun toString() = name
}

class CustomerModel : ItemViewModel<Customer>(Customer()) {
    val name: StringProperty = bind { item?.nameProperty() }
    val birthday: Property<LocalDate> = bind { item?.birthdayProperty() }
    val street: StringProperty = bind { item?.streetProperty() }
    val zip: StringProperty = bind { item?.zipProperty() }
    val city: StringProperty = bind { item?.cityProperty() }
    val eyeColor: Property<Color> = bind { item?.eyecolorProperty() }
    val haircolor: Property<Color> = bind { item?.haircolorProperty() }
    val price: ObjectProperty<Number> = bind { item?.priceProperty() }
}

