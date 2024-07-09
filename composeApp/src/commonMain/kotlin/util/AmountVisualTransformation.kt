package util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class AmountVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text
        val formattedText = formatWithThousandSeparators(originalText)

        return TransformedText(
            AnnotatedString(formattedText),
            ThousandSeparatorsOffsetMapping(originalText, formattedText)
        )
    }

    private fun formatWithThousandSeparators(text: String): String {
        return text.reversed()
            .chunked(3)
            .joinToString(".")
            .reversed()
    }

    private class ThousandSeparatorsOffsetMapping(
        private val originalText: String,
        private val formattedText: String
    ) : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            val separatorsBeforeOffset = (offset - 1) / 3
            return (offset + separatorsBeforeOffset).coerceAtMost(formattedText.length)
        }

        override fun transformedToOriginal(offset: Int): Int {
            val separatorsBeforeOffset = formattedText.take(offset).count { it == '.' }
            return (offset - separatorsBeforeOffset).coerceAtMost(originalText.length)
        }
    }
}