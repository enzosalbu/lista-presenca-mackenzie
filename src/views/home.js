import React from "react";
import AuthService from "../app/service/authService";

class Home extends React.Component{
    state = {
        professor: ''
    }

    

    componentDidMount(){
       const usuarioLogado = AuthService.obterUsuarioAutenticadoJSON();
       if(usuarioLogado){
        this.setState({ professor: `, ${usuarioLogado.nome}`}) 
       }              
    }
    render(){
        
        return(                
            <div id="homeBody" className="jumbotron">
                <h1 className="display-3">Bem vindo{this.state.professor}!</h1>
                <p className="lead">Esse é seu sistema Apreender.</p>
                <hr className="my-4"/>
                <p>E essa é sua área administrativa, utilize um dos menus ou botões abaixo para navegar pelo sistema.</p>
                <p className="lead">
                  <a className="btn btn-primary btn-lg" 
                    href="#/login" 
                    role="button"><i className="pi pi-users"></i>  Login</a>
                  <a className="btn btn-danger btn-lg" 
                  href="#/cadastro-usuarios"
                   role="button"><i className="pi pi-pencil"></i>  Cadastrar Usuário</a>
                </p>
         </div>        
        )
    }
}

export default Home;
