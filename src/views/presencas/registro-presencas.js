import React from "react";
import Card from "../../components/card";
import FormGroup from "../../components/form-group";
import SelectMenu from "../../components/selectMenu";
import PresencasTable from "./presencasTable";
import { Dialog } from 'primereact/dialog';
import PresencaService from "../../app/service/presencaService";
import * as messages from "../../components/toastr"


class RegistroPresencas extends React.Component{
    state = {
        turma: '',
        presente: true,
        motivo:'',
        dataSelecionada: '',
        dataFormatada: '',        
        showInfoDialog: false,
        disableOutro: true,    
        alunoInformar: {},          
        alunos: []       
    }

    constructor(){
        super();
        this.service = new PresencaService();
    }

    realizar = () => {
        if(!this.state.dataSelecionada){
            messages.mensagemErro('Preencha o campo data.')
            return false;
        }if(!this.state.turma){
            messages.mensagemErro('Selecione a turma.')
            return false;
        }
        const chamadaFiltro = {
            data: this.state.dataSelecionada,
            turma: this.state.turma
        };
        this.service.consultar(chamadaFiltro)
        .then( response => {
            const lista = response.data;
            if(lista.length < 1){
                messages.mensagemAlerta("Nenhuma aula encontrada")
            }
            this.setState({alunos: lista})
            
        }).catch(error =>{
            console.log(error)
        })
    }

    marcar = (aluno) => {
        this.service.atualizaStatus(aluno.id)
        .then(response => {
            this.setState({...response.data})
            const alunos = this.state.alunos;
            const index = alunos.indexOf(aluno);
            if(index !== -1){
                aluno['motivo'] = this.state.motivo;
                aluno['presente'] = this.state.presente;
                alunos[index] = aluno
                this.setState({aluno});
            }            
            
        }).catch(error =>{
            console.log(error)
        })
        
    }

    salvaInfo = () => {
        const chamada = {
            id : this.state.alunoInformar.id,
            motivo: this.state.motivo,
            aluno: this.state.alunoInformar.aluno.id,
            data: this.state.data,
            presente: this.state.presente

        };
        this.service.atualizar(chamada)
        .then( response => {
            this.setState({showInfoDialog: false});
            this.realizar();
            messages.mensagemSucesso('Informações salvas com sucesso.');
        }).catch(error =>{
            console.log(error)
        })       
    }
   
    abrirInfo = (aluno) => {
        this.service.obterPorId(aluno.id)
        .then(response => { 
            this.setState({...response.data})
            const input = this.state.dataSelecionada;
            const [year, month, day] =  input.split('-')
            this.setState({ dataFormatada:  `${day}/${month}/${year}`, showInfoDialog: true, alunoInformar: aluno})
        }).catch(error =>{
            console.log(error)
        })       
    }

    fecharInfo = () => {
        this.setState({showInfoDialog: false, alunoInformar: {}})
    }

    setMotivo = (event) =>{
        this.setState({motivo: event.target.value});
        if(event.target.value === 'Outro'){
            this.setState({disableOutro: false});
        }else{
            this.setState({disableOutro: true});
        } 
    }

    handleChange = (event) =>{
        const value = event.target.value;
        const name = event.target.name;
        this.setState({ [name] : value })
    }
    render(){
        const footerContent = (
            <div>
                <button onClick={this.salvaInfo} className="btn btn-success"><i className="pi pi-check"></i> Salvar</button>
                <button onClick={this.fecharInfo} className="btn btn-danger"><i className="pi pi-times"></i> Cancelar</button>                        
            </div>
        );
        
        const turmas = this.service.obterListaTurmas();
        const motivos = this.service.obterListaMotivos();
        return (
                <Card title = "Registro de Presenças">
                    <div className = "row">
                        <div className="col-md-3">
                            <div className="bs-component">
                                <FormGroup htmlFor="inputData" label="Data: *">
                                    <input type="date" 
                                           className="form-control"
                                            id="inputData"
                                            value={this.state.dataSelecionada}
                                            onChange={e => this.setState({dataSelecionada: e.target.value})}
                                            placeholder="Escolha a data"/>
                                </FormGroup>
                                <FormGroup htmlFor="inputTurma" label="Turma">
                                    <SelectMenu id="inputTurma"
                                             value={this.state.turma}
                                             name="turma"
                                             onChange={this.handleChange}
                                             className ="form-control" lista={turmas}/>
                                </FormGroup>                               
                                    <button onClick={this.realizar}
                                            type="button"
                                            className="btn btn-success">
                                            <i className="pi pi-search"></i> Buscar</button>
                            </div>
                        </div>
                    </div>
                        <div className="row">
                            <div className="col-md-12">
                                <div className="bs-component">
                                    <PresencasTable alunos={this.state.alunos} marcarAction = {this.marcar} infoAction= {this.abrirInfo}/>
                                </div>
                            </div>
                        </div>
                    <div>
                        <Dialog header="Informar motivo"
                                visible={this.state.showInfoDialog}
                                style={{ width: '50vw' }}
                                onHide={() => this.setState({showInfoDialog: false})}
                                footer={footerContent}>
                                    {this.state.alunoInformar.aluno && (
                                    <p className="lead">Nome: {this.state.alunoInformar.aluno.nome}</p>)}
                                    <p className="lead">Data: {this.state.dataFormatada}</p>
                                        <FormGroup htmlFor="inputMotivo" label="Motivo">
                                                <SelectMenu id="inputMotivo"
                                                            value={this.state.motivo}
                                                            onChange={this.setMotivo}
                                                            className ="form-control" lista={motivos}/>
                                        </FormGroup>
                                        <FormGroup label="Outro: " htmlFor="inputOutro">
                                                <input type="text"
                                                        id="inputOutro"
                                                        className="form-control"
                                                        disabled={this.state.disableOutro}
                                                        name="motivo"
                                                        onChange={this.handleChange} />                                                                                 
                                        </FormGroup>   
                        </Dialog>
                    </div>
                </Card>
        )
    }
}

export default RegistroPresencas;