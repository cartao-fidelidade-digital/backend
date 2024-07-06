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


/**
    role:ADMIN
    email:fidelidadeclubee@gmail.com
    password:clubee.Ab31hinh@
**/