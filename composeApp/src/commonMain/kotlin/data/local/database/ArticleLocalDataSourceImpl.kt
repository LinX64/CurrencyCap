package data.local.database

import data.local.model.ArticleEntity
import data.local.model.toDomain
import domain.model.Article
import domain.repository.ArticleLocalDataSource
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ArticleLocalDataSourceImpl(
    private val realm: Realm
) : ArticleLocalDataSource {

    override suspend fun insertArticle(article: ArticleEntity) {
        realm.write { copyToRealm(article) }
    }

    override suspend fun removeArticle(article: Article) {
        realm.write {
            val articleEntity = this.query<ArticleEntity>("url == $0", article.url).first()
            delete(articleEntity)
        }
    }

    override fun readArticles(): Flow<List<Article>> {
        return realm.query<ArticleEntity>()
            .asFlow()
            .map { it.list.map(ArticleEntity::toDomain) }
    }

    override suspend fun cleanUp() {
        return realm.write {
            val articles = this.query<ArticleEntity>()
            delete(articles)
        }
    }
}