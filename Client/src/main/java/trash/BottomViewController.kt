package com.itmo.r3135.controller

import com.itmo.r3135.view.BottomView
import com.itmo.r3135.view.Editor
import javafx.scene.layout.StackPane
import trash.CatSchedule
import trash.CatScheduleScope

import tornadofx.*


class BottomViewController: Controller() {

    private val view: BottomView by inject()

    val mondays = listOf(
            CatSchedule("Tom Mariano", "Meowsers", "1015 High Street", "3:00PM", "/trash/kitty/kitty1.png"),
            CatSchedule("John Hill", "Tom", "1010 8 Street", "4:00PM", "/trash/kitty/kitty2.png"),
            CatSchedule("Louise Vargas", "Mr. Whiskers", "1120 6th Street", "4:30PM", "/trash/kitty/kitty3.png"),
            CatSchedule("Dale Benton", "Pepper", "2111 8th Street", "7:00PM", "/trash/kitty/kitty4.png"),
            CatSchedule("Tucker Harrison", "Princess", "2267 8th Street", "8:00PM", "/trash/kitty/kitty5.png")
    ).observable()

    val tuesdays = listOf(
            CatSchedule("Tom Mariano", "Meowsers", "1110 6th Street", "3:00PM", "/trash/kitty/kitty1.png"),
            CatSchedule("John Hill", "Tom", "1115 6th Street", "4:00PM", "/trash/kitty/kitty2.png"),
            CatSchedule("Louise Vargas", "Mr. Whiskers", "1120 6th Street", "4:30PM", "/trash/kitty/kitty3.png"),
            CatSchedule("Dale Benton", "Pepper", "2111 8th Street", "7:00PM", "/trash/kitty/kitty4.png"),
            CatSchedule("Tucker Harrison", "Princess", "2267 8th Street", "8:00PM", "/trash/kitty/kitty5.png")
    ).observable()

    val wednesdays = listOf(
            CatSchedule("Tom Mariano", "Meowsers", "1110 6th Street", "3:00PM", "/trash/kitty/kitty1.png"),
            CatSchedule("John Hill", "Tom", "1115 6th Street", "4:00PM", "/trash/kitty/kitty2.png"),
            CatSchedule("Louise Vargas", "Mr. Whiskers", "1120 6th Street", "4:30PM", "/trash/kitty/kitty3.png"),
            CatSchedule("Dale Benton", "Pepper", "2111 8th Street", "7:00PM", "/trash/kitty/kitty4.png"),
            CatSchedule("Tucker Harrison", "Princess", "2267 8th Street", "8:00PM", "/trash/kitty/kitty5.png")
    ).observable()

    val thursdays = listOf(
            CatSchedule("Tom Mariano", "Meowsers", "1110 6th Street", "3:00PM", "/trash/kitty/kitty1.png"),
            CatSchedule("John Hill", "Tom", "1115 6th Street", "4:00PM", "/trash/kitty/kitty2.png"),
            CatSchedule("Louise Vargas", "Mr. Whiskers", "1120 6th Street", "4:30PM", "/trash/kitty/kitty3.png"),
            CatSchedule("Dale Benton", "Pepper", "2111 8th Street", "7:00PM", "/trash/kitty/kitty4.png"),
            CatSchedule("Tucker Harrison", "Princess", "2267 8th Street", "8:00PM", "/trash/kitty/kitty5.png")
    ).observable()

    val fridays = listOf(
            CatSchedule("Tom Mariano", "Meowsers", "1110 6th Street", "3:00PM", "/trash/kitty/kitty1.png"),
            CatSchedule("John Hill", "Tom", "1115 6th Street", "4:00PM", "/trash/kitty/kitty2.png"),
            CatSchedule("Louise Vargas", "Mr. Whiskers", "1120 6th Street", "4:30PM", "/trash/kitty/kitty3.png"),
            CatSchedule("Dale Benton", "Pepper", "2111 8th Street", "7:00PM", "/trash/kitty/kitty4.png"),
            CatSchedule("Tucker Harrison", "Princess", "2267 8th Street", "8:00PM", "/trash/kitty/kitty5.png")
    ).observable()

    fun changeCatAvi(catSchedule: CatSchedule) {
        view.avi.children.clear()

        val image = StackPane().apply {
            rectangle {
                width = 200.0
                height = 200.0
                fill = c("#AARRGGBB")
                opacity = 0.01
            }
            imageview(catSchedule.catImage)
        }

        view.avi.children.add(image)
    }

    fun editCatSchedule(catSchedule: CatSchedule) {
        val catScheduleScope = CatScheduleScope()
        catScheduleScope.model.item = catSchedule
        find(Editor::class, scope = catScheduleScope).openModal()
    }

    init {
        subscribe<BottomView.CatScheduleRequest> {

        }
    }
}

class CatScheduleEvent(val catSchedules: List<CatSchedule>): FXEvent()