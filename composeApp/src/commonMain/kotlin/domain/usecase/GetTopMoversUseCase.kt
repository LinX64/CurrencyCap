package domain.usecase

import data.repository.MainRepository
import domain.model.DataDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTopMoversUseCase(
    private val mainRepository: MainRepository
) {

    suspend operator fun invoke(): Flow<List<DataDao>> {
        return mainRepository.getCoinCapRates().map { rates ->
            rates
                .sortedByDescending { it.rateUsd }
                .take(4)
        }
    }
}
