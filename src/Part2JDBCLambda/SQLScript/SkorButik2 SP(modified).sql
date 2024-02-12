use Skorbutik2;

drop procedure if exists AddToCart;
delimiter //
create procedure AddToCart(in INkvittoid int, in INkundid int, in INproduktid int, in INantal int)
begin
	declare kvittoCount int default 0;
    declare ProduktCount int default 0;
    declare LagerAntal int default 0;
    
    declare exit handler for 1452
    begin
		rollback;
        select 'invalid kund id';
        resignal;
    end;
    
    declare exit handler for sqlexception
    begin
		rollback;
		select 'unavailable kvitto id / produkt id / too few in lager';
        resignal;
    end;
    
start transaction;    

    select count(*) into KvittoCount from Kvitto where Kvitto.id = INKvittoid;
    select count(*) into ProduktCount from Beställning where Beställning.produktid = INProduktid and Beställning.kvittoid=INKvittoid;
    select antalExample into LagerAntal from Produkt where Produkt.id = InProduktid;

    if LagerAntal < INantal then
        SIGNAL SQLSTATE '45000';
	end if;
    
	if kvittoCount = 0 and LagerAntal > 0 then
    -- if ingen kvitto förut, skappa en ny kvitto and beställning
		insert into Kvitto (kundid) values (INkundid);
        insert into Beställning values (null, last_insert_id(), INProduktid, INantal);
	else 
		if ProduktCount = 0 and LagerAntal > 0 then
        -- if kvitto finnas, lägga en ny beställning
			insert into Beställning values (null, INKvittoid, INProduktid, INantal);
		else
			-- if kvitto finnas och produkten har beställt tidigare, ökar beställning antal
			update Beställning set beställantal = beställantal + INantal where kvittoid = INKvittoid and produktid = INProduktid;
		end if;
    end if;
    -- minska antal i lager
	update Produkt set antalExample = antalExample-INantal where produkt.id = INProduktid;
commit;

    select * from Kvitto;
    select * from Beställning;
    select * from Produkt;
    
end //

delimiter ;