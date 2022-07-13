package expo.modules.expoalipay

import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import com.alipay.sdk.app.AuthTask
import com.alipay.sdk.app.PayTask
import com.alipay.sdk.app.EnvUtils
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.alibaba.fastjson.JSONObject
import com.alipay.mobile.android.verify.sdk.ServiceFactory
import com.alipay.mobile.android.verify.sdk.interfaces.ICallback


class ExpoAlipayModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("ExpoAlipay")

    AsyncFunction('pay') { params: String ->
      val payRunnable = Runnable {
        val alipay = PayTask(getCurrentActivity())
        val result = alipay.payV2(params.trim(), true)
        return@AsyncFunction result
      }

      // 必须异步调用
      val payThread = Thread(payRunnable)
      payThread.start()
    }

    AsyncFunction('authInfo') { params: String ->
      val payRunnable = Runnable {
        val alipay = AuthTask(getCurrentActivity())
        val result = alipay.authV2(params.trim(), true)
        return@AsyncFunction result
      }

      // 必须异步调用
      val payThread = Thread(payRunnable)
      payThread.start()
    }

    AsyncFunction('verify') { certifyId: String, certifyUrl: String ->
      val requestInfo = JSONObject()
      val bizCode = ServiceFactory.build().getBizCode(getCurrentActivity())
      requestInfo.put("url", certifyUrl)
      requestInfo.put("certifyId", certifyId)
      requestInfo.put("bizCode", bizCode)

      // 发起认证
      ServiceFactory.build().startService(getCurrentActivity(), requestInfo, {
        @Override
        fun onResponse(response: Map<String?, String?>) {
          val responseCode = response["resultStatus"]
          if ("9001".equals(responseCode)) {
            // 9001需要等待回调/回前台查询认证结果
            waitForResult = true
          }
          return@AsyncFunction responseCode
        }
      })
    }

    ViewManager {
      View { context ->
        ExpoAlipayView(context)
      }

      Prop("name") { view: ExpoAlipayView, prop: String ->
        println(prop)
      }
    }
  }
}
