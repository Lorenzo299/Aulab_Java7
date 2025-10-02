INSERT INTO authors (firstname, lastname, email)
value ("lollo", "micio", "lollo@micio.it");

INSERT INTO authors (firstname, lastname, email)
value ("fofo", "ciotti", "fofo@ciotti.it");

INSERT INTO posts (title, body, publish_date, author_id) SELECT 'primopost', 'body body body body0', null, id FROM authors where firstname= 'fofo' and lastname= 'ciotti';

INSERT INTO posts (title, body, publish_date, author_id) SELECT 'secondoopost', 'bodyyyy bodyyyy body body0', null, id FROM authors where firstname= 'fofo' and lastname= 'ciotti';

INSERT INTO comments (email, body, date, post_id) SELECT 'primocomment', 'body body body body0', null, id FROM posts where title= 'primopost';

INSERT INTO comments (email, body, date, post_id) SELECT 'secondocommmeent', 'bodyyyy bodyyyy body body0', null, id FROM posts where title= 'primopost';