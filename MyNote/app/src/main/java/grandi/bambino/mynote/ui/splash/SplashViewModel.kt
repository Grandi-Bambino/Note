package grandi.bambino.mynote.ui.splash

import grandi.bambino.mynote.data.Repository
import grandi.bambino.mynote.data.errors.NoAuthException
import grandi.bambino.mynote.ui.base.BaseViewModel

class SplashViewModel(private val notesRepository: Repository) : BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        notesRepository.getCurrentUser().observeForever {
            viewStateLiveData.value = it?.let {
                SplashViewState(isAuth  = true)
            } ?: let {
                SplashViewState(error = NoAuthException())
            }
        }
    }
}