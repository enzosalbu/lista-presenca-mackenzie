import ApiService from "../apiservice";

class AlunoService extends ApiService{

    constructor(){
        super('/api/alunos')
    }
    obterListaTurmas(){
        return [
             {label: 'Selecione...', value: ''},
             {label: 'Turma 01', value: 'TURMA_01'},
             {label: 'Turma 02', value: 'TURMA_02'},
             {label: 'Turma 03', value: 'TURMA_03'},
             {label: 'Turma 04', value: 'TURMA_04'},
             {label: 'Turma 05', value: 'TURMA_05'}    
            
         ]
     }
    consultar(alunoFiltro){
        let params;
        if(alunoFiltro.turma){
            params = `?turma=${alunoFiltro.turma}`;
        }
        return this.get(params);
    }
    
}

export default AlunoService