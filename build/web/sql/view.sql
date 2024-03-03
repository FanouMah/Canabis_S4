/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  PC
 * Created: 3 mars 2024
 */

/* recherche salleCulture */
select *
from salleCulture
where temperature between ? and ?
and humidite between ? and ?
and LOWER(nom_salle) like '%?%';

create or replace view v_plante_salleCulture as
select p.plante_id ,p.espece,p.variete,sc.salleculture_id,sc.nom_salle
from plante p
join salleCulture sc on p.salleCulture_id = sc.salleCulture_id;

/*recherche plante*/
select *
from v_plante_salleCulture
where LOWER(espece) like '%?%'
and LOWER(variete) like '%?%'
and LOWER(nom_salle) like '%?%';

/* recherche journalCulture */
select *
from journalCulture 
where LOWER(plante_id) like '%?%'
and date between ? and ?
and LOWER(etapecroissance) like '%?%'
and LOWER(notes) like '%?%';

/* recherche recolte */
select *
from recolte 
where LOWER(plante_id) like '%?%'
and date between ? and ?
and rendement between ? and ?
and LOWER(qualite) like '%?%';