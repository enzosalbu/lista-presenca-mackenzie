import React from "react";

export default (props) => {

    const rows = props.alunos.map( aluno => {
        return(
            <tr key={aluno.id}>
                <td>{aluno.aluno.nome}</td>
                
                <td>{String(aluno.presente) === 'true' ? 
                      (
                        <button type="button" title="Presente" className="btn btn-success" onClick={e => props.marcarAction(aluno)}>P</button>
                      ) : (
                        <button type="button" title="Faltou"className="btn btn-danger" onClick={e => props.marcarAction(aluno)}>F</button>
                      )
                    }</td>
                <td>{aluno.motivo}</td>
                <td> 
                        <button type="button" className="btn btn-warning" title="Informar Motivo" disabled={ String(aluno.presente) !== 'false' } onClick={e => props.infoAction(aluno)}><i className="pi pi-info-circle" ></i></button>                           
                </td>
            </tr>
        )
    })

    return(
        <table className="table table-hover">
            <thead>
                <tr>
                      <th scope="col">Aluno</th>
                      <th scope="col">Turma</th>
                      <th scope="col">Presente</th>
                      <th scope="col">Motivo</th>
                </tr>
            </thead>
            <tbody>
                {rows}
            </tbody>
        </table>
    )
}