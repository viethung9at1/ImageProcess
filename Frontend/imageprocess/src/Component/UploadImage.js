import React, { Component } from 'react';
import { PreviewImage } from './PreviewImage';
export class UploadImage extends Component{
  constructor(props){
    super(props)
    this.state={
      selectedFile: null,
      base64Img: null,
      base64Img2: null
    }
    this.handleFileChange=this.handleFileChange.bind(this)
    this.fileUpload=this.fileUpload.bind(this)
    this.getBase64=this.getBase64.bind(this)
  }
  handleFileChange(event){
    event.preventDefault(); 
    this.setState({selectedFile: event.target.files[0]})
    this.getBase64(event.target.files[0],1)
  }
  getBase64(file, choice){
    let reader=new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => {
      let base64URL=reader.result
      if(choice==1) this.setState({base64Img: base64URL})
      else if(choice==2) this.setState({base64Img2: base64URL})
    }
  }
  async fileUpload(){
    if(!this.state.selectedFile) return;
    const formData=new FormData()
    formData.append('file', this.state.selectedFile) 
    const reponse=await fetch("http://localhost:8080/upload", {
      method:"POST",
      body: formData
    }).then(response => response.text())
    console.log(reponse)
    this.fileDownload(reponse)
  }
  async fileDownload(filePath){
    const formData=new FormData()
    formData.append('filePath', filePath)
    const reponse=await fetch("http://localhost:8080/download", {
      method: "POST",
      body:formData
    }).then(reponse=> reponse.blob())
    this.getBase64(reponse, 2)
  }
  render(){
    return (
      <div>
        <h1>Image processing learning</h1>
        <input type='file'onChange={this.handleFileChange}/>
        <button onClick={this.fileUpload}>Upload</button>
        <PreviewImage image1={this.state.base64Img} image2={this.state.base64Img2}/>
      </div>
    )
  }
}

