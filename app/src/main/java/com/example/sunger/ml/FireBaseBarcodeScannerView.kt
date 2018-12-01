package com.example.sunger.ml

import android.content.Context
import android.hardware.Camera
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.vision.barcode.internal.NativeBarcode
import com.google.android.gms.vision.barcode.internal.NativeBarcodeDetector
import com.google.android.gms.vision.barcode.internal.client.BarcodeDetectorOptions
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import me.dm7.barcodescanner.core.BarcodeScannerView
import java.nio.ByteBuffer
import java.util.*


class FireBaseBarcodeScannerView : BarcodeScannerView {

    private lateinit var nativeBarcodeDetector: NativeBarcodeDetector

    internal var handler = Handler(Looper.getMainLooper())

    private var mResultHandler: ResultHandler? = null

    private var isCameraOpen = false

    fun setResultHandler(resultHandler: ResultHandler) {
        mResultHandler = resultHandler
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        init()
    }

    private fun init() {
        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(
                FirebaseVisionBarcode.FORMAT_ALL_FORMATS
//                ,
//                FirebaseVisionBarcode.FORMAT_CODE_128,
//                FirebaseVisionBarcode.FORMAT_CODE_39,
//                FirebaseVisionBarcode.FORMAT_CODE_93
            ).build()
//        firebaseVisionBarcodeDetector = FirebaseVision.getInstance()
//            .getVisionBarcodeDetector(options)

        nativeBarcodeDetector = NativeBarcodeDetector(BarcodeDetectorOptions())
    }

    override fun onPreviewFrame(data: ByteArray?, camera: Camera) {

        if (data == null || data.isEmpty()) {
            camera.setOneShotPreviewCallback(this)
            return
        }
        val parameters = camera.parameters
        val size = parameters.previewSize
        val width = size.width
        val height = size.height

        val metadata = FirebaseVisionImageMetadata.Builder()
            .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
            .setWidth(width)
            .setHeight(height)
            .build()

        val buffer = ByteBuffer.wrap(data)

        val image = FirebaseVisionImage.fromByteBuffer(buffer, metadata)

        val result = nativeBarcodeDetector.decode(width, height, data)
        if (result == null || result.isEmpty()) {
            resetOneShotPreviewCallback(camera)
            return
        }
        handler.post {
            mResultHandler!!.handleResult(result.asList())
            resetOneShotPreviewCallback(camera)
        }

        //        firebaseVisionBarcodeDetector.detectInImage(image)
//            .addOnSuccessListener(OnSuccessListener { firebaseVisionBarcodes ->
//                if (firebaseVisionBarcodes.isEmpty()) {
//                    resetOneShotPreviewCallback(camera)
//                    return@OnSuccessListener
//                }
//                handler.post {
//                    mResultHandler!!.handleResult(firebaseVisionBarcodes)
//
//                    resetOneShotPreviewCallback(camera)
//                }
//            }).addOnFailureListener {e->
//
//                    e.printStackTrace()
//                    resetOneShotPreviewCallback(camera)
//                 }
    }

    override fun startCamera() {
        super.startCamera()
        isCameraOpen = true
    }

    override fun stopCamera() {
        super.stopCamera()
        isCameraOpen = false
    }

    private fun resetOneShotPreviewCallback(camera: Camera?) {
        if (camera == null || !isCameraOpen) {
            return
        }
        try {
            camera.setOneShotPreviewCallback(this@FireBaseBarcodeScannerView)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    interface ResultHandler {
        fun handleResult(rawResult: List<NativeBarcode>)
    }
}
