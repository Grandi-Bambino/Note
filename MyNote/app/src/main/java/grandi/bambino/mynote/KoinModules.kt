package grandi.bambino.mynote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import grandi.bambino.mynote.data.Repository
import grandi.bambino.mynote.data.provider.FireStoreProvider
import grandi.bambino.mynote.data.provider.RemoteDataProvider
import grandi.bambino.mynote.ui.main.MainViewModel
import grandi.bambino.mynote.ui.note.NoteViewModel
import grandi.bambino.mynote.ui.splash.SplashViewModel
import org.koin.android.viewmodel.ext.koin.viewModel

import org.koin.dsl.module.module


val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FireStoreProvider(get(), get()) } bind RemoteDataProvider::class
    single { Repository(get()) }
}

val splashModule = module {
    viewModel { SplashViewModel(get()) }
}

val mainModule = module {
    viewModel { MainViewModel(get()) }
}

val noteModule = module {
    viewModel { NoteViewModel(get()) }
}
