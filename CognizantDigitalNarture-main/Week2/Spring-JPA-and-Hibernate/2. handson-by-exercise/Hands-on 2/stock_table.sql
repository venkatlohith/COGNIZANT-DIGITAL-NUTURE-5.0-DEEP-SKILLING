CREATE TABLE IF NOT EXISTS `ormlearn`.`stock` (
  `st_id`     INT NOT NULL AUTO_INCREMENT,
  `st_code`   VARCHAR(10),
  `st_date`   DATE,
  `st_open`   NUMERIC(10,2),
  `st_close`  NUMERIC(10,2),
  `st_volume` NUMERIC,
  PRIMARY KEY (`st_id`)
);

-- After creating the table, populate using stock-data.sql (generated from CSV)
-- Excel formula to generate insert statements (paste in F2 cell):
-- =CONCATENATE("insert into stock (st_code, st_date, st_open, st_close, st_volume) values (""", B2, """, """, YEAR(A2), "-", MONTH(A2), "-", DAY(A2), """, ", C2, ", ", D2, ", ", E2, ");")
-- Drag down for all rows, copy column F, save as stock-data.sql and run:
-- mysql> source D:\spring-data-jpa-files\stock-data.sql
