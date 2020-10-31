package com.example.drawingapp

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_brush_size.*
import kotlinx.android.synthetic.main.dialog_color.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

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

    private fun requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).toString())){
            Toast.makeText(this, "You need specific permissions to add external storage!", Toast.LENGTH_SHORT).show()
        }

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission granted, now you can read the storage!", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "OOPS, you just denied the permission!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun isReadStorageAllowed(): Boolean{
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun getBitmapFromView(view: View): Bitmap{
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background

        if(bgDrawable != null)
            bgDrawable.draw(canvas)
        else
            canvas.drawColor(Color.WHITE)

        view.draw(canvas)

        return returnedBitmap
    }

    @Suppress("DEPRECATION")
    private inner class BitmapAsyncTask(val mBitmap: Bitmap): AsyncTask<Any, Void, String>(){
        override fun doInBackground(vararg params: Any?): String {
            var result = ""
            if(mBitmap != null){
                try {
                    val bytes = ByteArrayOutputStream()
                    mBitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes)
                    val f = File(externalCacheDir!!.absoluteFile.toString() + File.separator + "DrawingApp_" + System.currentTimeMillis()/1000 + ".png")
                    val fos = FileOutputStream(f)
                    fos.write(bytes.toByteArray())
                    fos.close()
                    result = f.absolutePath
                }catch (e: Exception){
                    result = ""
                    e.printStackTrace()
                }
            }

            return result
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if(result!!.isNotEmpty())
                Toast.makeText(this@MainActivity, "File saved successfully: $result", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this@MainActivity, "Something went wrong while saving the file!", Toast.LENGTH_SHORT).show()
        }

    }

    companion object{
        private const val STORAGE_PERMISSION_CODE = 1
        private const val GALLERY = 2
    }

    fun galleryBtnClicked(view: View) {
        if(isReadStorageAllowed()){

            val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickPhotoIntent, GALLERY)

        } else
            requestStoragePermission()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == GALLERY){

                try {
                    if(data!!.data != null){
                        iv_background.visibility = View.VISIBLE
                        iv_background.setImageURI(data.data)
                    }
                    else
                        Toast.makeText(this, "Error in parsing image or it must be corrupted!", Toast.LENGTH_SHORT).show()
                } catch (e: Exception){ e.printStackTrace() }

            }
        }
    }

    fun undoClick(view: View) {
        drawing_view.onClickUndo()
    }

    @Suppress("DEPRECATION")
    fun saveClick(view: View) {
        if(isReadStorageAllowed())
            BitmapAsyncTask(getBitmapFromView(fl_drawing_view_container)).execute()
        else
            requestStoragePermission()
    }

}

