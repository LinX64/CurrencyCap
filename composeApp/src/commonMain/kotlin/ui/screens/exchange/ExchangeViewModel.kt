package ui.screens.exchange

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import ui.common.MviViewModel

class ExchangeViewModel(

) : MviViewModel<ExchangeViewEvent, ExchangeState, ExchangeNavigationEffect>(ExchangeState.Loading) {
    private val fromValue = MutableStateFlow("")
    private val toValue = MutableStateFlow("")
    private val amountValue = MutableStateFlow("")
    val convertResult: MutableState<String> = mutableStateOf("")

    override fun handleEvent(event: ExchangeViewEvent) {
        TODO("Not yet implemented")
    }

    fun onAmountChange(amount: String) {
        amountValue.value = amount
    }

    fun onFromChange(from: String) {
        fromValue.value = from.split(" ")[2]
    }

    fun onToChange(to: String) {
        toValue.value = to.split(" ")[2]
    }

    fun onConvertClick() {
        if (fromValue.value.isEmpty() ||
            toValue.value.isEmpty() ||
            amountValue.value.isEmpty()
        ) {
            return
        }
//        val rates = (liveFiats.value as ConvertUiState.Success).rates
//        if (rates.isNotEmpty()) {
//            val result = convertRatesUseCaseImpl(
//                rates = rates,
//                from = fromValue.value,
//                to = toValue.value,
//                amount = amountValue.value
//            )
//            convertResult.value = result
//        }
    }


}