package com.productosapp.entities

import com.productosapp.database.FirebaseDataProductSource
import com.productosapp.database.FirebaseDataUserSource
import com.productosapp.database.ProductSource
import com.productosapp.database.UserSource
import org.koin.dsl.module

val AppModule = module {

    single<UserSource> { FirebaseDataUserSource() }
    single<ProductSource> { FirebaseDataProductSource() }
}