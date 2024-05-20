import React from "react";
import 'bootswatch/dist/flatly/bootstrap.css'
import '../custom.css'
import 'toastr/build/toastr.css'
import "primereact/resources/themes/lara-light-blue/theme.css";
import 'primeicons/primeicons.css';
import 'primereact/resources/primereact.min.css';
import 'toastr/build/toastr.min.js'
import Rotas from "./rotas";
import NavBar from "../components/navbar"
import ProvedorAutenticacao from "./provedorAutenticacao"


class App extends React.Component {
  render(){
    return(
      
      <ProvedorAutenticacao>
        <NavBar/>
          <div className="container">
            <Rotas/>
            
          </div>
          </ProvedorAutenticacao>   
    )
  } 
}

export default App;
