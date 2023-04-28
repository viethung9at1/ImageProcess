import logo from './logo.svg';
import './App.css';
import { Component } from 'react';
import { UploadImage } from './Component/UploadImage';

class App extends Component{
  render(){
    return (
      <div>
        <UploadImage />
      </div>
    )
  }
}

export default App;
