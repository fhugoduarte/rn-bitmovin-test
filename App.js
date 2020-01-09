import React from 'react';
import {View} from 'react-native';
import BitmovinPlayer from './module';

export default function App() {
  return (
    <View
      style={{
        flex: 1,
        backgroundColor: '#ddd',
        justifyContent: 'center',
        alignItems: 'center',
      }}>
      <BitmovinPlayer style={{width: 300, height: 150}} />
    </View>
  );
}
