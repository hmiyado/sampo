package com.example.hmiyado.sampo

import android.app.Application
import android.content.Context
import android.location.LocationManager
import com.example.hmiyado.sampo.domain.usecase.UseLocation
import com.example.hmiyado.sampo.repository.LocationRepositoryRealmImpl
import com.example.hmiyado.sampo.repository.LocationServiceImpl
import com.example.hmiyado.sampo.repository.LocationServiceVirtualImpl
import com.github.salomonbrys.kodein.*

/**
 * Created by hmiyado on 2016/08/05.
 */
