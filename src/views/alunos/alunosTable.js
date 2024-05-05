import React, { useEffect, useState } from "react";
import AlunoService from "../../app/service/alunoService";

const AlunosTable = (props) => {
    const [frequencias, setFrequencias] = useState({});

    useEffect(() => {
        const fetchData = async () => {
            const frequenciasData = {};
            const service = new AlunoService(); 
            for (const aluno of props.alunos) {
                const frequencia = await service.obterFrequencia(aluno.id); 
                frequenciasData[aluno.id] = frequencia;
            }
            setFrequencias(frequenciasData);
        };

        fetchData();
    }, [props.alunos]);

    const rows = props.alunos.map(aluno => (
        <tr key={aluno.id}>
            <td>{aluno.nome}</td>
            <td>{aluno.turma}</td>
            <td>{aluno.email}</td>
            <td>{frequencias[aluno.id] && frequencias[aluno.id].data}</td>

        </tr>
    ));

    return (
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
    );
};

export default AlunosTable;
