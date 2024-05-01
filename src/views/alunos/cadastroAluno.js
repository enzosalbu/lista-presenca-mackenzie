import React from "react";
import AlunoService from "../../app/service/alunoService";
import Card from "../../components/card";
import SelectMenu from "../../components/selectMenu";
import { Dialog } from "primereact/dialog";
import FormGroup from "../../components/form-group";
import * as messages from "../../components/toastr"
import AlunosTable from "./alunosTable";


class CadastroAluno extends React.Component{
    state ={
        nome: '',
        email: '',
        turma: '',
        alunos: []
        
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
                messages.mensagmeAlerta("Nenhum aluno encontrado");
            }
            this.setState({alunos: lista});
        }).catch(error => {
            console.log(error);
        })
    }

    handleChange = (event) =>{
        const value = event.target.value;
        const name = event.target.name;
        this.setState({ [name] : value });
    }
    render(){
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
                        
                </Card>

        )

    }
}
export default CadastroAluno;