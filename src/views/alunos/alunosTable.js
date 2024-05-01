import React from "react";

export default (props) => {
    const rows = props.alunos.map(aluno => {

        return(
            <tr key={aluno.id}>
                <td>{aluno.aluno.nome}</td>
                <td>{aluno.aluno.turma}</td>
                <td>{aluno.aluno.email}</td>
                <td>{aluno.aluno.frequencia}</td>
                
            </tr>
        )
        

    })
    return(
        <table className="table table-hover">
            <thead>
                <tr>
                      <th scope="col">Aluno</th>
                      <th scope="col">Turma</th>
                      <th scope="col">Email</th>
                      <th scope="col">Frequência</th>
                </tr>
            </thead>
            <tbody>
                {rows}
            </tbody>
        </table>
    )
}