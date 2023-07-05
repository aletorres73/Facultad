package com.productosapp.entities

import com.productosapp.database.FirebaseDataProductSource
import com.productosapp.database.FirebaseDataUserSource
import com.productosapp.database.ProductSource
import com.productosapp.database.UserSource
import com.productosapp.fragments.home.HomeViewModel
import com.productosapp.fragments.login_register.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val userModule = module {
//    single<UserSource> {FirebaseDataUserSource()}
    single { FirebaseDataUserSource() } bind UserSource::class
}
val productModule = module {
//    single<ProductSource> {FirebaseDataProductSource()}
    single { FirebaseDataProductSource() } bind ProductSource::class
}
