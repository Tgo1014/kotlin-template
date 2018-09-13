package dk.eboks.app.presentation.ui.message.screens.embedded

import dk.eboks.app.BuildConfig
import dk.eboks.app.domain.interactors.message.DeleteMessagesInteractor
import dk.eboks.app.domain.interactors.message.GetMessagesInteractor
import dk.eboks.app.domain.interactors.message.MoveMessagesInteractor
import dk.eboks.app.domain.interactors.message.UpdateMessageInteractor
import dk.eboks.app.domain.managers.AppStateManager
import dk.eboks.app.domain.models.folder.Folder
import dk.eboks.app.domain.models.local.ViewError
import dk.eboks.app.domain.models.message.Message
import dk.eboks.app.domain.models.message.MessagePatch
import dk.nodes.arch.presentation.base.BasePresenterImpl
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by bison on 20-05-2017.
 */
class MessageEmbeddedPresenter @Inject constructor(val stateManager: AppStateManager,
                                                   val deleteMessagesInteractor: DeleteMessagesInteractor,
                                                   val updateMessageInteractor: UpdateMessageInteractor
) :
        MessageEmbeddedContract.Presenter,
        BasePresenterImpl<MessageEmbeddedContract.View>(),
        DeleteMessagesInteractor.Output,
        UpdateMessageInteractor.Output {

    init {
        deleteMessagesInteractor.output = this
        updateMessageInteractor.output = this
    }

    var message: Message? = null
    var moveToFolder: String? = null

    override fun setup() {
        message = stateManager.state?.currentMessage
        setupDrawerHeader()
        startViewer()
        runAction { v ->
            v.addHeaderComponentFragment()
            message?.let { message ->
                if (BuildConfig.ENABLE_REPLY) {
                    message.reply?.let {
                        v.addReplyButtonComponentFragment(message)
                    }
                }
                if (BuildConfig.ENABLE_SIGN) {
                    message.sign?.let {
                        v.addSignButtonComponentFragment(message)
                    }
                }
                if (BuildConfig.ENABLE_DOCUMENT_ACTIONS) {
                    v.setActionButton(message.unread)
                }

                v.addNotesComponentFragment()
                if (message.attachments != null)
                    v.addAttachmentsComponentFragment()
                v.addShareComponentFragment()
                v.addFolderInfoComponentFragment()
                v.showTitle(message)
            }
        }
    }

    private fun setupDrawerHeader() {
        if (message?.numberOfAttachments ?: 0 > 0)
            runAction { v -> v.setHighPeakHeight() }
    }

    fun startViewer() {
        message?.content?.mimeType?.let { mimetype ->
            if (mimetype.startsWith("image/", true)) {
                runAction { v -> v.addImageViewer() }
                return
            }
            if (mimetype == "application/pdf") {
                runAction { v -> v.addPdfViewer() }
                return
            }
            if (mimetype == "text/html") {
                runAction { v -> v.addHtmlViewer() }
                return
            }
            if (mimetype.startsWith("text/", true)) {
                runAction { v -> v.addTextViewer() }
                return
            }
        }
    }

    fun updateMessage(messagePatch: MessagePatch) {
        message?.let {
            updateMessageInteractor.input = UpdateMessageInteractor.Input(it, messagePatch)
            updateMessageInteractor.run()
        }
    }

    override fun moveMessage(folder: Folder) {
        moveToFolder = folder.name
        updateMessage(MessagePatch(false, null, folder.id, null))
    }

    override fun deleteMessage() {
        message?.let {
            deleteMessagesInteractor.input = DeleteMessagesInteractor.Input(it)
            deleteMessagesInteractor.run()
        }
    }

    override fun archiveMessage() {
        updateMessage(MessagePatch(null, true, null, null))
    }

    override fun markMessageRead() {
        updateMessage(MessagePatch(false, null, null, null))
    }

    override fun markMessageUnread() {
        updateMessage(MessagePatch(true, null, null, null))
    }

    override fun onDeleteMessagesSuccess() {
        runAction { v->
            v.messageDeleted()
        }
    }

    override fun onDeleteMessagesError(error: ViewError) {
        runAction { view ->
            view.showErrorDialog(error)
        }
    }

    override fun onUpdateMessageSuccess() {
        moveToFolder?.let {
            runAction { v->
                v.updateFolderName(it)
            }
        }
    }

    override fun onUpdateMessageError(error: ViewError) {
        moveToFolder = null
        runAction { view ->
            view.showErrorDialog(error)
        }
    }
}