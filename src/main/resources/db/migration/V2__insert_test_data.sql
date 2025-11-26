-- V2__insert_test_data.sql
-- Insere dados de teste nas tabelas tb_usuario, tb_registro_habito e tb_mapa_energia.

-- 1. Inserir Usuários de Teste (tb_usuario)
INSERT INTO tb_usuario (nome, email, senha_hash, status_conta)
VALUES ('Ana Silva', 'ana.silva@teste.com', 'hash_ana_123', 'ATIVA'),
       ('Bruno Costa', 'bruno.costa@teste.com', 'hash_bruno_456', 'ATIVA');

-- Capturar os IDs dos usuários inseridos (PostgreSQL)
DO
$$
DECLARE
ana_id BIGINT;
    bruno_id
BIGINT;
    id_hidratacao
INT;
    id_sono
INT;
    id_meditacao
INT;
    id_foco
INT;
    id_pausa
INT;
BEGIN
SELECT id
INTO ana_id
FROM tb_usuario
WHERE email = 'ana.silva@teste.com';
SELECT id
INTO bruno_id
FROM tb_usuario
WHERE email = 'bruno.costa@teste.com';

-- 2. Capturar os IDs dos Tipos de Hábito (tb_tipo_habito)
SELECT id
INTO id_hidratacao
FROM tb_tipo_habito
WHERE nome = 'Hidratação';
SELECT id
INTO id_sono
FROM tb_tipo_habito
WHERE nome = 'Qualidade do Sono';
SELECT id
INTO id_meditacao
FROM tb_tipo_habito
WHERE nome = 'Meditação/Respiração';
SELECT id
INTO id_foco
FROM tb_tipo_habito
WHERE nome = 'Foco/Trabalho';
SELECT id
INTO id_pausa
FROM tb_tipo_habito
WHERE nome = 'Pausa/Recuperação';

-- 3. Inserir Registros de Hábito para Ana (tb_registro_habito)
-- Registros de 3 dias atrás para a Ana
INSERT INTO tb_registro_habito (usuario_id, tipo_habito_id, valor_registro, data_registro)
VALUES (ana_id, id_hidratacao, 2.5, CURRENT_DATE - INTERVAL '3 days'), -- Hidratação: 2.5 Litros
       (ana_id, id_sono, 7.5, CURRENT_DATE - INTERVAL '3 days'),       -- Sono: 7.5 Horas

       -- Registros de 2 dias atrás para a Ana
       (ana_id, id_hidratacao, 2.0, CURRENT_DATE - INTERVAL '2 days'),
       (ana_id, id_meditacao, 10, CURRENT_DATE - INTERVAL '2 days'),   -- Meditação: 10 minutos
       (ana_id, id_foco, 180, CURRENT_DATE - INTERVAL '2 days'),       -- Foco/Trabalho: 180 minutos

       -- Registros de hoje para a Ana
       (ana_id, id_hidratacao, 1.0, CURRENT_DATE),
       (ana_id, id_pausa, 20, CURRENT_DATE);
-- Pausa/Recuperação: 20 minutos

-- 4. Inserir Registros de Hábito para Bruno (tb_registro_habito)
-- Registros de 1 dia atrás para o Bruno
INSERT INTO tb_registro_habito (usuario_id, tipo_habito_id, valor_registro, data_registro)
VALUES (bruno_id, id_hidratacao, 3.0, CURRENT_DATE - INTERVAL '1 day'),
       (bruno_id, id_sono, 8.0, CURRENT_DATE - INTERVAL '1 day');


-- 5. Inserir Registros do Mapa de Energia Diário (tb_mapa_energia)
-- Nível de energia (1 a 5)
INSERT INTO tb_mapa_energia (usuario_id, data_registro, nivel_energia, comentario)
VALUES (ana_id, CURRENT_DATE - INTERVAL '3 days', 3, 'Dia agitado, mas ok'),
       (ana_id, CURRENT_DATE - INTERVAL '2 days', 5, 'Máximo foco e produtividade'),
       (ana_id, CURRENT_DATE, 4, 'Energia boa, mas começando a cansar'),
       (bruno_id, CURRENT_DATE - INTERVAL '1 day', 2, 'Muito estressado no trabalho');

END $$;