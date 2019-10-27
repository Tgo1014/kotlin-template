package tgo1014.template.domain.repositories

import tgo1014.template.domain.managers.PrefManager
import tgo1014.template.repositories.ThemeRepository

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