import React from "react";
import NavBarItem from "./navbarItem";
import { AuthConsumer } from "../main/provedorAutenticacao";
import logo from "../imgs/logo.png"

function NavBar(props){
    return(
        <div className="navbar navbar-expand-lg fixed-top navbar-dark bg-primary">
          <div className="container">
            <a href="#/home" className="navbar-brand"><img id="logo" src={logo} alt="logo"/></a>
          <div className="collapse navbar-collapse" id="navbarResponsive">            
            <ul className="navbar-nav mr-auto" >
              <NavBarItem render={props.isUsuarioAutenticado} href="#/home" label="Home"/>
              <NavBarItem render={props.isUsuarioAutenticado} href="#/registro-presencas" label="Registrar Presenças"/>
              <NavBarItem render={props.isUsuarioAutenticado} href="#/alunos" label="Alunos"/>
              <NavBarItem render={props.isUsuarioAutenticado} onClick={props.deslogar} href="#/login" label="Sair"/>
            </ul>
          </div>
        </div>
      </div>
    )
}

export default () => (
  <AuthConsumer>
      { (context) => (<NavBar isUsuarioAutenticado={context.isAutenticado} deslogar={context.encerrarSessao} />) }
  </AuthConsumer>
)

