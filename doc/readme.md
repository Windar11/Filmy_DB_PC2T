# Filmova Databaze
---
## Zakladni Prehled
- databaze pro spravu seznamu filmu
- film:
    - reziser, rok vydani, seznam hercu, hodnoceni - bodove, slovni
    - dva typy filmu
        - hrane
            - herci jako herci
            - bodove hodnoceni 1 - 5 (hvezdicky)
        - animovane
            - herci jako animatori
            - bodove hodnoceni jako 1 - 10 (kladne cislo)
            - navic doporuceny vek divaka

## Funkce
- pridani filmu
    - uzivatel vyber druh filmu
    - zada nazev, rezisera, rok vydani, seznam hercu, animatoru
    - pokud animovany - zada navic i doporuceny vek divaka
- upraveni filmu
    - uzivatel film vybere dle nazvu
    - muze upravit nazev, rezisera, rok vydani, seznam hercu/ animatoru
    - pokud animovany - muze menit i doporuceny vek divaka
- smazani filmu
    - vyber dle jmena
- pridani hodnoceni
    - dle jmena
    - bodove hodnoceni dle druhu filmu, slovni hodnoceni volitelne
- vypis filmu
    - zobrazeni vsech filmu v seznamu
    - nazev, reziser, rok vydani, seznam hercu / animatoru, hodnoceni divaku - razeno sestupne dle hodnoceni
- vypis hercu / animatoru podilejicich se na vice nez jednom filmu
    - uzivateli zobrazen seznam hercu / animatoru, kteri se podileli na vice filmech
    - vybere jednoho
    - zobrazi se mu seznam filmu na kterem se podileli
- vypis vsech filmu s konkretnim hercem / animatorem
    - uzivatel zada jmeno
    - zobrazi se seznam filmu na kterem se podileli
- ulozeni informace o vybranem filmu (vyber dle nazvu) do souboru
- nacteni vsech informaci o danem filmu ze souboru (jeden soubor = jeden film)
- ukonceni - ulozeni do sql
- spusteni - nacteni ze sql


## Struktura
- abstract class Crew
    - class Actor extends Crew
    - class Animator extends Crew
    - class Director extends Crew
- abstract class Review
    - class AnimatedFilmReview extends Review
    - class ActedFilmReview extends Review
- abstract class Film
    - class ActedFilm extends Film
    - class AnimatedFilm extends Film
- DB classes
    - CrewDB
    - ReviewDB
    - FilmDB
- SQLDataProvider interface
- SQLDataHandler class
---

![Struktura programu](struktura.png "Struktura programu")

---
## TODO
- [ ] Uzivatelske rozhrani
    - [ ] pridani filmu
    - [ ] uprava filmu
    - [ ] smazani filmu
    - [ ] pridani hodnoceni
    - [ ] vypis filmu
    - [ ] vypis hercu s vice filmy
    - [ ] vypis filmu s konkretnim hercem
    - [ ] ulozeni informace o jednom filmu do souboru
    - [ ] nacteni informace o jednom filmu ze souboru
    - [ ] ui polish
    - [ ] vyuziti sql loading / saving
- [ ] Databaze, datove typy, zprostredkovatele
    - [ ] review a potomci
    - [ ] film a potomci
    - [ ] crew a potomci
    - [ ] CrewDB
    - [ ] FilmDB
    - [ ] ReviewDB
    - [ ] SQLDataProvider interface
    - [ ] SQLHandler
