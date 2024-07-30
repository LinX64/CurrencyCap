package domain.repository

import domain.model.main.Crypto
import kotlinx.coroutines.flow.Flow

interface ExploreRepository {
    fun search(query: String): Flow<List<Crypto>>
}