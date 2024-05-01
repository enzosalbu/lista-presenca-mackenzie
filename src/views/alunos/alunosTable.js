import React from "react";

export default (props) => {
    const rows = props.alunos.map(aluno => {

        return(
            <tr key={aluno.id}>
                <td>{aluno.nome}</td>
                <td>{aluno.turma}</td>
                <td>{aluno.email}</td>
                
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
                </tr>
            </thead>
            <tbody>
                {rows}
            </tbody>
        </table>
    )
}