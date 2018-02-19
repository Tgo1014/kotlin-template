package dk.eboks.app.presentation.ui.screens.message

import android.os.Bundle
import android.util.Log
import android.view.View
import dk.eboks.app.R
import dk.eboks.app.domain.managers.EboksFormatter
import dk.eboks.app.domain.models.Message
import dk.eboks.app.domain.models.Translation
import dk.eboks.app.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.include_toolnar.*
import javax.inject.Inject

class MessageActivity : BaseActivity(), MessageContract.View {
    @Inject lateinit var presenter: MessageContract.Presenter
    @Inject
    lateinit var formatter: EboksFormatter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        presenter.onViewCreated(this, lifecycle)
        setContentView(R.layout.activity_message)

    }

    override fun setupTranslations() {
        toolbarTv.text = Translation.message.title
    }

    override fun showError(msg: String) {
        Log.e("debug", msg)
    }

    override fun showTitle(message: Message) {
        toolbarSubTv.text = formatter.formatDate(message)
        toolbarTv.visibility = View.VISIBLE
        toolbarSubTv.visibility = View.VISIBLE
    }
}
