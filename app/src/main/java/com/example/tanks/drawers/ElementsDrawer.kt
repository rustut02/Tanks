package com.example.tanks.drawers

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.tanks.CELL_SIZE
import com.example.tanks.HORIZONTAL_MAX_SIZE
import com.example.tanks.R
import com.example.tanks.VERTICAL_MAX_SIZE
import com.example.tanks.enums.Direction
import com.example.tanks.enums.Material
import com.example.tanks.models.Coordinate
import com.example.tanks.models.Element
import kotlinx.android.synthetic.main.activity_main.*

class ElementsDrawer(val container: RelativeLayout) {

    var currentMaterial = Material.EMPTY
    val elementsOnContainer = mutableListOf<Element>()

    fun onTouchContainer(x: Float, y: Float){
        val topMargin = y.toInt() - (y.toInt()% CELL_SIZE)
        val leftMargin = x.toInt() - (x.toInt()% CELL_SIZE)
        val coordinate = Coordinate(topMargin, leftMargin)
        if(currentMaterial == Material.EMPTY){
            eraseView(coordinate)
        }else{
            drawOrReplaceView(coordinate)
        }
    }

    private fun getElementCoordinate(coordinate: Coordinate)=
        elementsOnContainer.firstOrNull{it.coordinate == coordinate}

    private fun drawOrReplaceView(coordinate: Coordinate){
        val viewOnCoordinate = getElementCoordinate(coordinate)
        if(viewOnCoordinate == null){
            drawView(coordinate)
            return
        }
        if(viewOnCoordinate.material!=currentMaterial){
            replaceView(coordinate)
        }
    }

    private fun replaceView(coordinate: Coordinate){
        eraseView(coordinate)
        drawView(coordinate)
    }

    private fun eraseView(coordinate: Coordinate){
        val elementsOnCoordinate = getElementCoordinate(coordinate)
        if(elementsOnCoordinate!=null){
            val erasingView = container.findViewById<View>(elementsOnCoordinate.viewId)
            container.removeView(erasingView)
            elementsOnContainer.remove(elementsOnCoordinate)
        }
    }


    fun drawView(coordinate: Coordinate){
        val view = ImageView(container.context)
        val layoutParams = RelativeLayout.LayoutParams(CELL_SIZE, CELL_SIZE)
        when(currentMaterial){
            Material.EMPTY -> {}
            Material.BRICK -> view.setImageResource(R.drawable.brick)
            Material.CONCRETE -> view.setImageResource(R.drawable.concrete)
            Material.GRASS -> view.setImageResource(R.drawable.grass)
        }
        layoutParams.topMargin = coordinate.top
        layoutParams.leftMargin = coordinate.left
        val viewId = View.generateViewId()
        view.id = viewId
        view.layoutParams = layoutParams
        container.addView(view)
        elementsOnContainer.add(Element(viewId, currentMaterial, coordinate))
    }

}