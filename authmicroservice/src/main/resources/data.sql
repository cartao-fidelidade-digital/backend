INSERT INTO role (id, name)
VALUES
    (1, 'CLIENT'),
    (2, 'COMPANY'),
    (3, 'ADMIN')
ON CONFLICT (id) DO NOTHING;

INSERT INTO users(id, email, password, reset_password_token, reset_password_expiry_date)
	VALUES (1, 'fidelidadeclubee@gmail.com', '$2a$10$XvRhKA/qQWdfQ.0ertGVnuugpK6iE08g/jVxS2riNOMUG6nsYOq5y', null, null) ON CONFLICT (id) DO NOTHING;

INSERT INTO public.user_role(user_id, role_id)
	VALUES
	    (1, 3) ON CONFLICT (user_id, role_id) DO NOTHING;


/**
    role:ADMIN
    email:fidelidadeclubee@gmail.com
    password:%{e9B<9WP7Ke>iZef7
**/