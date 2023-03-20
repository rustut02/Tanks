package com.example.tanks.drawers

import android.view.View
import android.widget.RelativeLayout
import com.example.tanks.CELL_SIZE
import com.example.tanks.HORIZONTAL_MAX_SIZE
import com.example.tanks.VERTICAL_MAX_SIZE
import com.example.tanks.enums.Direction
import com.example.tanks.models.Coordinate
import com.example.tanks.models.Element

class TankDrawer(val container: RelativeLayout) {
    var currentDirection = Direction.DOWN

    fun move(myTank: View, direction: Direction, elementsOnContainer:List<Element>) {
        var layoutParams = myTank.layoutParams as RelativeLayout.LayoutParams
        val currentCoordinate = Coordinate(layoutParams.topMargin, layoutParams.leftMargin)
        currentDirection = direction
        myTank.rotation = direction.rotation
        when(direction){
            Direction.UP -> {
                if(layoutParams.topMargin > 0)
                    (myTank.layoutParams as RelativeLayout.LayoutParams).topMargin -= CELL_SIZE
            }
            Direction.DOWN -> {
                if(layoutParams.topMargin + myTank.height < HORIZONTAL_MAX_SIZE)
                    (myTank.layoutParams as RelativeLayout.LayoutParams).topMargin += CELL_SIZE
            }
            Direction.RIGHT -> {
                if(layoutParams.leftMargin + myTank.width < VERTICAL_MAX_SIZE)
                    (myTank.layoutParams as RelativeLayout.LayoutParams).leftMargin += CELL_SIZE
            }
            Direction.LEFT-> {
                if(layoutParams.leftMargin > 0)
                    (myTank.layoutParams as RelativeLayout.LayoutParams).leftMargin -= CELL_SIZE
            }
        }
        val nextCoordinate = Coordinate(layoutParams.topMargin, layoutParams.leftMargin)
        if(checkTankCanMoveTroughBorder(nextCoordinate,myTank)
            && checkTankCanMoveTroughMaterial(nextCoordinate, elementsOnContainer)){
            container.removeView(myTank)
            container.addView(myTank,0)
        }else{
            (myTank.layoutParams as RelativeLayout.LayoutParams).topMargin = currentCoordinate.top
            (myTank.layoutParams as RelativeLayout.LayoutParams).leftMargin = currentCoordinate.left
        }
    }

    private fun getElementCoordinate(coordinate: Coordinate, elementsOnContainer:List<Element>)=
        elementsOnContainer.firstOrNull{it.coordinate == coordinate}

    private fun checkTankCanMoveTroughMaterial(coordinate: Coordinate, elementsOnContainer:List<Element>):Boolean{
        getTankCoordinates(coordinate).forEach{
            val element = getElementCoordinate(it, elementsOnContainer)
            if(element != null && !element.material.tankCanGoTrough) {
                return false
            }
        }
        return true
    }

    private fun checkTankCanMoveTroughBorder(coordinate: Coordinate, myTank: View):Boolean{
        if(coordinate.top >= 0
            && coordinate.top + myTank.height <= HORIZONTAL_MAX_SIZE
            && coordinate.left >= 0
            && coordinate.left + myTank.width <= VERTICAL_MAX_SIZE
        ){
            return true
        }
        return false
    }

    private fun getTankCoordinates(topLeftCoordinate: Coordinate):List<Coordinate>{
        val coordinateList = mutableListOf<Coordinate>()
        coordinateList.add(topLeftCoordinate)
        coordinateList.add(Coordinate(topLeftCoordinate.top + CELL_SIZE, topLeftCoordinate.left))
        coordinateList.add(Coordinate(topLeftCoordinate.top, topLeftCoordinate.left + CELL_SIZE))
        coordinateList.add(Coordinate(topLeftCoordinate.top + CELL_SIZE, topLeftCoordinate.left + CELL_SIZE))
        return coordinateList
    }
}