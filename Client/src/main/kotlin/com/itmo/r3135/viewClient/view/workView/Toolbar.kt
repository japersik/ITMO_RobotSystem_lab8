package com.itmo.r3135.viewClient.view.workView

import tornadofx.*

class Toolbar : View("Toolbar") {
    override val root = stackpane {

        menubar {
            menu("File") {
                item("New").action {
                    log.info("Opening text file")
                }
                separator()
                item("Exit").action {
                    log.info("Leaving workspace")
                }
            }
            menu("Window") {
                item("Close all").action {

                }
                separator()
            }
            menu("Help") {
                item("About...")
            }
        }

        //add(RestProgressBar::class)
//        with(bottomDrawer) {
//            item( "Logs") {
//                textarea {
//                    addClass("consola")
//                    val ps = PrintStream(TextAreaOutputStream(this))
//                    java.lang.System.setErr(ps)
//                    java.lang.System.setOut(ps)
//                }
//
//            }
    }
}

//    private fun Menu.openWindowMenuItemsAtfer() {
//        editorController.editorModelList.onChange { dvm ->
//            dvm.next()
//            if (dvm.wasAdded()) {
//                dvm.addedSubList.forEach { x ->
//                    val item = MenuItem(x.title)
//                    item.action {
//                        workspace.dock(x, true)
//                    }
//                    items.add(item)
//                }
//            } else if (dvm.wasRemoved()) {
//                dvm.removed.forEach { x ->
//                    workspace.viewStack.remove(x)
//                    x.close()
//                    println(workspace.dockedComponent)
//                    val morituri = items.takeLast(items.size - 2).filter { item -> item.text.equals(x.title) }
//                    items.removeAll(morituri)
//                }
//            }
//        }
//    }

