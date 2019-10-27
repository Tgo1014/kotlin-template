package tgo1014.template.repositories

interface ThemeRepository {

    fun getTheme(): Theme
    fun setTheme(t: Theme)

    enum class Theme {
        DARK, LIGHT, SYSTEM
    }
}