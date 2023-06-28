package com.productosapp.entities

import com.productosapp.database.FirebaseDataProductSource
import com.productosapp.database.FirebaseDataUserSource
import com.productosapp.database.ProductSource
import com.productosapp.database.UserSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val userModule = module {
    single<UserSource> {FirebaseDataUserSource()}
}
val productModule = module {
    single<ProductSource> {FirebaseDataProductSource()}
}
