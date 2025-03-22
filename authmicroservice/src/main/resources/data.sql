INSERT INTO category (name)
VALUES
    ('ALIMENTACAO'),
    ('PETSHOP'),
    ('PRODUTOS_ARTESANAIS'),
    ('PAPELARIA'),
    ('FLORES_E_PLANTAS'),
    ('BELEZA_E_ESTETICA'),
    ('MANUTENCAO_AUTOMOVEIS'),
    ('LIMPEZA_AUTOMOVEIS'),
    ('LIVRARIA'),
    ('PERFUMARIA'),
    ('VESTUARIO_CALCADOS'),
    ('INFORMATICA_ELETRONICOS')
    ON CONFLICT (name) DO NOTHING;

INSERT INTO users (id, email, password, role, active)
VALUES (
           1,
           'fidelidadeclubee@gmail.com',
           '$2a$10$Rn4LWGaZwJM2hE.TghIDDO/IgPVfhWs6QEwVh7QD/.B6KaDLNPUwO',
           'ADMIN',
           true
       )
    ON CONFLICT (id) DO NOTHING;