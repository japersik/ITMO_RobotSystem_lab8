package com.itmo.r3135.ViewClient.controller

enum class LocaleString(var stringLocale: String) {

    //Страны и языки
    LANGUAGE_RU("ru"),
    COUNTRY_RU("RU"),
    LANGUAGE_FI("fi"),
    COUNTRY_FI("FI"),
    LANGUAGE_FR("fr"),
    COUNTRY_FR("FR"),
    LANGUAGE_ES("es"),
    COUNTRY_HN("HN"),
    LANGUAGE_EN("en"),
    COUNTRY_US("US"),
    //Окно соединения

    TITLE_HOST("host"),
    TITLE_PORT("port"),
    BUTTON_PING("ping"),

    //Окно авторизации
    TITLE_LOGIN("login"),
    TITLE_EMAIL("email"),
    TITLE_PASSWORD("password"),
    TITLE_CODE("code"),
    BUTTON_CANCEL("cancel"),
    BUTTON_LOGIN("sign.in"),
    BUTTON_REG("sign.up"),
    BUTTON_CODE("send.code"),

    //Интерфейс
    BUTTON_ADD("add"),
    BUTTON_ADD_TOOLTIP("add.tooltip"),
    BUTTON_REMOVE("remove"),
    BUTTON_REMOVE_TOOLTIP("remove.tooltip"),
    BUTTON_CLEAR("clear"),
    BUTTON_CLEAR_TOOLTIP("clear.tooltip"),
    BUTTON_SCRIPT("script"),
    BUTTON_SCRIPT_TOOLTIP("script.tooltip"),
    BUTTON_ADD_IF_MIN("add.if.min"),
    BUTTON_ADD_IF_MIN_TOOLTIP("add.if.min.tooltip"),
    BUTTON_UPDATE("update"),
    BUTTON_UPDATE_TOOLTIP("update.tooltip"),
    BUTTON_LOGOUT("logout"),
    //Информация о объекте
    TITLE_ID("id"),
    TITLE_NAME("name"),
    TITLE_PRICE("price"),
    TITLE_X("x.coordinate"),
    TITLE_Y("y.coordinate"),
    TITLE_PART_NUMBER("part.number"),
    TITLE_UNIT_OF_MEASURE("unit.of.measure"),
    TITLE_MANUFACTURE_COST("manufacture.cost"),
    TITLE_OWNER_NAME("owner.name"),
    TITLE_OWNER_BIRTH_DAY("owner.birth.day"),
    TITLE_OWNER_EYE_COLOR("owner.eye.color"),
    TITLE_OWNER_HAIR_COLOR("owner.hair.color"),
    TITLE_CREATOR("creator"),
    TITLE_OWNER("owner"),
    TITLE_PRODUCT("product"),
    TITLE_SELECT_ID("select.id"),

    //toolbar
    TITLE_LANGUAGE("language"),
    TITLE_HELP("help"),
    TITLE_CLOSE("close"),
    TITLE_EXIT("exit"),
    TITLE_USER_DATA("user.data"),


}