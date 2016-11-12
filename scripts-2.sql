create table paw1_produto(
	id int not null,
	codigo varchar(10) null,
	nome varchar(30) null,
	descricao varchar(255) null
);

alter table paw1_produto
add constraint pk_produto 
primary key(id);

create sequence paw1_produto_seq INCREMENT 1 MINVALUE 1 CACHE 1;
	
create table paw1_pedido(
	id int not null,
	nome_cliente varchar(30) null,
	data date not null
);

alter table paw1_pedido
add constraint pk_pedido 
primary key(id);

create sequence paw1_pedido_seq INCREMENT 1 MINVALUE 1 CACHE 1;

create table paw1_pedido_item(
	id int not null,
	quantidade int not null,
	id_produto int not null,
	id_pedido int not null
);

alter table paw1_pedido_item
add constraint pk_pedido_item 
primary key(id);

alter table paw1_pedido_item
add constraint pk_pedido_item_pedido 
foreign key(id_pedido)
references paw1_pedido(id);

alter table paw1_pedido_item
add constraint pk_pedido_item_produto 
foreign key(id_produto)
references paw1_produto(id);

create sequence paw1_pedido_item_seq INCREMENT 1 MINVALUE 1 CACHE 1;


insert into paw1_produto values (nextval('paw1_produto_seq'), 'A1', 'Caneta hidrocor 12 cores', 'Caneta hidrocor 12 cores para pintar');
insert into paw1_produto values (nextval('paw1_produto_seq'), 'A2', 'Caneta hidrocor 24 cores', 'Caneta hidrocor 24 cores para pintar mais');
insert into paw1_produto values (nextval('paw1_produto_seq'), 'A3', 'Caneta hidrocor 36 cores', 'Caneta hidrocor 36 cores para pintar 3 X mais');
insert into paw1_produto values (nextval('paw1_produto_seq'), 'A4', 'Caneta hidrocor 48 cores', 'Caneta hidrocor 48 cores: Pô, tu gosta de pintar, hein?');
insert into paw1_produto values (nextval('paw1_produto_seq'), 'B1', 'Lápis preto', 'Lápis simples');
insert into paw1_produto values (nextval('paw1_produto_seq'), 'C1', 'Papel A4', 'Bloco com 100 folhas');
insert into paw1_produto values (nextval('paw1_produto_seq'), 'C2', 'Papel A4 500fls', 'Bloco com 500 folhas');
insert into paw1_produto values (nextval('paw1_produto_seq'), 'B2', 'Caneta Bic Azul', 'Caneta Bic Azul');
SELECT currval('paw1_produto_seq');

insert into paw1_pedido values (nextval('paw1_pedido_seq'), 'João da Silva', CURRENT_DATE);
insert into paw1_pedido_item (id, quantidade, id_produto, id_pedido) values (nextval('paw1_pedido_item_seq'), 4, 1, 1);
insert into paw1_pedido_item (id, quantidade, id_produto, id_pedido) values (nextval('paw1_pedido_item_seq'), 4, 2, 1);

insert into paw1_pedido values (nextval('paw1_pedido_seq'), 'Maria dos Anjos', CURRENT_DATE);
insert into paw1_pedido_item (id, quantidade, id_produto, id_pedido) values (nextval('paw1_pedido_item_seq'), 3, 5, 2);
insert into paw1_pedido_item (id, quantidade, id_produto, id_pedido) values (nextval('paw1_pedido_item_seq'), 3, 6, 2);

select pe.nome_cliente, pr.nome, pi.quantidade
from paw1_pedido pe, paw1_produto pr, paw1_pedido_item pi
where pe.id = pi.id_pedido
and pi.id_produto = pr.id
and pe.nome_cliente like '%'

-- ou 

select pe.nome_cliente, pr.nome, pi.quantidade
from paw1_pedido pe, paw1_produto pr, paw1_pedido_item pi
where pe.id = pi.id_pedido
and pi.id_produto = pr.id
and pe.id = 1

create table paw1_dado_binario(
	id int not null,
	conteudo bytea,
	tipo varchar(50) not null,
	tamanho int,
	nome_arquivo varchar(1024) not null
);

alter table paw1_dado_binario
add constraint pk_dado_binario 
primary key(id);

create sequence paw1_dado_binario_seq INCREMENT 1 MINVALUE 1 CACHE 1;

create table paw1_produto_dado_binario(
	id int not null,
	id_produto int not null,
	id_dado_binario int not null
);

alter table paw1_produto_dado_binario
add constraint pk_produto_dado_binario 
primary key(id);

alter table paw1_produto_dado_binario
add constraint fk_produto_dado_binario_produto 
foreign key(id_produto)
references paw1_produto(id);

alter table paw1_produto_dado_binario
add constraint fk_produto_dado_binario_dado_binario 
foreign key(id_dado_binario)
references paw1_dado_binario(id);

create sequence paw1_produto_dado_binario_seq INCREMENT 1 MINVALUE 1 CACHE 1;
