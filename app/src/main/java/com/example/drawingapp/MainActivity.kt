package com.example.drawingapp

import android.app.Dialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_brush_size.*
import kotlinx.android.synthetic.main.dialog_color.*

class MainActivity : AppCompatActivity() {

    private var isEraserClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawing_view.setSizeForBrush(20F)
    }

    private fun showBrushSizeChooserDialog(){
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.window!!.setBackgroundDrawable(getDrawable(R.drawable.background_color_pallet_dialog))
        brushDialog.setTitle("Brush Size: ")

        val smallBtn = brushDialog.ib_small_brush
        smallBtn.setOnClickListener {
            drawing_view.setSizeForBrush(10F)
            brushDialog.dismiss()
        }



        val mediumBtn = brushDialog.ib_medium_brush
        mediumBtn.setOnClickListener {
            drawing_view.setSizeForBrush(20F)
            brushDialog.dismiss()
        }

        val largeBtn = brushDialog.ib_large_brush
        largeBtn.setOnClickListener {
            drawing_view.setSizeForBrush(30F)
            brushDialog.dismiss()
        }

        brushDialog.show()
    }


    
    private fun showColorPalletChooserDialog(){

        if(isEraserClicked) {
            drawing_view.setSizeForBrush(20F)
            isEraserClicked = false
        }

        val palletDiag = Dialog(this)

        palletDiag.setContentView(R.layout.dialog_color)
        palletDiag.window!!.setBackgroundDrawable(getDrawable(R.drawable.background_color_pallet_dialog))
        palletDiag.window!!.attributes.windowAnimations = R.style.animation

        palletDiag.setTitle("Color: ")

        val blackCol = palletDiag.BtnBlack
        blackCol.setOnClickListener{
            drawing_view.setColorForBrush(Color.BLACK)
            btn_colorPallet.background = getDrawable(R.drawable.ic_pallet_black)
            palletDiag.dismiss()
        }

        val redCol = palletDiag.btnRed
        redCol.setOnClickListener{
            drawing_view.setColorForBrush(Color.RED)
            btn_colorPallet.background = getDrawable(R.drawable.ic_pallet_red)
            palletDiag.dismiss()
        }

        val skinCol = palletDiag.btnSkin
        skinCol.setOnClickListener{
            drawing_view.setColorForBrush(Color.rgb(197,140,133))
            btn_colorPallet.background = getDrawable(R.drawable.ic_pallet_skin)
            palletDiag.dismiss()
        }

        val greenCol = palletDiag.btnGreen
        greenCol.setOnClickListener{
            drawing_view.setColorForBrush(Color.GREEN)
            btn_colorPallet.background = getDrawable(R.drawable.ic_pallet_green)
            palletDiag.dismiss()
        }

        val blueCol = palletDiag.btnBlue
        blueCol.setOnClickListener{
            drawing_view.setColorForBrush(Color.BLUE)
            btn_colorPallet.background = getDrawable(R.drawable.ic_pallet_blue)
            palletDiag.dismiss()
        }

        val yellowCol = palletDiag.btnYellow
        yellowCol.setOnClickListener{
            drawing_view.setColorForBrush(Color.YELLOW)
            btn_colorPallet.background = getDrawable(R.drawable.ic_pallet_yellow)
            palletDiag.dismiss()
        }

        val lollipopCol = palletDiag.btnLollipop
        lollipopCol.setOnClickListener{
            drawing_view.setColorForBrush(Color.CYAN)
            btn_colorPallet.background = getDrawable(R.drawable.ic_pallet_lollipop)
            palletDiag.dismiss()
        }

        val randomCol = palletDiag.btnRandom
        randomCol.setOnClickListener{
            drawing_view.setColorForBrush(Color.MAGENTA)
            btn_colorPallet.background = getDrawable(R.drawable.ic_pallet_random)
            palletDiag.dismiss()
        }

        palletDiag.show()
    }


    fun brushClick(view: View) {
        showBrushSizeChooserDialog()
    }

    fun palletClick(view: View) {
        showColorPalletChooserDialog()
    }

    fun eraserClick(view: View) {
        drawing_view.setColorForBrush(Color.WHITE)
        drawing_view.setSizeForBrush(60F)
        isEraserClicked = true
    }

}

