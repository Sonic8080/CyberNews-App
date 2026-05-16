package com.cachenews.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cachenews.data.repository.NewsRepository
import com.cachenews.notification.NotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val newsRepository: NewsRepository,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            // Refresh all articles from all sources
            newsRepository.refreshAllArticles()

            // Get unnotified articles and send notifications
            val newArticles = newsRepository.getUnnotifiedArticles()

            if (newArticles.isNotEmpty()) {
                // Send notifications for the latest articles (max 5 to avoid spam)
                newArticles.take(5).forEach { article ->
                    notificationHelper.sendNewArticleNotification(article)
                }

                // Mark all as notified
                newsRepository.markAsNotified(newArticles.map { it.id })
            }

            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 3) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }
}
