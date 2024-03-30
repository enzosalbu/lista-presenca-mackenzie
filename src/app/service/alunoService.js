import ApiService from "../apiservice";

class AlunoService extends ApiService{

    constructor(){
        super('/api/alunos')
    }
    consultar(){
        return this.get('/1')
    }
    
}

export default AlunoService