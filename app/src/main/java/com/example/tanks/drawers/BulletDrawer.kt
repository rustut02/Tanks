package com.example.tanks.drawers

import android.graphics.Path
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.tanks.CELL_SIZE
import com.example.tanks.R
import com.example.tanks.enums.Direction
import com.example.tanks.models.Coordinate

private const val BULLET_WIDTH = 15
private const val BULLET_HEIGHT = 25

class BulletDrawer(val container:RelativeLayout) {
    fun drawBullet(myTank: View, currentDirection: Direction){
        val bullet = ImageView(container.context)
            .apply{
                this.setImageResource(R.drawable.bullet)
                this.layoutParams = RelativeLayout.LayoutParams(BULLET_WIDTH, BULLET_HEIGHT)
                val bulletCoordinate = getBulletCoordinate(this, myTank, currentDirection)
                (this.layoutParams as RelativeLayout.LayoutParams).topMargin = bulletCoordinate.top
                (this.layoutParams as RelativeLayout.LayoutParams).leftMargin = bulletCoordinate.left
                this.rotation = currentDirection.rotation
            }
        container.addView(bullet)
    }

    private fun getBulletCoordinate(bullet: ImageView, myTank: View, currentDirection: Direction): Coordinate {
        val tankLeftTopCoordinate = Coordinate(myTank.top, myTank.left)
        return when(currentDirection){
            Direction.UP ->
                Coordinate(
                    top = tankLeftTopCoordinate.top - bullet.layoutParams.height,
                    left = getDistanceToMiddleOfTank(tankLeftTopCoordinate.left, bullet.layoutParams.width))
            Direction.DOWN ->
                Coordinate(
                    top = tankLeftTopCoordinate.top + myTank.layoutParams.height,
                    left = getDistanceToMiddleOfTank(tankLeftTopCoordinate.left, bullet.layoutParams.width))
            Direction.LEFT ->
                Coordinate(
                    top = getDistanceToMiddleOfTank(tankLeftTopCoordinate.top, bullet.layoutParams.height),
                    left = tankLeftTopCoordinate.left - bullet.layoutParams.width)
            Direction.RIGHT ->
                Coordinate(
                    top = getDistanceToMiddleOfTank(tankLeftTopCoordinate.top, bullet.layoutParams.height),
                    left = tankLeftTopCoordinate.left + myTank.layoutParams.width)
        }
        return tankLeftTopCoordinate
    }

    private fun getDistanceToMiddleOfTank(startCoordinate:Int, bulletsize:Int): Int{
        return startCoordinate + (CELL_SIZE - bulletsize/2)
    }
}