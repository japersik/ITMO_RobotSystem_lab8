package view.WorkView

import com.itmo.r3135.World.Product
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import java.lang.Math.abs

class ProductPoint(product: Product) {
    val point = Circle()
    var xReal: Double
    var yReal: Double

    init {
        xReal = product.coordinates.x
        yReal = product.coordinates.y
        point.centerX = product.coordinates.x
        point.centerY = product.coordinates.y
        point.radius = 10.0
        point.fill = Color(abs(product.userName.hashCode()%10000/(10000.toDouble())),
                abs(product.userName.hashCode()%1000/(1000.toDouble())),
                abs(product.userName.hashCode()%100/(100.toDouble())),
                1.0)
    }

    fun setXY(xGraph: Double, yGraph: Double) {
        point.centerX = xGraph
        point.centerY = yGraph
    }
}