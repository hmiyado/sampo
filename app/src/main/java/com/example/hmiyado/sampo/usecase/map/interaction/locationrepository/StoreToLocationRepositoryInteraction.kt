package com.example.hmiyado.sampo.usecase.map.interaction.locationrepository

import com.example.hmiyado.sampo.domain.store.MapStore
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
        private val store: MapStore,
        private val locationRepository: LocationRepository
) : Interaction() {
    init {
        subscriptions += saveLocationInteraction()
    }

    private fun saveLocationInteraction(): Subscription {
        return store.getOriginalLocation()
                .observeOn(Schedulers.newThread())
                .subscribe({ locationRepository.saveLocation(it) })
    }
}