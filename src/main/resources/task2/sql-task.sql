CREATE TABLE addresses (
	id serial primary key,
	postcode text NOT NULL,
	city text NOT NULL,
	street text NOT NULL,
	house_number text NOT NULL
);

CREATE TABLE patients (
	id serial primary key,
	address_id integer,
	first_name text NOT NULL,
	last_name text NOT NULL,
	mother_name text,
	gender text NOT NULL,
	date_of_birth date NOT NULL,
	date_of_death date,
	place_of_birth text NOT NULL,
	phone_number text,
	email text,
	CONSTRAINT fk_patients_addresses FOREIGN KEY (address_id) REFERENCES addresses (id) ON DELETE SET NULL
);

CREATE TYPE connection_type as enum (
	'MOTHER', 'FATHER', 'SIBLING', 'NEIGHBOR', 'COLLEAGUE', 'HUSBAND', 'WIFE', 'PARTNER', 'DAUGHTER', 'SON'
);

CREATE TYPE connection_quality as enum (
	'POSITIVE', 'NEGATIVE', 'NEUTRAL'
);

CREATE TABLE connections (
	id serial primary key,
	type connection_type NOT NULL,
	quality connection_quality NOT NULL,
	distance smallint NOT NULL,
	start_date date NOT NULL,
	end_date date,
	patient1_id bigint NOT NULL,
	patient2_id bigint NOT NULL
);

ALTER TABLE connections ADD CONSTRAINT fk_connections_patients_patient1_id FOREIGN KEY (patient1_id) REFERENCES patients (id);
ALTER TABLE connections ADD CONSTRAINT fk_connections_patients_patient2_id FOREIGN KEY (patient2_id) REFERENCES patients (id);


CREATE OR REPLACE FUNCTION trigger_function() 
   RETURNS TRIGGER 
AS 
$func$
DECLARE b1 date; b2 date;
BEGIN
	SELECT p1.date_of_birth into b1 FROM patients p1 WHERE p1.id = NEW.patient1_id;
	SELECT p2.date_of_birth into b2 FROM patients p2 WHERE p2.id = NEW.patient2_id;

	IF b1 > NEW.start_date
		THEN RAISE NOTICE 'Value b1: %', b1;
		NEW.start_date := b1;
   	END IF;
   	
	IF  b2 > NEW.start_date 
		THEN RAISE NOTICE 'Value b2: %', b2;
		NEW.start_date := b2;
   	END IF;
	RETURN NEW;
END
$func$ LANGUAGE plpgsql;


CREATE TRIGGER trg_patient_new_connection 
AFTER INSERT ON connections
FOR EACH ROW EXECUTE PROCEDURE trigger_function();


DO
$$
DECLARE rec RECORD; mother TEXT;
BEGIN
	FOR rec IN SELECT id, mother_name FROM patients
	LOOP
	IF rec.mother_name IS NULL 
		THEN
			SELECT first_name || ' ' || last_name INTO mother 
			FROM patients p 
			LEFT JOIN connections c ON p.id = c.patient2_id 
			WHERE c.type = 'MOTHER';
			RAISE NOTICE 'Value mother: %', mother;
			UPDATE patients SET mother_name = mother WHERE id = rec.id; 
			
	END IF;
	END LOOP;
END;
$$;