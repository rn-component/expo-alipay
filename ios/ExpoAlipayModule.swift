import ExpoModulesCore

public class ExpoAlipayModule: Module {
  public func definition() -> ModuleDefinition {
    Name("ExpoAlipay")

    AsyncFunction("helloAsync") { (options: [String: String]) in
      print("Hello 👋")
    }

    ViewManager {
      View {
        ExpoAlipayView()
      }

      Prop("name") { (view: ExpoAlipayView, prop: String) in
        print(prop)
      }
    }
  }
}
