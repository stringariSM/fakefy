create table paw1_curso(
	id int not null,
	codigo varchar(10) null,
	nome varchar(30) null
);

alter table paw1_curso
add constraint pk_curso 
primary key(id);

create table paw1_aluno(
    id int not null,
	matricula varchar(10) null,
	nome varchar(30) null,
	curso varchar(30) null,
	curso_id int null
);

alter table paw1_aluno
add constraint pk_aluno 
primary key(id);

alter table paw1_aluno
add constraint fk_curso_aluno
foreign key(curso_id)
references paw1_curso(id);

create sequence paw1_aluno_seq INCREMENT 1 MINVALUE 5 CACHE 1;

insert into paw1_curso values (4, '44', 'Engenharia Elétrica');
insert into paw1_curso values (5, '55', 'Engenharia de Computação');
insert into paw1_curso values (6, '66', 'Engenharia de Materiais');
insert into paw1_curso values (7, '77', 'Engenharia Química');
insert into paw1_curso values (8, '88', 'Engenharia de Alimentos');
insert into paw1_curso values (9, '99', 'Sociologia');
insert into paw1_curso values (10, '1010', 'Psicologia');
insert into paw1_curso values (11, '1111', 'Filosofia');
insert into paw1_curso values (12, '1212', 'Medicina');
insert into paw1_curso values (13, '1313', 'Letras');
insert into paw1_curso values (14, '1414', 'História');
insert into paw1_curso values (15, '1515', 'Direito');
insert into paw1_curso values (16, '1616', 'Administração de Empresas');
insert into paw1_curso values (17, '1717', 'Arquitetura e Urbanismo');

insert into paw1_aluno values(1,'1234','João da Silva',4);
insert into paw1_aluno values(2,'6678','Pedro Tomé',12);
insert into paw1_aluno values(3,'1444','Joana da Silva',16);
insert into paw1_aluno values(4,'2134','Arlindo Olaria',15);

create sequence paw1_curso_seq INCREMENT 1 MINVALUE 18 CACHE 1;

create table paw1_disciplina(
	id int not null,
	codigo varchar(10) null,
	nome varchar(30) null,
	creditos integer not null
);

alter table paw1_disciplina
add constraint pk_disiplina 
primary key(id);

create sequence paw1_disiplina_seq INCREMENT 1 MINVALUE 1 CACHE 1;

alter table paw1_aluno 
add curso_id int null;

alter table paw_aluno 
add constraint fk_aluno_curso 
foreign key (curso_id)
references paw1_curso(id);
