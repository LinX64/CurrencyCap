package ui.common

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import di.SETTINGS_PREFERENCES
import java.io.File
import java.text.DecimalFormat
import java.util.Locale

actual fun formatCurrentTotal(currentTotal: Long): String {
    val decimalFormat = DecimalFormat("$#,###")
    return decimalFormat.format(currentTotal)
}

actual fun formatToPrice(price: Double): String {
    if (price == price.toLong().toDouble()) {
        return String.format(Locale.US, "%,d", price.toLong())
    }

    val formattedPrice = String.format(Locale.US, "%,.2f", price).trimEnd('0').trimEnd('.')
    return formattedPrice
}

actual fun getSettingsPreferencesPath(): String {
    return File(applicationContext.filesDir, SETTINGS_PREFERENCES).absolutePath
}

actual class DecimalFormat {
    actual fun format(double: Double): String {
        val df = DecimalFormat()
        df.isGroupingUsed = false
        df.maximumFractionDigits = 2
        df.isDecimalSeparatorAlwaysShown = false
        return df.format(double)
    }
}

@Composable
actual fun SendMail(to: String, subject: String) {
    val context = LocalContext.current
    try {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        val chooserIntent = Intent.createChooser(intent, "Send Email")
        context.startActivity(chooserIntent)
    } catch (e: ActivityNotFoundException) {
        println("No email app found")
    } catch (t: Throwable) {
        println("Error sending email: ${t.message}")
    }
}