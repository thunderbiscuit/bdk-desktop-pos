package me.thunderbiscuit.pos

import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.BitmapPainter
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.BinaryBitmap
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO

fun buildQRCode(address: String): ImageBitmap {

    val QRBufferedImage = buildBufferedImage(address)

    val stream = ByteArrayOutputStream()
    ImageIO.write(QRBufferedImage, "png", stream)
    val byteArray = stream.toByteArray()

    return Image.makeFromEncoded(byteArray).toComposeImageBitmap()
}

private fun buildBufferedImage(addressToEncode: String): BufferedImage? {
    return try {
        val qrCodeWriter: QRCodeWriter = QRCodeWriter()
        val bitMatrix: BitMatrix = qrCodeWriter.encode(addressToEncode, BarcodeFormat.QR_CODE, 700, 700)
        MatrixToImageWriter.toBufferedImage(bitMatrix)
    } catch (e: Throwable) {
        println("Error with QR code generation, $e")
        // Log.i(TAG, "Error with QRCode generation, $e")
        null
    }
}

