package com.example.tanks


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.example.tanks.drawers.BulletDrawer
import com.example.tanks.drawers.ElementsDrawer
import com.example.tanks.drawers.GridDrawer
import com.example.tanks.drawers.TankDrawer
import com.example.tanks.enums.Direction
import com.example.tanks.enums.Material
import com.example.tanks.models.Coordinate
import kotlinx.android.synthetic.main.activity_main.*

const val CELL_SIZE = 50
const val VERTICAL_CELL_AMOUNT = 27//27
const val HORIZONTAL_CELL_AMOUNT = 11//12
const val VERTICAL_MAX_SIZE = CELL_SIZE * VERTICAL_CELL_AMOUNT
const val HORIZONTAL_MAX_SIZE = CELL_SIZE * HORIZONTAL_CELL_AMOUNT


class MainActivity : AppCompatActivity() {

    private var editMode = false;

    private val gridDrawer by lazy{
        GridDrawer(container);
    }

    private val elementsDrawer by lazy{
        ElementsDrawer(container)
    }

    private val tankDrawer by lazy{
        TankDrawer(container)
    }

    private val bulletDrawer by lazy{
        BulletDrawer(container)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container.layoutParams = RelativeLayout.LayoutParams(VERTICAL_MAX_SIZE, HORIZONTAL_MAX_SIZE)
        editor_clear.setOnClickListener { elementsDrawer.currentMaterial = Material.EMPTY }
        editor_brick.setOnClickListener { elementsDrawer.currentMaterial = Material.BRICK }
        editor_concrete.setOnClickListener { elementsDrawer.currentMaterial = Material.CONCRETE }
        editor_grass.setOnClickListener { elementsDrawer.currentMaterial = Material.GRASS }
        container.setOnTouchListener { _, event ->
            elementsDrawer.onTouchContainer(event.x, event.y)
            return@setOnTouchListener true
        }

        btn_up.setOnClickListener{ tankDrawer.move(myTank, Direction.UP, elementsDrawer.elementsOnContainer) }
        btn_down.setOnClickListener{ tankDrawer.move(myTank, Direction.DOWN, elementsDrawer.elementsOnContainer) }
        btn_left.setOnClickListener{ tankDrawer.move(myTank, Direction.LEFT, elementsDrawer.elementsOnContainer) }
        btn_right.setOnClickListener{ tankDrawer.move(myTank, Direction.RIGHT, elementsDrawer.elementsOnContainer) }
        btn_shot.setOnClickListener { bulletDrawer.drawBullet(myTank, tankDrawer.currentDirection) }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_settings ->{
                switchEditMode()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun switchEditMode(){
        if(editMode) {
            gridDrawer.removeGrid()
            materials_container.visibility = GONE
        }else {
            gridDrawer.drawGrid()
            materials_container.visibility = VISIBLE
        }
        editMode = !editMode
    }


}
