
/* 8.Calcular la apuesta media por partida. */

select avg(apuesta), idpartida
from turnos
group by idpartida;



