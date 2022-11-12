package com.panasetskaia.feature_details.di

import androidx.lifecycle.ViewModel
import com.panasetskaia.core.di.ViewModelKey
import com.panasetskaia.feature_details.presenation.viewmodels.DetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface DetailsModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    fun bindDetailsViewModel(impl: DetailsViewModel): ViewModel

}