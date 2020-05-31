package com.itmo.r3135.ViewClient.view.WorkView

import com.itmo.r3135.ViewClient.controller.CoolMapController
import com.itmo.r3135.ViewClient.controller.LocaleString
import com.itmo.r3135.ViewClient.controller.LocalizationManager
import javafx.scene.control.TableColumn
import tornadofx.*


class ProductsTable : View("Table") {
    val controller: CoolMapController by inject()
    private val localizationManager: LocalizationManager by inject()

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
        root.resize(w, h)
    }

    init {
        updateLanguage()
    }

    fun updateLanguage() {
        (root.columns[0]).text = localizationManager.getNativeTitle(LocaleString.TITLE_ID)
        (root.columns[1]).text = localizationManager.getNativeTitle(LocaleString.TITLE_NAME)
        (root.columns[2]).text= localizationManager.getNativeTitle(LocaleString.TITLE_PRICE)
        (root.columns[3]).text = localizationManager.getNativeTitle(LocaleString.TITLE_X)
        (root.columns[4]).text = localizationManager.getNativeTitle(LocaleString.TITLE_Y)
        (root.columns[5]).text = localizationManager.getNativeTitle(LocaleString.TITLE_PART_NUMBER)
        (root.columns[6]).text = localizationManager.getNativeTitle(LocaleString.TITLE_UNIT_OF_MEASURE)
        (root.columns[7]).text= localizationManager.getNativeTitle(LocaleString.TITLE_MANUFACTURE_COST)
        (root.columns[8]).text= localizationManager.getNativeTitle(LocaleString.TITLE_OWNER_NAME)
        (root.columns[9]).text = localizationManager.getNativeTitle(LocaleString.TITLE_OWNER_BIRTH_DAY)
        (root.columns[10]).text = localizationManager.getNativeTitle(LocaleString.TITLE_OWNER_EYE_COLOR)
        (root.columns[11]).text = localizationManager.getNativeTitle(LocaleString.TITLE_OWNER_HAIR_COLOR)
        (root.columns[12]).text = localizationManager.getNativeTitle(LocaleString.TITLE_CREATOR)
    }
}
