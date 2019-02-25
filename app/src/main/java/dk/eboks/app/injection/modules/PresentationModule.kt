package dk.eboks.app.injection.modules

import dagger.Binds
import dagger.Module
import dk.eboks.app.pasta.activity.PastaContract
import dk.eboks.app.pasta.activity.PastaPresenter
import dk.eboks.app.presentation.ui.channels.components.content.ekey.EkeyComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.ekey.EkeyComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.content.ekey.additem.EkeyAddItemComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.ekey.additem.EkeyAddItemComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.content.ekey.detail.EkeyDetailComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.ekey.detail.EkeyDetailComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.content.ekey.open.EkeyOpenItemComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.ekey.open.EkeyOpenItemComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.content.ekey.pin.EkeyPinComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.ekey.pin.EkeyPinComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.content.storebox.content.ChannelContentStoreboxComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.storebox.content.ChannelContentStoreboxComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.content.storebox.detail.ChannelContentStoreboxDetailComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.storebox.detail.ChannelContentStoreboxDetailComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.content.web.ChannelContentComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.web.ChannelContentComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.opening.ChannelOpeningComponentContract
import dk.eboks.app.presentation.ui.channels.components.opening.ChannelOpeningComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.overview.ChannelOverviewComponentContract
import dk.eboks.app.presentation.ui.channels.components.overview.ChannelOverviewComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.requirements.ChannelRequirementsComponentContract
import dk.eboks.app.presentation.ui.channels.components.requirements.ChannelRequirementsComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.settings.ChannelSettingsComponentContract
import dk.eboks.app.presentation.ui.channels.components.settings.ChannelSettingsComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.verification.ChannelVerificationComponentContract
import dk.eboks.app.presentation.ui.channels.components.verification.ChannelVerificationComponentPresenter
import dk.eboks.app.presentation.ui.channels.screens.content.ChannelContentContract
import dk.eboks.app.presentation.ui.channels.screens.content.ChannelContentPresenter
import dk.eboks.app.presentation.ui.channels.screens.content.ekey.EkeyContentContract
import dk.eboks.app.presentation.ui.channels.screens.content.ekey.EkeyContentPresenter
import dk.eboks.app.presentation.ui.channels.screens.content.storebox.ConnectStoreboxContract
import dk.eboks.app.presentation.ui.channels.screens.content.storebox.ConnectStoreboxPresenter
import dk.eboks.app.presentation.ui.channels.screens.overview.ChannelOverviewContract
import dk.eboks.app.presentation.ui.channels.screens.overview.ChannelOverviewPresenter
import dk.eboks.app.presentation.ui.debug.components.DebugOptionsComponentContract
import dk.eboks.app.presentation.ui.debug.components.DebugOptionsComponentPresenter
import dk.eboks.app.presentation.ui.debug.components.DebugUsersComponentContract
import dk.eboks.app.presentation.ui.debug.components.DebugUsersComponentPresenter
import dk.eboks.app.presentation.ui.debug.screens.user.DebugUserContract
import dk.eboks.app.presentation.ui.debug.screens.user.DebugUserPresenter
import dk.eboks.app.mail.presentation.ui.folder.components.FoldersComponentContract
import dk.eboks.app.mail.presentation.ui.folder.components.FoldersComponentPresenter
import dk.eboks.app.mail.presentation.ui.folder.components.NewFolderComponentContract
import dk.eboks.app.mail.presentation.ui.folder.components.NewFolderComponentPresenter
import dk.eboks.app.mail.presentation.ui.folder.components.FolderSelectUserComponentContract
import dk.eboks.app.mail.presentation.ui.folder.components.FolderSelectUserComponentPresenter
import dk.eboks.app.mail.presentation.ui.folder.screens.FolderContract
import dk.eboks.app.mail.presentation.ui.folder.screens.FolderPresenter
import dk.eboks.app.presentation.ui.home.components.channelcontrol.ChannelControlComponentContract
import dk.eboks.app.presentation.ui.home.components.channelcontrol.ChannelControlComponentPresenter
import dk.eboks.app.presentation.ui.home.components.folderpreview.FolderPreviewComponentContract
import dk.eboks.app.presentation.ui.home.components.folderpreview.FolderPreviewComponentPresenter
import dk.eboks.app.presentation.ui.home.screens.HomeContract
import dk.eboks.app.presentation.ui.home.screens.HomePresenter
import dk.eboks.app.presentation.ui.login.components.ActivationCodeComponentContract
import dk.eboks.app.presentation.ui.login.components.ActivationCodeComponentPresenter
import dk.eboks.app.presentation.ui.login.components.DeviceActivationComponentContract
import dk.eboks.app.presentation.ui.login.components.DeviceActivationComponentPresenter
import dk.eboks.app.presentation.ui.login.components.ForgotPasswordComponentContract
import dk.eboks.app.presentation.ui.login.components.ForgotPasswordComponentPresenter
import dk.eboks.app.presentation.ui.login.components.ForgotPasswordDoneComponentContract
import dk.eboks.app.presentation.ui.login.components.ForgotPasswordDoneComponentPresenter
import dk.eboks.app.presentation.ui.login.components.LoginComponentContract
import dk.eboks.app.presentation.ui.login.components.LoginComponentPresenter
import dk.eboks.app.presentation.ui.login.components.UserCarouselComponentContract
import dk.eboks.app.presentation.ui.login.components.UserCarouselComponentPresenter
import dk.eboks.app.presentation.ui.login.components.verification.VerificationComponentContract
import dk.eboks.app.presentation.ui.login.components.verification.VerificationComponentPresenter
import dk.eboks.app.presentation.ui.login.screens.PopupLoginContract
import dk.eboks.app.presentation.ui.login.screens.PopupLoginPresenter
import dk.eboks.app.mail.presentation.ui.components.foldershortcuts.FolderShortcutsComponentContract
import dk.eboks.app.mail.presentation.ui.components.foldershortcuts.FolderShortcutsComponentPresenter
import dk.eboks.app.mail.presentation.ui.components.maillist.MailListComponentContract
import dk.eboks.app.presentation.ui.mail.components.maillist.MailListComponentPresenter
import dk.eboks.app.mail.presentation.ui.components.sendercarousel.SenderCarouselComponentContract
import dk.eboks.app.mail.presentation.ui.screens.list.MailListContract
import dk.eboks.app.mail.presentation.ui.screens.overview.MailOverviewContract
import dk.eboks.app.mail.presentation.ui.components.sendercarousel.SenderCarouselComponentPresenter
import dk.eboks.app.mail.presentation.ui.screens.list.MailListPresenter
import dk.eboks.app.mail.presentation.ui.screens.overview.MailOverviewPresenter
import dk.eboks.app.mail.presentation.ui.message.components.detail.attachments.AttachmentsComponentContract
import dk.eboks.app.mail.presentation.ui.message.components.detail.attachments.AttachmentsComponentPresenter
import dk.eboks.app.mail.presentation.ui.message.components.detail.documents.DocumentComponentContract
import dk.eboks.app.mail.presentation.ui.message.components.detail.documents.DocumentComponentPresenter
import dk.eboks.app.mail.presentation.ui.message.components.detail.folderinfo.FolderInfoComponentContract
import dk.eboks.app.mail.presentation.ui.message.components.detail.folderinfo.FolderInfoComponentPresenter
import dk.eboks.app.mail.presentation.ui.message.components.detail.header.HeaderComponentContract
import dk.eboks.app.mail.presentation.ui.message.components.detail.header.HeaderComponentPresenter
import dk.eboks.app.mail.presentation.ui.message.components.detail.notes.NotesComponentContract
import dk.eboks.app.mail.presentation.ui.message.components.detail.notes.NotesComponentPresenter
import dk.eboks.app.mail.presentation.ui.message.components.detail.reply.ReplyButtonComponentContract
import dk.eboks.app.mail.presentation.ui.message.components.detail.reply.ReplyButtonComponentPresenter
import dk.eboks.app.mail.presentation.ui.message.components.detail.share.ShareComponentContract
import dk.eboks.app.mail.presentation.ui.message.components.detail.share.ShareComponentPresenter
import dk.eboks.app.mail.presentation.ui.message.components.detail.sign.SignButtonComponentContract
import dk.eboks.app.mail.presentation.ui.message.components.detail.sign.SignButtonComponentPresenter
import dk.eboks.app.mail.presentation.ui.message.components.opening.privatesender.PrivateSenderWarningComponentContract
import dk.eboks.app.mail.presentation.ui.message.components.opening.privatesender.PrivateSenderWarningComponentPresenter
import dk.eboks.app.mail.presentation.ui.message.components.opening.promulgation.PromulgationComponentContract
import dk.eboks.app.mail.presentation.ui.message.components.opening.promulgation.PromulgationComponentPresenter
import dk.eboks.app.mail.presentation.ui.message.components.opening.protectedmessage.ProtectedMessageComponentContract
import dk.eboks.app.mail.presentation.ui.message.components.opening.protectedmessage.ProtectedMessageComponentPresenter
import dk.eboks.app.mail.presentation.ui.message.components.opening.quarantine.QuarantineComponentContract
import dk.eboks.app.mail.presentation.ui.message.components.opening.quarantine.QuarantineComponentPresenter
import dk.eboks.app.mail.presentation.ui.message.components.opening.recalled.RecalledComponentContract
import dk.eboks.app.mail.presentation.ui.message.components.opening.recalled.RecalledComponentPresenter
import dk.eboks.app.mail.presentation.ui.message.components.opening.receipt.OpeningReceiptComponentContract
import dk.eboks.app.mail.presentation.ui.message.components.opening.receipt.OpeningReceiptComponentPresenter
import dk.eboks.app.mail.presentation.ui.message.components.viewers.html.HtmlViewComponentContract
import dk.eboks.app.mail.presentation.ui.message.components.viewers.html.HtmlViewComponentPresenter
import dk.eboks.app.mail.presentation.ui.message.components.viewers.image.ImageViewComponentContract
import dk.eboks.app.mail.presentation.ui.message.components.viewers.image.ImageViewComponentPresenter
import dk.eboks.app.mail.presentation.ui.message.components.viewers.pdf.PdfViewComponentContract
import dk.eboks.app.mail.presentation.ui.message.components.viewers.pdf.PdfViewComponentPresenter
import dk.eboks.app.mail.presentation.ui.message.components.viewers.text.TextViewComponentContract
import dk.eboks.app.mail.presentation.ui.message.components.viewers.text.TextViewComponentPresenter
import dk.eboks.app.mail.presentation.ui.message.MessageContract
import dk.eboks.app.presentation.ui.message.screens.MessagePresenter
import dk.eboks.app.presentation.ui.message.screens.embedded.MessageEmbeddedContract
import dk.eboks.app.presentation.ui.message.screens.embedded.MessageEmbeddedPresenter
import dk.eboks.app.presentation.ui.message.screens.opening.MessageOpeningContract
import dk.eboks.app.presentation.ui.message.screens.opening.MessageOpeningPresenter
import dk.eboks.app.mail.presentation.ui.message.reply.ReplyFormContract
import dk.eboks.app.mail.presentation.ui.message.reply.ReplyFormPresenter
import dk.eboks.app.mail.presentation.ui.message.sign.SignContract
import dk.eboks.app.mail.presentation.ui.message.sign.SignPresenter
import dk.eboks.app.presentation.ui.navigation.components.NavBarComponentContract
import dk.eboks.app.presentation.ui.navigation.components.NavBarComponentPresenter
import dk.eboks.app.presentation.ui.overlay.screens.OverlayContract
import dk.eboks.app.presentation.ui.overlay.screens.OverlayPresenter
import dk.eboks.app.presentation.ui.profile.components.HelpContract
import dk.eboks.app.presentation.ui.profile.components.HelpPresenter
import dk.eboks.app.presentation.ui.profile.components.PrivacyContract
import dk.eboks.app.presentation.ui.profile.components.PrivacyPresenter
import dk.eboks.app.presentation.ui.profile.components.drawer.EmailVerificationComponentContract
import dk.eboks.app.presentation.ui.profile.components.drawer.EmailVerificationComponentPresenter
import dk.eboks.app.presentation.ui.profile.components.drawer.FingerHintComponentContract
import dk.eboks.app.presentation.ui.profile.components.drawer.FingerHintComponentPresenter
import dk.eboks.app.presentation.ui.profile.components.drawer.FingerPrintComponentContract
import dk.eboks.app.presentation.ui.profile.components.drawer.FingerPrintComponentPresenter
import dk.eboks.app.presentation.ui.profile.components.drawer.MergeAccountComponentContract
import dk.eboks.app.presentation.ui.profile.components.drawer.MergeAccountComponentPresenter
import dk.eboks.app.presentation.ui.profile.components.drawer.PhoneVerificationComponentContract
import dk.eboks.app.presentation.ui.profile.components.drawer.PhoneVerificationComponentPresenter
import dk.eboks.app.presentation.ui.profile.components.main.ProfileInfoComponentContract
import dk.eboks.app.presentation.ui.profile.components.main.ProfileInfoComponentPresenter
import dk.eboks.app.presentation.ui.profile.components.myinfo.MyInfoComponentContract
import dk.eboks.app.presentation.ui.profile.components.myinfo.MyInfoComponentPresenter
import dk.eboks.app.presentation.ui.profile.screens.ProfileContract
import dk.eboks.app.presentation.ui.profile.screens.ProfilePresenter
import dk.eboks.app.presentation.ui.profile.screens.myinfo.MyInfoContract
import dk.eboks.app.presentation.ui.profile.screens.myinfo.MyInfoPresenter
import dk.eboks.app.presentation.ui.senders.components.SenderGroupsComponentContract
import dk.eboks.app.presentation.ui.senders.components.SenderGroupsComponentPresenter
import dk.eboks.app.presentation.ui.senders.components.categories.CategoriesComponentContract
import dk.eboks.app.presentation.ui.senders.components.categories.CategoriesComponentPresenter
import dk.eboks.app.presentation.ui.senders.components.list.SenderAllListComponentContract
import dk.eboks.app.presentation.ui.senders.components.list.SenderAllListComponentPresenter
import dk.eboks.app.presentation.ui.senders.components.register.RegisterPresenter
import dk.eboks.app.presentation.ui.senders.components.register.RegistrationContract
import dk.eboks.app.presentation.ui.senders.screens.browse.BrowseCategoryContract
import dk.eboks.app.presentation.ui.senders.screens.browse.BrowseCategoryPresenter
import dk.eboks.app.presentation.ui.senders.screens.detail.SenderDetailContract
import dk.eboks.app.presentation.ui.senders.screens.detail.SenderDetailPresenter
import dk.eboks.app.presentation.ui.senders.screens.list.SenderAllListContract
import dk.eboks.app.presentation.ui.senders.screens.list.SenderAllListPresenter
import dk.eboks.app.presentation.ui.senders.screens.overview.SendersOverviewContract
import dk.eboks.app.presentation.ui.senders.screens.overview.SendersOverviewPresenter
import dk.eboks.app.presentation.ui.senders.screens.registrations.PendingContract
import dk.eboks.app.presentation.ui.senders.screens.registrations.PendingPresenter
import dk.eboks.app.presentation.ui.senders.screens.registrations.RegistrationsContract
import dk.eboks.app.presentation.ui.senders.screens.registrations.RegistrationsPresenter
import dk.eboks.app.presentation.ui.senders.screens.segment.SegmentDetailContract
import dk.eboks.app.presentation.ui.senders.screens.segment.SegmentDetailPresenter
import dk.eboks.app.presentation.ui.start.components.signup.SignupComponentContract
import dk.eboks.app.presentation.ui.start.components.signup.SignupComponentPresenter
import dk.eboks.app.presentation.ui.start.screens.StartContract
import dk.eboks.app.presentation.ui.start.screens.StartPresenter
import dk.eboks.app.presentation.ui.uploads.components.UploadOverviewComponentContract
import dk.eboks.app.presentation.ui.uploads.components.UploadOverviewComponentPresenter
import dk.eboks.app.presentation.ui.uploads.screens.UploadsContract
import dk.eboks.app.presentation.ui.uploads.screens.UploadsPresenter
import dk.eboks.app.presentation.ui.uploads.screens.fileupload.FileUploadContract
import dk.eboks.app.presentation.ui.uploads.screens.fileupload.FileUploadPresenter
import dk.nodes.arch.domain.injection.scopes.ActivityScope

