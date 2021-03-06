delete vehicle;
delete vehicletype;
delete manufacturer;
delete location;

--alter table manufacturer add column active boolean not null;


insert into location (id, country, state) values (1, 'United States', 'Alabama');
insert into location (id, country, state) values (2, 'United States', 'Alaska');
insert into location (id, country, state) values (3, 'United States', 'Arizona');
insert into location (id, country, state) values (4, 'United States', 'Arkansas');
insert into location (id, country, state) values (5, 'United States', 'California');
insert into location (id, country, state) values (6, 'United States', 'Colorado');
insert into location (id, country, state) values (7, 'United States', 'Connecticut');
insert into location (id, country, state) values (8, 'United States', 'Delaware');
insert into location (id, country, state) values (9, 'United States', 'Florida');
insert into location (id, country, state) values (10, 'United States', 'Georgia');
insert into location (id, country, state) values (11, 'United States', 'Hawaii');
insert into location (id, country, state) values (12, 'United States', 'Idaho');
insert into location (id, country, state) values (13, 'United States', 'Illinois');
insert into location (id, country, state) values (14, 'United States', 'Indiana');
insert into location (id, country, state) values (15, 'United States', 'Iowa');
insert into location (id, country, state) values (16, 'United States', 'Kansas');
insert into location (id, country, state) values (17, 'United States', 'Kentucky');
insert into location (id, country, state) values (18, 'United States', 'Louisiana');
insert into location (id, country, state) values (19, 'United States', 'Maine');
insert into location (id, country, state) values (20, 'United States', 'Maryland');
insert into location (id, country, state) values (21, 'United States', 'Massachusetts');
insert into location (id, country, state) values (22, 'United States', 'Michigan');
insert into location (id, country, state) values (23, 'United States', 'Minnesota');
insert into location (id, country, state) values (24, 'United States', 'Mississippi');
insert into location (id, country, state) values (25, 'United States', 'Missouri');
insert into location (id, country, state) values (26, 'United States', 'Montana');
insert into location (id, country, state) values (27, 'United States', 'Nebraska');
insert into location (id, country, state) values (28, 'United States', 'Nevada');
insert into location (id, country, state) values (29, 'United States', 'New Hampshire');
insert into location (id, country, state) values (30, 'United States', 'New Jersey');
insert into location (id, country, state) values (31, 'United States', 'New Mexico');
insert into location (id, country, state) values (32, 'United States', 'New York');
insert into location (id, country, state) values (33, 'United States', 'North Carolina');
insert into location (id, country, state) values (34, 'United States', 'North Dakota');
insert into location (id, country, state) values (35, 'United States', 'Ohio');
insert into location (id, country, state) values (36, 'United States', 'Oklahoma');
insert into location (id, country, state) values (37, 'United States', 'Oregon');
insert into location (id, country, state) values (38, 'United States', 'Pennsylvania');
insert into location (id, country, state) values (39, 'United States', 'Rhode Island');
insert into location (id, country, state) values (40, 'United States', 'South Carolina');
insert into location (id, country, state) values (41, 'United States', 'South Dakota');
insert into location (id, country, state) values (42, 'United States', 'Tennessee');
insert into location (id, country, state) values (43, 'United States', 'Texas');
insert into location (id, country, state) values (44, 'United States', 'Utah');
insert into location (id, country, state) values (45, 'United States', 'Vermont');
insert into location (id, country, state) values (46, 'United States', 'Virginia');
insert into location (id, country, state) values (47, 'United States', 'Washington');
insert into location (id, country, state) values (48, 'United States', 'West Virginia');
insert into location (id, country, state) values (49, 'United States', 'Wisconsin');
insert into location (id, country, state) values (50, 'United States', 'Wyoming');

insert into manufacturer (id, name, location_id, averageYearlySales, foundedDate, active) values (1, 'General Motors Corporation', 22, 25000000, '1946-01-01', true);
insert into manufacturer (id, name, location_id, averageYearlySales, foundedDate, active) values (2, 'Ford Corporation', 22, 32000000, '1902-01-01', true);
insert into manufacturer (id, name, location_id, averageYearlySales, foundedDate, active) values (3, 'Boeing Corporation', 13, 32000000, '1902-01-01', true);
insert into manufacturer (id, name, location_id, averageYearlySales, foundedDate, active) values (4, 'AlumaCraft Corporation', 3, 32000000, '1902-01-01', false);

insert into vehicletype (id, name) values (1, 'Car');
insert into vehicletype (id, name) values (2, 'Truck');
insert into vehicletype (id, name) values (3, 'Van');
insert into vehicletype (id, name) values (4, 'SUV');
insert into vehicletype (id, name) values (5, 'Airplane');
insert into vehicletype (id, name) values (6, 'Drone');
insert into vehicletype (id, name) values (7, 'Amphibian');
insert into vehicletype (id, name) values (8, 'Boat');
insert into vehicletype (id, name) values (9, null);

insert into vehicle (id, name, manufacturer_id, vehicletype_id, price, yearfirstmade, batchno) values (1, 'Chevrolet Bolt EV', 1, 1, 10000, '1954-01-01', 21);
insert into vehicle (id, name, manufacturer_id, vehicletype_id, price, yearfirstmade, batchno) values (2, 'Buick Verano', 1, 1, 18000,  '1950-01-01', 22);
insert into vehicle (id, name, manufacturer_id, vehicletype_id, price, yearfirstmade, batchno) values (3, 'Chevrolet Volt', 1, 1, 16000, '1963-01-01', 19);
insert into vehicle (id, name, manufacturer_id, vehicletype_id, price, yearfirstmade, batchno) values (4, 'FIESTA', 2, 1, 14205, '1954-01-01', 21);
insert into vehicle (id, name, manufacturer_id, vehicletype_id, price, yearfirstmade, batchno) values (5, 'Focus', 2, 1, 17950,  '1950-01-01', 22);
insert into vehicle (id, name, manufacturer_id, vehicletype_id, price, yearfirstmade, batchno) values (6, 'Fusion', 2, 1, 22215, '1963-01-01', 19);
insert into vehicle (id, name, manufacturer_id, vehicletype_id, price, yearfirstmade, batchno) values (7, 'F-150', 2, 2, 22215, '1963-01-01', 19);
insert into vehicle (id, name, manufacturer_id, vehicletype_id, price, yearfirstmade, batchno) values (8, 'TRANSIT', 2, 3, 22215, '1963-01-01', 19);
insert into vehicle (id, name, manufacturer_id, vehicletype_id, price, yearfirstmade, batchno) values (9, 'ECOSPORT', 2, 4, 22215, '1963-01-01', 19);
insert into vehicle (id, name, manufacturer_id, vehicletype_id, price, yearfirstmade, batchno) values (10, 'ESCAPE', 2, 4, 22215, '1963-01-01', 19);
insert into vehicle (id, name, manufacturer_id, vehicletype_id, price, yearfirstmade, batchno) values (11, 'Boeing 737', 3, 5, 2222215, '1963-01-01', 19);
insert into vehicle (id, name, manufacturer_id, vehicletype_id, price, yearfirstmade, batchno) values (12, 'Boeing 747', 3, 5, 2222215, '1963-01-01', 19);
