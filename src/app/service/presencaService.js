import ApiService from "../apiservice";

export default class PresencaService extends ApiService {
    constructor(){
        super('/api/chamadas')
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

     obterListaMotivos(){
        return [
             {label: 'Selecione...', value: ''},
             {label: 'Atestado', value: 'Atestado'},
             {label: 'Atraso', value: 'Atraso'},
             {label: 'Chuva', value: 'Chuva'},
             {label: 'Outro', value: 'Outro'}
            
         ]
     }
     obterPorId(id){
        return this.get(`/${id}`)
     }

     atualizaStatus(id){
        return this.put(`/${id}/realiza-chamada`)
     }
    consultar(chamadaFiltro){
        let params = `?data=${chamadaFiltro.data}`;
        if(chamadaFiltro.turma){
            params = `${params}&turma=${chamadaFiltro.turma}`
        }
        return this.get(params);
    }
    atualizar(chamada){
        return this.put(`/${chamada.id}`, chamada)
    }

}