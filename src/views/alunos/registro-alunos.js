import React from "react";
import AlunoService from "../../app/service/alunoService";
import Card from "../../components/card";
import SelectMenu from "../../components/selectMenu";
import { Dialog } from "primereact/dialog";
import FormGroup from "../../components/form-group";
import * as messages from "../../components/toastr"
import AlunosTable from "./alunosTable";


class RegistroAlunos extends React.Component{
    state ={
        nome: '',
        email: '',
        turma: '',
        showCadastroDialog: false,
        nomeNovo: '',
        turmaNovo: '',
        emailNovo: '',
        alunos: [],
        alunoCadastrar:{}       
    }
    constructor(){
        super();
        this.service = new AlunoService();
    }

    buscar = () => {
        const alunoFiltro = {
            turma: this.state.turma
        }
        this.service.consultar(alunoFiltro)
        .then(response => {
            const lista = response.data;
            if(lista.length < 1){
                messages.mensagemAlerta("Nenhum aluno encontrado");
            }
            this.setState({alunos: lista});
        }).catch(error => {
            console.log(error);
        })
    }
    salvarAluno = () => {
        if(!this.state.nomeNovo){
            messages.mensagemErro("O campo Nome é obrigatório.");
            return false;
        }else if(!this.state.emailNovo){
            messages.mensagemErro('O campo Email é obrigatório.');
            return false;
        }else if(!this.state.emailNovo.match(/^[a-z0-9.]+@[a-z0-9]+\.[a-z]/)){
            messages.mensagemErro('Informe um Email Válido.');
            return false;
        }else if(!this.state.turmaNovo){
            messages.mensagemErro('Preencha a turma.');
            return false;
        }

        const aluno = {
            nome: this.state.nomeNovo,
            turma: this.state.turmaNovo,
            email: this.state.emailNovo
        }
        this.service.salvar(aluno)
        .then(response => {
            messages.mensagemSucesso("Aluno Cadastrado com Sucesso!");
            this.setState({turma: '', showCadastroDialog: false});
            this.buscar();
        }).catch(error => {
            console.log(error);
        })

    }
    fecharAluno = () => {
        this.setState({showCadastroDialog: false, alunoCadastrar: {}});
    }
    abrirAluno = () => {
        this.setState({showCadastroDialog: true});
    }

    handleChange = (event) =>{
        const value = event.target.value;
        const name = event.target.name;
        this.setState({ [name] : value });
    }
    render(){
        const footerContent = (
            <div>
                <button onClick={this.salvarAluno} className="btn btn-success"><i className="pi pi-check"></i> Salvar</button>
                <button onClick={this.fecharAluno} className="btn btn-danger"><i className="pi pi-times"></i> Cancelar</button>                        
            </div>
        );
        const turmas = this.service.obterListaTurmas();
        return(
            
        <Card title = "Alunos">
                    <div className = "row">
                        <div className="col-md-3">
                            <div className="bs-component">
                                <FormGroup htmlFor="inputTurma" label="Turma: ">
                                    <SelectMenu id="inputTurma"
                                             value={this.state.turma}
                                             name="turma"
                                             onChange={this.handleChange}
                                             className ="form-control" lista={turmas}/>
                                </FormGroup>                               
                                    <button onClick={this.buscar}
                                            type="button"
                                            className="btn btn-success">
                                            <i className="pi pi-search"></i> Buscar</button>
                                            <button onClick={this.abrirAluno}
                                            type="button"
                                            className="btn btn-success">
                                            <i className="pi pi-pencil"></i> Cadastrar</button>
                            </div>
                        </div>
                    </div>
                    <div className="row">
                            <div className="col-md-12">
                                <div className="bs-component">
                                    <AlunosTable alunos={this.state.alunos}/>
                                </div>
                            </div>
                        </div>
                        <div>
                        <Dialog header="Cadastrar Aluno"
                                visible={this.state.showCadastroDialog}
                                style={{ width: '50vw' }}
                                onHide={() => this.setState({showCadastroDialog: false})}
                                footer={footerContent}>
                                    
                                        
                                        <FormGroup label="Nome: " htmlFor="inputNome">
                                                <input type="text"
                                                        id="inputNome"
                                                        className="form-control"                                               
                                                        name="nomeNovo"
                                                        onChange={this.handleChange} />                                                                                 
                                        </FormGroup>
                                        <FormGroup htmlFor="inputTurma" label="Turma">
                                        <SelectMenu id="inputTurma"
                                             value={this.state.turmaNovo}
                                             name="turmaNovo"
                                             onChange={this.handleChange}
                                             className ="form-control" lista={turmas}/>
                                </FormGroup>       
                                        <FormGroup label="Email: " htmlFor="inputEmail">
                                                <input type="email"
                                                        id="inputNome"
                                                        className="form-control"                                               
                                                        name="emailNovo"
                                                        onChange={this.handleChange}/>                                                                                 
                                        </FormGroup>                        
                        </Dialog>
                    </div>
                </Card>
        )

    }
}
export default RegistroAlunos;