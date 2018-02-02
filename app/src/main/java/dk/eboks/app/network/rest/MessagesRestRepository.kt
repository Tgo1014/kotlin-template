package dk.eboks.app.network.rest

import dk.eboks.app.domain.models.Folder
import dk.eboks.app.domain.models.Message
import dk.eboks.app.domain.repositories.FoldersRepository
import dk.eboks.app.domain.repositories.MessagesRepository
import dk.eboks.app.domain.repositories.RepositoryException
import dk.eboks.app.injection.modules.FolderStore
import dk.eboks.app.injection.modules.MessageStore
import dk.eboks.app.injection.modules.MessageStoreKey
import java.io.IOException
import java.net.UnknownHostException

/**
 * Created by bison on 01/02/18.
 */
class MessagesRestRepository(val messageStore: MessageStore) : MessagesRepository {

    override fun getMessages(cached: Boolean, folderId : Long): List<Message> {
        try {
            val result = if(cached) messageStore.get(MessageStoreKey(folderId)).blockingGet() else messageStore.fetch(MessageStoreKey(folderId)).blockingGet()
            if(result == null) {
                throw(RepositoryException(-1, "darn"))
            }
            return result
        }
        catch (e : Throwable)
        {
            e.printStackTrace()
            if(e.cause != null) {
                when(e.cause) {
                    is UnknownHostException -> throw(RepositoryException(-1, "UnknownHostException"))
                    is IOException -> throw(RepositoryException(-1, "IOException"))
                    else -> throw(RepositoryException(-1, "UnknownException"))
                }
            }
            else
                throw(RepositoryException(-1, "Unknown"))
        }
    }
}