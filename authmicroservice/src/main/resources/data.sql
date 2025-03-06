INSERT INTO role (id, name)
VALUES
    (1, 'CLIENT'),
    (2, 'COMPANY'),
    (3, 'ADMIN')
ON CONFLICT (id) DO NOTHING;

INSERT INTO users(id, email, password, reset_password_token, reset_password_expiry_date, refresh_token_key)
	VALUES (1, 'fidelidadeclubee@gmail.com', '$2a$10$Rn4LWGaZwJM2hE.TghIDDO/IgPVfhWs6QEwVh7QD/.B6KaDLNPUwO', null, null, 0) ON CONFLICT (id) DO NOTHING;

INSERT INTO public.user_role(user_id, role_id)
	VALUES
	    (1, 3) ON CONFLICT (user_id, role_id) DO NOTHING;

INSERT INTO public.promotions (promotion_id, company_id, promotion_name, points, reviews_rating, total_reviews, promotion_image, created_at)
VALUES
  (1, 1, 'Promoção Imperdível 1', 100, 4.5, 20, 'https://example.com/image1.jpg', '2025-02-01 10:00:00'),
  (2, 2, 'Desconto Especial 2', 200, 4.2, 15, 'https://example.com/image2.jpg', '2025-02-02 11:30:00'),
  (3, 1, 'Brinde Exclusivo 3', 150, 4.8, 30, 'https://example.com/image3.jpg', '2025-02-03 14:45:00'),
  (4, 3, 'Cupom de Desconto 4', 250, 4.0, 10, 'https://example.com/image4.jpg', '2025-02-04 09:15:00'),
  (5, 2, 'Promoção Relâmpago 5', 300, 4.7, 25, 'https://example.com/image5.jpg', '2025-02-05 16:00:00'),
  (6, 3, 'Frete Grátis 6', 120, 4.3, 18, 'https://example.com/image6.jpg', '2025-02-06 13:20:00'),
  (7, 1, 'Desconto Progressivo 7', 180, 4.6, 22, 'https://example.com/image7.jpg', '2025-02-07 08:40:00'),
  (8, 2, 'Compre 1 Leve 2 - 8', 220, 4.4, 19, 'https://example.com/image8.jpg', '2025-02-08 17:50:00'),
  (9, 3, 'Promoção de Verão 9', 160, 4.9, 35, 'https://example.com/image9.jpg', '2025-02-09 12:10:00'),
  (10, 1, 'Sorteio Exclusivo 10', 140, 4.1, 14, 'https://example.com/image10.jpg', '2025-02-10 15:30:00');



/**
    role:ADMIN
    email:fidelidadeclubee@gmail.com
    password:clubee.Ab31hinh@
**/