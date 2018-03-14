package dk.eboks.app.domain.models

import dk.eboks.app.domain.models.folder.Folder
import dk.eboks.app.domain.models.message.Message
import dk.eboks.app.domain.models.channel.ChannelState
import dk.eboks.app.domain.models.login.LoginState
import dk.eboks.app.domain.models.message.MessageOpeningState

/**
 * Created by bison on 09-02-2018.
 */
data class AppState (
        var currentFolder : Folder? = null,
        var currentMessage : Message? = null,
        var currentViewerFileName : String? = null,
        @Transient val openingState: MessageOpeningState = MessageOpeningState(),
        @Transient val channelState: ChannelState = ChannelState(),
        @Transient val loginState: LoginState = LoginState()
)