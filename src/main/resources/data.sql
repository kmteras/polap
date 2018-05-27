CREATE OR REPLACE VIEW v_only_main_requests AS SELECT * FROM requests WHERE request_url NOT LIKE '%.%' AND request_url NOT LIKE '/api/%';
CREATE OR REPLACE VIEW v_only_main_requests_count AS SELECT COUNT(*) FROM requests WHERE request_url NOT LIKE '%.%' AND request_url NOT LIKE '/api/%';
CREATE OR REPLACE VIEW v_date_history AS SELECT FROM_UNIXTIME(FLOOR(UNIX_TIMESTAMP(date_time)/(60*60))*(60*60)) AS datetime, COUNT(id) AS count FROM v_only_main_requests GROUP BY FROM_UNIXTIME(FLOOR(UNIX_TIMESTAMP(date_time)/(60*60))*(60*60));
CREATE OR REPLACE VIEW v_os_request_count AS SELECT os_group, COUNT(os_group) FROM v_only_main_requests JOIN request_oss WHERE request_oss.id = v_only_main_requests.os_id GROUP BY os_group ORDER BY COUNT(os_group) DESC;
CREATE OR REPLACE VIEW v_browser_request_count AS SELECT name, COUNT(name) FROM v_only_main_requests JOIN request_browsers WHERE request_browsers.id = v_only_main_requests.browser_id GROUP BY name;
CREATE OR REPLACE VIEW v_poll_question_answers AS SELECT * FROM poll_question_answers;
CREATE OR REPLACE VIEW v_poll_questions AS SELECT * FROM poll_questions;
CREATE OR REPLACE VIEW v_request_browsers AS SELECT * FROM request_browsers;
CREATE OR REPLACE VIEW v_request_locations AS SELECT * FROM request_locations;
CREATE OR REPLACE VIEW v_request_oss AS SELECT * FROM request_oss;
CREATE OR REPLACE VIEW v_users AS SELECT * FROM users;

/*CREATE PROCEDURE
  top_countries()
BEGIN
  SELECT DISTINCT(country), COUNT(country)
    FROM requests JOIN request_locations
    WHERE request_locations.ip = requests.location_ip
    GROUP BY country
    ORDER BY COUNT(country) DESC;
END;

CREATE PROCEDURE
    top_cities()
BEGIN
  SELECT DISTINCT(country), COUNT(city), city
    FROM requests JOIN request_locations
    WHERE request_locations.ip = requests.location_ip
    GROUP BY country, city
    ORDER BY COUNT(city) DESC;
END;

CREATE PROCEDURE
    all_requests()
BEGIN
  SELECT * FROM requests JOIN request_locations
    WHERE request_locations.ip = requests.location_ip;
END;

CREATE PROCEDURE
  top_browsers()
BEGIN
  SELECT DISTINCT(name), COUNT(name) FROM requests
  JOIN request_browsers ON request_browsers.id = requests.browser_id
  GROUP BY browser_id, name
  ORDER BY COUNT(name) DESC;
END;

CREATE PROCEDURE
  request_history(IN start_time TIMESTAMP, IN end_time TIMESTAMP)
BEGIN
    SELECT * FROM requests
      WHERE request_url NOT LIKE '%.%'
        AND request_url NOT LIKE '/api/%'
        AND date_time > start_time
        AND date_time < end_time;
END;

CREATE PROCEDURE
  user_id(IN p_email VARCHAR(255), OUT p_id INT)
BEGIN
  SELECT id FROM users WHERE users.email = p_email INTO p_id;
END;

CREATE PROCEDURE
  user_answers(IN email VARCHAR(255))
BEGIN
  CALL user_id(email, @id);
  SELECT answer, checked, question, multiple_choice, title
  FROM poll_question_answers
    JOIN poll_questions question on poll_question_answers.question_id = question.id
    JOIN polls ON question.poll_id = polls.id
    WHERE poll_question_answers.creator_user_id=@id;
END;*/

