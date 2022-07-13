import { NativeModulesProxy } from 'expo-modules-core';

import ExpoAlipayView, { ExpoAlipayViewProps } from './ExpoAlipayView'

const { ExpoAlipay } = NativeModulesProxy;

export async function pay(params: String) {
  return await ExpoAlipay.pay(params)
}

export async function authInfo(params: String) {
  return await ExpoAlipay.authInfo(params)
}

export async function verify(certifyId: String, certifyUrl: String) {
  return await ExpoAlipay.verify(certifyId, certifyUrl)
}

export {
  ExpoAlipayView,
  ExpoAlipayViewProps
};
