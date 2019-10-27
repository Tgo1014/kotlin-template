package dk.nodes.template.domain.repositories

import dk.nodes.template.domain.managers.PrefManager
import dk.nodes.template.repositories.ThemeRepository

class ThemeRepositoryImpl(private val prefManager: PrefManager) : ThemeRepository {

    companion object {
        private const val PREF_THEME = "PREF_THEME"
    }

    override fun getTheme(): ThemeRepository.Theme {
        val theme = prefManager.getString(PREF_THEME, ThemeRepository.Theme.LIGHT.name)!!
        return ThemeRepository.Theme.valueOf(theme)
    }

    override fun setTheme(t: ThemeRepository.Theme) {
        prefManager.setString(PREF_THEME, t.name)
    }

}