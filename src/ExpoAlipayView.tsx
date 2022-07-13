import { requireNativeViewManager } from 'expo-modules-core';
import * as React from 'react';

export type ExpoAlipayViewProps = {
  name: number;
};

type ExpoAlipayViewState = {}

const NativeView: React.ComponentType<ExpoAlipayViewProps> =
  requireNativeViewManager('ExpoAlipay');

export default class ExpoAlipayView extends React.Component<ExpoAlipayViewProps, ExpoAlipayViewState> {
  render() {
    return <NativeView name={this.props.name} />;
  }
}
