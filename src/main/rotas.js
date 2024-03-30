import React from "react";
import Login from "../views/login";
import CadastroUsuario from "../views/cadastroUsuario";
import Home from '../views/home'
import RegistroPresencas from "../views/presencas/registro-presencas";
import {Route, Switch, HashRouter, Redirect  } from 'react-router-dom'
import { AuthConsumer } from "./provedorAutenticacao";


function RotaAutenticada( {component: Component, isUsuarioAutenticado, ...props} ){
    return (
        <Route {...props} render={(componentProps) => {
            if(isUsuarioAutenticado){
                return(
                    <Component {...componentProps} />
                )
            }else{
                return(
                    <Redirect to={{pathname: '/home', state: {from: componentProps.location}}} />
                )
            }
        }}/>
    )
}

function Rotas(props){
    return(
        <HashRouter>
            <Switch>
                <Route path="/login" component={Login}/>
                <Route path="/cadastro-usuarios" component={CadastroUsuario}/>

                <Route  path="/home" component={Home}/>
                <RotaAutenticada isUsuarioAutenticado={props.isUsuarioAutenticado} path="/registro-presencas" component={RegistroPresencas}/>
            </Switch>
        </HashRouter>
    )
}

export default () => (
    <AuthConsumer>
        { (context) => (<Rotas isUsuarioAutenticado={context.isAutenticado} />) }
    </AuthConsumer>
)