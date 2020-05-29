package com.itmo.r3135.viewClient.view.workView

import com.itmo.r3135.World.Product
import javafx.scene.Group
import javafx.scene.effect.DropShadow
import javafx.scene.paint.Color
import javafx.scene.shape.SVGPath
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.util.Duration
import tornadofx.*
import java.lang.Math.abs

class ProductPoint(product: Product) {
    var xReal: Double
    var yReal: Double
    val group = Group()

    init {
        xReal = product.coordinates.x
        yReal = product.coordinates.y

        val svgpath = SVGPath()
        svgpath.content = "M-20,15 L-20,-10 L20,-10 L20,15  L-20,15 M-20,-10 L-15,-20 L15,-20, L20,-10"
        svgpath.stroke = c(abs(product.userName.hashCode() % 10000 / (10000.toDouble())),
                abs(product.userName.hashCode() % 1000 / (1000.toDouble())),
                abs(product.userName.hashCode() % 100 / (100.toDouble())),
                1.0)
        svgpath.fill = Color.BURLYWOOD
        svgpath.strokeWidth = 3.0
        svgpath.effect = DropShadow()
        val text = Text()
        text.font = Font.font(9.0)
        text.x = -17.0
        text.style = "-fx-text-alignment:center"
        text.text = "Box \n ${product.id}"
        group.add(svgpath)
        group.add(text)
        group.translateX = product.coordinates.x
        group.translateY = product.coordinates.y
        sequentialTransition {
            timeline {
                keyframe(Duration.seconds(0.5)) {
                    keyvalue(group.scaleXProperty(), 1.5)
                    keyvalue(group.scaleYProperty(), 0.5)
                }
            }
            timeline {
                keyframe(Duration.seconds(0.5)) {
                    keyvalue(group.scaleXProperty(), 1.0)
                    keyvalue(group.scaleYProperty(), 1.0)
                }
            }
        }
    }

    fun removeAnimation() {
        sequentialTransition {
            timeline {
                keyframe(Duration.seconds(1.0)) {
                    keyvalue(group.scaleXProperty(), 3.5)
                    keyvalue(group.scaleYProperty(), 3.5)
                }
            }
            timeline {
                keyframe(Duration.seconds(0.25)) {
                    keyvalue(group.scaleXProperty(), 0.0)
                    keyvalue(group.scaleYProperty(), 0.0)
                }
            }
        }
    }

    fun setXY(xGraph: Double, yGraph: Double) {
        group.translateX = xGraph
        group.translateY = yGraph
    }


    fun updateXY(xGraph: Double, yGraph: Double) {
        timeline {
            keyframe(Duration.seconds(0.75)) {
                keyvalue(group.translateXProperty(), xGraph)
                keyvalue(group.translateYProperty(), yGraph)
            }
        }
    }
}