# Filmova Databaze
---
## Zakladni Prehled
- databaze pro spravu seznamu filmu
- film:
    - reziser, rok vydani, seznam hercu/animatoru, hodnoceni - bodove, slovni
    - dva typy filmu
        - hrane
            - herci jako herci
            - bodove hodnoceni 1 - 5 (hvezdicky)
        - animovane
            - herci jako daberi
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
- CrewMember class
    - info about crewMember
    - links to films where they participate
    - their Director/Actor/Animator status is determined by the film they are participating in
    - they can be a director in one film and animator/actor in another one
    - they even can be director and animator/actor in the same film
- abstract class Review
    - class AnimatedFilmReview extends Review
    - class ActedFilmReview extends Review
- abstract class Film
    - class ActedFilm extends Film
    - class AnimatedFilm extends Film
    - v kazdem filmu je seznam odkazu na jeho herce/animatory/rezisera a hodnoceni do jinych db
    - obsahuje metody zjednodusujici praci s daty
- DatabaseBackend
    - ma arraylisty Filmu a CrewMemberu
    - zprostredkovava veskerou praci s nimi
    - zprostredkovava praci s sql
    - funkce pro ukladani do souboru
    - funkce pro nacitani ze souboru
- SQLHandler
    - provides communication with sql


---

![Struktura programu](struktura.png "Struktura programu")

---
## TODO
- [ ] Uzivatelske rozhrani - @Windar11
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
    - [x] vyuziti sql loading / saving (kinda done)
- [x] Databaze, datove typy, zprostredkovatele, sql - @Otas02CZ
    - [x] review a potomci
    - [x] film a potomci
    - [x] crew a potomci
    - [x] DatabaseBackend
        - [x] Everything except SQL
        - [x] SQL Saving
        - [x] SQL Loading
        - [ ] save to file
        - [ ] load from file
    - [x] SQLHandler