@Module
abstract class PresentationModule {
    @ActivityScope
    @Binds
    abstract fun providePastaPresenter(presenter: PastaPresenter): PastaContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideMailOverviewPresenter(presenter: MailOverviewPresenter): MailOverviewContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideMailListPresenter(presenter: MailListPresenter): MailListContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideFolderPresenter(presenter: FolderPresenter): FolderContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideMessagePresenter(presenter: MessagePresenter): MessageContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideChannelsPresenter(presenter: ChannelOverviewPresenter): ChannelOverviewContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideFileUploadPresenter(presenter: FileUploadPresenter): FileUploadContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideMessageSheetPresenter(presenter: MessageEmbeddedPresenter): MessageEmbeddedContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideMessageOpeningPresenter(presenter: MessageOpeningPresenter): MessageOpeningContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideHeaderComponentPresenter(presenter: HeaderComponentPresenter): HeaderComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideNotesComponentPresenter(presenter: NotesComponentPresenter): NotesComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideReplyButtonComponentPresenter(presenter: ReplyButtonComponentPresenter): ReplyButtonComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideSignButtonComponentPresenter(presenter: SignButtonComponentPresenter): SignButtonComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideSignPresenter(presenter: SignPresenter): SignContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideAttachmentsComponentPresenter(presenter: AttachmentsComponentPresenter): AttachmentsComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideFolderInfoComponentPresenter(presenter: FolderInfoComponentPresenter): FolderInfoComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideDocumentComponentPresenter(presenter: DocumentComponentPresenter): DocumentComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun providePdfPreviewComponentPresenter(presenter: PdfViewComponentPresenter): PdfViewComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideFoldersComponentPresenter(presenter: FoldersComponentPresenter): FoldersComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideFolderShortcutsComponentPresenter(presenter: FolderShortcutsComponentPresenter): FolderShortcutsComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideSenderCarouselComponentPresenter(presenter: SenderCarouselComponentPresenter): SenderCarouselComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideMailListComponentPresenter(presenter: MailListComponentPresenter): MailListComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideNavBarComponentPresenter(presenter: NavBarComponentPresenter): NavBarComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideHtmlViewComponentPresenter(presenter: HtmlViewComponentPresenter): HtmlViewComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideImageViewComponentPresenter(presenter: ImageViewComponentPresenter): ImageViewComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideTextViewComponentPresenter(presenter: TextViewComponentPresenter): TextViewComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideQuarantineComponentPresenter(presenter: QuarantineComponentPresenter): QuarantineComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideRecalledMessageComponentPresenter(presenter: RecalledComponentPresenter): RecalledComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun providePromulgationComponentPresenter(presenter: PromulgationComponentPresenter): PromulgationComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideProtectedMessageComponentPresenter(presenter: ProtectedMessageComponentPresenter): ProtectedMessageComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideOpeningReceiptComponentPresenter(presenter: OpeningReceiptComponentPresenter): OpeningReceiptComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun providePrivateSenderWarningComponentPresenter(presenter: PrivateSenderWarningComponentPresenter): PrivateSenderWarningComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideChannelListComponentPresenter(presenter: ChannelOverviewComponentPresenter): ChannelOverviewComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideChannelSettingsPopUpComponentPresenter(presenter: ChannelRequirementsComponentPresenter): ChannelRequirementsComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideShareComponentPresenter(presenter: ShareComponentPresenter): ShareComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideSendersOverviewPresenter(presenter: SendersOverviewPresenter): SendersOverviewContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideStartPresenter(presenter: StartPresenter): StartContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideSignupComponentPresenter(presenter: SignupComponentPresenter): SignupComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideVerificationComponentPresenter(presenter: VerificationComponentPresenter): VerificationComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideUserCarouselComponentPresenter(presenter: UserCarouselComponentPresenter): UserCarouselComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideLoginComponentPresenter(presenter: LoginComponentPresenter): LoginComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideForgotPasswordComponentPresenter(presenter: ForgotPasswordComponentPresenter): ForgotPasswordComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideActivationCodeComponentPresenter(presenter: ActivationCodeComponentPresenter): ActivationCodeComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideChannelOpeningComponentPresenter(presenter: ChannelOpeningComponentPresenter): ChannelOpeningComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideChannelVerificationComponentPresenter(presenter: ChannelVerificationComponentPresenter): ChannelVerificationComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideChannelContentComponentPresenter(presenter: ChannelContentComponentPresenter): ChannelContentComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideChannelContentStoreboxComponentPresenter(presenter: ChannelContentStoreboxComponentPresenter): ChannelContentStoreboxComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideChannelContentStoreboxDetailComponentPresenter(presenter: ChannelContentStoreboxDetailComponentPresenter): ChannelContentStoreboxDetailComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideChannelSettingsComponentPresenter(presenter: ChannelSettingsComponentPresenter): ChannelSettingsComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideChannelContentPresenter(presenter: ChannelContentPresenter): ChannelContentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideCategoriesComponentPresenter(presenter: CategoriesComponentPresenter): CategoriesComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideBrowseCategoryPresenter(presenter: BrowseCategoryPresenter): BrowseCategoryContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideRegistrationsPresenter(presenter: RegistrationsPresenter): RegistrationsContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideSenderGroupsComponentPresenter(presenter: SenderGroupsComponentPresenter): SenderGroupsComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideUploadOverviewPresenter(presenter: UploadOverviewComponentPresenter): UploadOverviewComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideRegisterPresenter(presenter: RegisterPresenter): RegistrationContract.Presenter

