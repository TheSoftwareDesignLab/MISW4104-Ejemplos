package com.habitrpg.android.habitica.data.implementation

import android.content.Context
import com.habitrpg.android.habitica.data.ApiClient
import com.habitrpg.android.habitica.data.ContentRepository
import com.habitrpg.android.habitica.data.local.ContentLocalRepository
import com.habitrpg.android.habitica.helpers.AprilFoolsHandler
import com.habitrpg.android.habitica.models.ContentResult
import com.habitrpg.android.habitica.models.WorldState
import com.habitrpg.android.habitica.models.inventory.SpecialItem
import io.reactivex.rxjava3.core.Flowable
import io.realm.RealmList
import java.util.*

abstract class ContentRepositoryImpl<T : ContentLocalRepository>(localRepository: T, apiClient: ApiClient) : BaseRepositoryImpl<T>(localRepository, apiClient), ContentRepository {

    private var lastContentSync = 0L
    private var lastWorldStateSync = 0L

    override fun retrieveContent(context: Context?): Flowable<ContentResult> {
        return retrieveContent(context, false)
    }

    override fun retrieveContent(context: Context?, forced: Boolean): Flowable<ContentResult> {
        val now = Date().time
        return if (forced || now - this.lastContentSync > 300000) {
            lastContentSync = now
            apiClient.content.doOnNext {
                context?.let { context ->
                    it.special = RealmList()
                    it.special.add(SpecialItem.makeMysteryItem(context))
                }
                localRepository.saveContent(it)
            }
        } else {
            Flowable.just(ContentResult())
        }
    }

    override fun retrieveWorldState(context: Context?): Flowable<WorldState> {
        val now = Date().time
        return if (now - this.lastWorldStateSync > 3600000) {
            lastWorldStateSync = now
            apiClient.worldState.doOnNext {
                localRepository.saveWorldState(it)
                for (event in it.events) {
                    if (event.aprilFools != null && event.isCurrentlyActive) {
                        AprilFoolsHandler.handle(event.aprilFools, event.end)
                    }
                }
            }
        } else {
            Flowable.just(WorldState())
        }
    }

    override fun getWorldState(): Flowable<WorldState> {
        return localRepository.getWorldState()
    }
}
