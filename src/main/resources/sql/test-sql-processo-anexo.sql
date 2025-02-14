/* Teste de persistência de finalidades. 
Ao copiar as finalidades requeridas nas finalidades autorizadas está sendo deletadas ou salvas
de acordo com o que o usuário quiser. 

Funcionando.
*/
select _f.finalidade, _f.subfinalidade, _i.id id_interferencia, _e.logradouro  from finalidade _f
left join interferencia _i on _i.id = _f.interferencia
left join endereco _e on _e.id = _i.endereco
where _i.id = 10


/* Teste de persistencia de processo e anexo
Problema: ao editar documento inserindo anexo diferente do anexo relacionado com o processo

Não está funcionando
*/



select _d.id d_id, _d.numero d_numero, _p.id p_id, _p.numero p_numero, _a.id a_id, _a.numero an_numero from processo _p
left join documento _d on _d.processo = _p.id
left join anexo _a on _a.id = _p.anexo
