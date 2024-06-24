package util

object ApplicationComponent {
    private var _coreComponent: CoreComponent? = null

    fun init() {
        _coreComponent = CoreComponentImpl()
    }
}
