package dk.eboks.app.presentation.ui.screens.uploads

import android.os.Bundle
import dk.eboks.app.domain.models.Translation
import dk.eboks.app.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.include_toolbar.*
import timber.log.Timber
import javax.inject.Inject

class UploadsActivity : BaseActivity(), UploadsContract.View {
    @Inject lateinit var presenter: UploadsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(dk.eboks.app.R.layout.activity_uploads)
        component.inject(this)
        presenter.onViewCreated(this, lifecycle)
        updateTranslation()
    }

    private fun updateTranslation() {
        mainTb.title = Translation.uploads.title
    }

}
