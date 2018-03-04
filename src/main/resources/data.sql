CREATE OR REPLACE VIEW only_main_requests AS SELECT * FROM requests WHERE request_url NOT LIKE '%.%' AND request_url NOT LIKE '/api/%';
