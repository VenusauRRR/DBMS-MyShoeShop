use SkorButik2;

drop view if exists ProduktDetail;
create view ProduktDetail as
select produkt.id as produkt_id, model.id as model_id, märke.namn as märke, färg.namn as färg, storlek.size, produkt.antalExample, pris as unit_pris 
from produkt
inner join model on produkt.modelid=model.id
inner join märke on model.MärkeID=märke.id
inner join färg on model.färgid=färg.id
inner join storlek on produkt.storlekid=storlek.id;
select * from ProduktDetail;

drop view if exists KundKvittoDeatils;
create view KundKvittoDeatils as
select kvitto.id as Kvitto_id, beställning.id as Beställ_id, kund.id as kund_id, kund.förnamn, kund.efternamn, beställning.produktID as produkt_id, produktDetail.model_id, produktDetail.unit_pris, beställantal, created
from beställning
left join Kvitto on beställning.kvittoID=kvitto.id
left join kund on kvitto.kundid=kund.id
inner join produktDetail on beställning.produktID=produktDetail.produkt_id;
select * from KundKvittoDeatils;

/* Vilka kunder har köpt svarta sandaler i storlek 38 av märket Ecco? Lista deras namn och
använd inga hårdkodade id-nummer i din fråga. */

select KundKvittoDeatils.förnamn, KundKvittoDeatils.efternamn
from KundKvittoDeatils
inner join produktDetail on KundKvittoDeatils.produkt_id=produktDetail.produkt_id
left join kategorization on produktDetail.model_id=kategorization.modelid
left join kategori on kategorization.kategoriid=kategori.id
where produktDetail.märke='ecco' and produktDetail.size=38 and kategori.namn='sandal';

/* Lista antalet produkter per kategori. Listningen ska innehålla kategori-namn och antalet
produkter. */

select kategori.Namn as kategori_namn, count(kategorization.KategoriID) as antal_produkt 
from kategorization
right join kategori on kategorization.KategoriID=kategori.id 
group by kategori.Namn;

/* Skapa en kundlista med den totala summan pengar som varje kund har handlat för. Kundens
för- och efternamn, samt det totala värdet som varje person har shoppats för, skall visas. */

select förnamn, efternamn, sum(unit_pris*beställantal) as sum
from KundKvittoDeatils
group by kund_id;

/* Skriv ut en lista på det totala beställningsvärdet per ort där beställningsvärdet är större än
1000 kr. Ortnamn och värde ska visas. (det måste finnas orter i databasen där det har
handlats för mindre än 1000 kr för att visa att frågan är korrekt formulerad)*/

select ortnamn, sum(unit_pris*beställantal) as sum
from KundKvittoDeatils
inner join kund on KundKvittoDeatils.kund_id=kund.id
inner join Ort on Ort.id=kund.ortid
group by ort.ortnamn having sum>1000;

/* Skapa en topp-5 lista av de mest sålda produkterna.produktdetail */

select ProduktDetail.model_id, ProduktDetail.märke, ProduktDetail.färg, sum(beställning.beställantal) as saldo 
from beställning
inner join ProduktDetail on beställning.produktid=ProduktDetail.produkt_id
group by ProduktDetail.model_id 
order by saldo desc limit 5;

/* Vilken månad hade du den största försäljningen? (det måste finnas data som anger
försäljning för mer än en månad i databasen för att visa att frågan är korrekt formulerad) */

select month(created) as beställ_month, sum(beställantal*unit_pris) as OrderSum
from KundKvittoDeatils
group by beställ_month 
order by beställ_month;

-- ******************************

-- att lista alla produkter from vissa kvitto
create index IX_KvittoContent on Beställning (kvittoid);

-- *******************************

-- create procedure AddToCart(in INkvittoid int, in INkundid int, in INproduktid int, in INantal int)
call AddToCart(null,1,1,1);
call AddToCart(1,null,1,1);
call AddToCart(1,null,6,1);

call AddToCart(100,null,1,1);
call AddToCart(null,100,1,1);
call AddToCart(1,null,100,1);
call AddToCart(1,null,1,100);
/*
    select * from Kvitto;
    select * from Beställning;
    select * from Produkt;
*/
