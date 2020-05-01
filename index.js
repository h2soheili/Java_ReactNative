import React from 'react';
import {AppRegistry, StyleSheet, Text, View ,TouchableOpacity,TextInput} from 'react-native';
import { DeviceEventEmitter , NativeModules } from 'react-native';

class HelloWorld extends React.Component {
  constructor(props) {
    super(props);
    this.state={
      text:' '
    }
  }
  componentDidMount(): void {
    DeviceEventEmitter.addListener('customEventName', function(e: Event) {
      // handle event and you will get a value in event object, you can log it here
      console.log('log:::',e)
      alert(JSON.stringify(e))
    });
    DeviceEventEmitter.addListener('qrCode', function(e: Event) {
      console.warn('log:: qrCode : ', e);
    });
    console.log('log:: this.props ',this.props);
    console.log('log:: NativeModules',NativeModules)
  }
  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.hello}>this is react activity</Text>
        <View style={{width:100,height:40,backgroundColor:'grey'}}>
          <TextInput style={{width:'100%',height:'100%'}} value={this.state.text} onChangeText={e=>this.setState({text:e})} />
        </View>
        <TouchableOpacity
        onPress={()=>{

          NativeModules.ActivityStarter.navigateToExample(this.state.text)

          // const nativeComm = NativeModules.ReactNativeBridge;
          // if(nativeComm){
          //   nativeComm.saveUser({name:'ali'});
          // }

        }}
        style={{backgroundColor:'#a5def1'}}
        >
          <Text>
            press To Back Native Activity
          </Text>
        </TouchableOpacity>
      </View>
    );
  }
}
var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems:'center'
  },
  hello: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
});

AppRegistry.registerComponent('MyReactNativeApp', () => HelloWorld);