    @ActivityScope
    @Binds
    abstract fun providePendingPresenter(presenter: PendingPresenter): PendingContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideSenderDetailPresenter(presenter: SenderDetailPresenter): SenderDetailContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideSegmentDetailPresenter(presenter: SegmentDetailPresenter): SegmentDetailContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideDebugOptionsComponentPresenter(presenter: DebugOptionsComponentPresenter): DebugOptionsComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideDebugUsersComponentPresenter(presenter: DebugUsersComponentPresenter): DebugUsersComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideProfilePresenter(presenter: ProfilePresenter): ProfileContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideProfileInfoComponentPresenter(presenter: ProfileInfoComponentPresenter): ProfileInfoComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideMyInfoComponentPresenter(presenter: MyInfoComponentPresenter): MyInfoComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideDebugUserPresenter(presenter: DebugUserPresenter): DebugUserContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideEmailVerificationComponentPresenter(presenter: EmailVerificationComponentPresenter): EmailVerificationComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun providePhoneVerificationComponentPresenter(presenter: PhoneVerificationComponentPresenter): PhoneVerificationComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideFingerHintComponentPresenter(presenter: FingerHintComponentPresenter): FingerHintComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideFingerPrintComponentPresenter(presenter: FingerPrintComponentPresenter): FingerPrintComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideMergeAccountComponentPresenter(presenter: MergeAccountComponentPresenter): MergeAccountComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideUploadsPresenter(presenter: UploadsPresenter): UploadsContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideForgotPasswordDoneComponentPresenter(presenter: ForgotPasswordDoneComponentPresenter): ForgotPasswordDoneComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideOverlayPresenter(presenter: OverlayPresenter): OverlayContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideConnectStoreboxPresenter(presenter: ConnectStoreboxPresenter): ConnectStoreboxContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideMyInfoPresenter(presenter: MyInfoPresenter): MyInfoContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideReplyFormPresenter(presenter: ReplyFormPresenter): ReplyFormContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideSenderAllListPresenter(presenter: SenderAllListPresenter): SenderAllListContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideSenderAllListComponentPresenter(presenter: SenderAllListComponentPresenter): SenderAllListComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideNewFolderComponentPresenter(presenter: NewFolderComponentPresenter): NewFolderComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideFolderSelectUserComponentPresenter(presenter: FolderSelectUserComponentPresenter): FolderSelectUserComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideEkeyContentPresenter(presenter: EkeyContentPresenter): EkeyContentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideEkeyComponentPresenter(presenter: EkeyComponentPresenter): EkeyComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideEkeyAddItemComponentPresenter(presenter: EkeyAddItemComponentPresenter): EkeyAddItemComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideEkeyDetailComponentPresenter(presenter: EkeyDetailComponentPresenter): EkeyDetailComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideFolderPreviewComponentPresenter(presenter: FolderPreviewComponentPresenter): FolderPreviewComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideChannelControlComponentPresenter(presenter: ChannelControlComponentPresenter): ChannelControlComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideEkeyOpenItemComponentPresenter(presenter: EkeyOpenItemComponentPresenter): EkeyOpenItemComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideEkeyPinComponentPresenter(presenter: EkeyPinComponentPresenter): EkeyPinComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun providePrivacyPresenter(presenter: PrivacyPresenter): PrivacyContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideHelpPresenter(presenter: HelpPresenter): HelpContract.Presenter

    @ActivityScope
    @Binds
    abstract fun providePopupLoginPresenter(presenter: PopupLoginPresenter): PopupLoginContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideDeviceActivationComponentPresenter(presenter: DeviceActivationComponentPresenter): DeviceActivationComponentContract.Presenter

    @ActivityScope
    @Binds
    abstract fun provideHomePresenter(presenter: HomePresenter): HomeContract.Presenter
    /* Pasta
    @ActivityScope
    @Binds
    abstract fun provideComponentPresenter(stateManager: AppStateManager) : ComponentContract.Presenter {
        return ComponentPresenter(stateManager)
    }
    */
}