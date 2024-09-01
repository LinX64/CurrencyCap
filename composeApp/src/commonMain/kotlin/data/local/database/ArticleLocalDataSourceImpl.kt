package data.local.database

import data.local.model.ArticleEntity
import data.local.model.toDomain
import domain.model.Article
import domain.repository.ArticleLocalDataSource
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ArticleLocalDataSourceImpl(
    private val realm: Realm
) : ArticleLocalDataSource {

    override suspend fun insertArticle(article: ArticleEntity) {
        realm.writeBlocking {
            copyToRealm(article, updatePolicy = UpdatePolicy.ALL)
        }
    }

    override suspend fun updateArticle(article: ArticleEntity) {
        realm.writeBlocking {
            query<ArticleEntity>("url == $0", article.url)
                .first()
                .find()
                ?.apply {
                    isBookmarked = article.isBookmarked
                } ?: copyToRealm(article)
        }
    }

    override suspend fun removeArticle(article: Article) {
        realm.write {
            val articleEntity = this.query<ArticleEntity>("url == $0", article.url).first()
            delete(articleEntity)
        }
    }

    override suspend fun removeArticleByUrl(url: String) {
        realm.write {
            val articleEntity = this.query<ArticleEntity>("url == $0", url).first()
            delete(articleEntity)
        }
    }

    override fun getArticleByUrl(url: String): Flow<Article?> {
        return realm.query<ArticleEntity>("url == $0", url)
            .asFlow()
            .map { result ->
                result.list.firstOrNull()?.toDomain()
            }
    }

    override suspend fun insertArticles(articles: Set<ArticleEntity>) {
        realm.write {
            articles.map { copyToRealm(it, UpdatePolicy.ALL) }
        }
    }

    override fun getArticles(): Flow<List<Article>> {
        return realm.query<ArticleEntity>()
            .asFlow()
            .map { it.list.map(ArticleEntity::toDomain) }
    }

    override fun getBookmarkedArticles(): Flow<List<Article>> {
        return realm.query<ArticleEntity>("isBookmarked == $0", true)
            .asFlow()
            .map { it.list.map(ArticleEntity::toDomain) }
    }

    override suspend fun deleteArticles() {
        return realm.write {
            val articles = this.query<ArticleEntity>()
            delete(articles)
        }
    }
}