package com.example.hmiyado.sampo.usecase.interaction.locationrepository

import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.repository.location.LocationRepository
import com.example.hmiyado.sampo.usecase.Interaction
import rx.Subscription
import rx.schedulers.Schedulers

/**
 * Created by hmiyado on 2016/12/27.
 *
 * 変化があった状態から，地点を保存する相互作用
 */
class StoreToLocationRepositoryInteraction(
        private val store: Store,
        private val locationRepository: LocationRepository
) : Interaction() {
    init {
        subscriptions += saveLocationInteraction()
    }

    private fun saveLocationInteraction(): Subscription {
        return store.getMapSignal()
                .observeOn(Schedulers.newThread())
                .subscribe({ locationRepository.saveLocation(it.originalLocation) })
    }
}