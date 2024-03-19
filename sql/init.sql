CREATE TABLE tb_cliente (
    id SERIAL PRIMARY KEY,
    limite BIGINT,
    saldo BIGINT
);

CREATE TABLE tb_transacao (
  id SERIAL PRIMARY KEY,
  valor BIGINT,
  tipo CHAR,
  realizada_em TIMESTAMP,
  descricao VARCHAR(10),
  cliente_id INT REFERENCES tb_cliente(id) NOT NULL
);

INSERT INTO tb_cliente (limite, saldo) VALUES
(100000, 0),
(80000, 0),
(1000000, 0),
(10000000, 0),
(500000, 0);