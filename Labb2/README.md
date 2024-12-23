Editeringsavståndet mellan två ord är det minimala antalet bokstavsoperationer som krävs för att 
transformera det ena ordet till det andra. Det finns tre tillåtna bokstavsoperationer:

1. Ta bort en av bokstäverna i ordet

2. Lägg till en bokstav någonstans i ordet

3. Byt ut en bokstav i ordet mot en annan bokstav

Till exempel kan ordet **alroitm** transformeras till **algoritm** genom att bokstaven _r_ byts ut mot _g_ (regel 3) och 
bokstaven _r_ skjuts in efter bokstaven _o_ (regel 2).

alroitm -> algoitm -> algoritm

Kedjan visar att editeringsavståndet mellan **alroitm** och **algoritm** är högst 2. Eftersom det inte går att 
transformera **alroitm** till **algoritm** i en enda bokstavsoperation så är editeringsavståndet mellan orden precis 2.

Ett vanligt sätt att ta fram rättstavningsförslag till ett felstavat ord är att helt enkelt returnera dom ord i ordlistan 
som har minst editeringsavstånd till det felstavade ordet. Programmet ska givet en ordlista och ett antal felstavade ord beräkna 
rättstavningsförslag på detta sätt.

För KTH:s ADK-studenter
Kursspecifik info för labblydelsen finns på kurswebbsidan. Där finns också länkar till testdata och till det Javaprogram 
du ska modifiera så att det går ungefär 10000 gånger snabbare.

## Indata
Indata består av två delar. Den första delen är ordlistan, som består av ett antal ord i bokstavsordning, ett ord per rad.
Denna del avslutas av en rad som bara innehåller ett #-tecken. Den andra delen är ett antal felstavade ord som ska rättstavas,
ett ord per rad. Dom felstavade orden ingår inte i ordlistan. Varje ord i indata består bara av små bokstäver i svenska alfabetet (a-z, å, ä, ö),
inga mellanslag, skiljetecken eller siffror. Inget ord är 40 tecken eller längre. Teckenkodningen är utf-8.

## Utdata
Programmet ska för varje felstavat ord skriva ut en rad bestående av det felstavade ordet följt av det minimala editeringsavståndet
inom parentes följt av en lista med alla ord i ordlistan som har minimalt editeringsavstånd till det felstavade ordet. 
Listan ska vara i bokstavsordning och varje ord i listan ska föregås av mellanslag. 
Ordlistan har högst en halv miljon ord och antalet felstavade ord i indata är högst 100.

### Test Indata
```
maska
masken
masker
maskin
maskot
#
massa
ma
aske
mskt
```

### Test Utdata
```
massa (1) maska
ma (3) maska
aske (2) maska masken masker
mskt (2) maska maskot
```
