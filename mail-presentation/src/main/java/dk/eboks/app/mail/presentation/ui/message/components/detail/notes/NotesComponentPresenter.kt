package dk.eboks.app.mail.presentation.ui.message.components.detail.notes

import dk.eboks.app.domain.managers.AppStateManager
import dk.eboks.app.domain.models.local.ViewError
import dk.eboks.app.domain.models.message.Message
import dk.eboks.app.domain.models.message.MessagePatch
import dk.eboks.app.mail.domain.interactors.messageoperations.UpdateMessageInteractor
import dk.nodes.arch.presentation.base.BasePresenterImpl
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by bison on 20-05-2017.
 */
internal class NotesComponentPresenter @Inject constructor(
    appState: AppStateManager,
    private val updateMessageInteractor: UpdateMessageInteractor
) :
    NotesComponentContract.Presenter,
    BasePresenterImpl<NotesComponentContract.View>(),
    UpdateMessageInteractor.Output {

    var currentMessage: Message? = appState.state?.currentMessage

    init {
        updateMessageInteractor.output = this
    }

    override fun setup() {
        view { currentMessage?.let(::updateView) }
    }

    override fun saveNote(note: String) {
        currentMessage?.let { msg ->
            val messages = arrayListOf(msg)
            updateMessageInteractor.input =
                UpdateMessageInteractor.Input(messages, MessagePatch(note = note))
            updateMessageInteractor.run()
        }
    }

    override fun onUpdateMessageSuccess() {
        Timber.e("update success")
    }

    override fun onUpdateMessageError(error: ViewError) {
        view { showErrorDialog(error) }
    }
}