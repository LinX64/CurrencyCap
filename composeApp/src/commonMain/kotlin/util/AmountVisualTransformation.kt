package util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import kotlin.math.max

internal class AmountVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text
        val formattedText = StringBuilder()

        originalText.reversed()
            .chunked(3)
            .joinToString(".")
            .reversed().forEach {
                formattedText.append(it)
            }

        val out = formattedText.toString()
        val offsetTranslator = ThousandSeparatorOffsetMapping(originalText, out)
        return TransformedText(AnnotatedString(out), offsetTranslator)
    }

    private class ThousandSeparatorOffsetMapping(
        private val original: String,
        private val transformed: String
    ) : OffsetMapping {

        override fun originalToTransformed(offset: Int): Int {
            val adjustment = max(0, transformed.length - original.length)
            return offset + adjustment
        }

        override fun transformedToOriginal(offset: Int): Int {
            val adjustment = max(0, transformed.length - original.length)
            return offset - adjustment
        }
    }
}
