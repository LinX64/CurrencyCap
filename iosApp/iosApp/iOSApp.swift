import SwiftUI
import FirebaseCore
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        FirebaseApp.configure()
        KoinInitializer().initializeKoin()
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
