# Matukain
 Szoftver projekt laboratórium - Fungorium

## Leírás
A Fungorium egy stratégiai szimulációs játék, amelyben gombák és rovarok versengenek egy dinamikusan változó környezetben. A bolygó felszínét tektonok alkotják, amelyeken a gombák terjednek, és a rovarok táplálékot keresnek.

## Főbb funkciók
- [ ] Dinamikus tektonikus rendszer, amely törések és mozgások révén változik.
- [ ] Gombák terjedése gombafonalak és spórák segítségével.
- [ ] Rovarok mozgása, amely a gombafonalakhoz kötött.
- [ ] Stratégiai döntések: gombászok a gombák terjedését irányítják, míg rovarászok a rovarok mozgását befolyásolják.
- [ ] Többféle spóra, amelyek különböző hatással vannak a rovarokra.

## Telepítés és futtatás

### Szükséges követelmények
- C++ fordító (pl. GCC vagy MSVC)
- CMake (ha szükséges)
- Egyéb szükséges könyvtárak (pl. SDL, SFML, stb.)

### Fordítás és futtatás
```sh
# Ha CMake-et használunk
mkdir build && cd build
cmake ..
make
./fungorium

# Ha sima g++ fordítást használunk
g++ -o fungorium main.cpp
./fungorium
```

## Használati példák
Írjatok ide néhány példát a program használatára, például:
```sh
./fungorium --help
./fungorium simulation_config.json
```

## Fejlesztők
- **Tóth Tódor** - *OWEKB9*
- **Blasek Balázs** - *CW4D3S*
- **Csordás Bence** - *PU8FXP*
- **Jónás Gergely Péter** - *UEYXGL*
- **Monostori Dóra Marianna** - *PNNV8Z*

## Játékmenet
- A gombászok a gombák terjedését és spóraszórását irányítják.
- A rovarászok a rovarok mozgását és a fonalak elvágását befolyásolják.
- A gombák és rovarok interakciója meghatározza a játék kimenetelét.

## Győzelmi feltételek
- A gombászok közül az nyer, akinek a legtöbb gombatestje fejlődött ki a játék végére.
- A rovarászok közül az nyer, aki a legtöbb tápanyagot gyűjtötte be a spórák elfogyasztásával.

## Hozzájárulás
Konzulens (Laborvezető):  **Salvi Péter** 

## Licenc
(Egy rövid megjegyzés arról, hogy milyen licenc alatt áll a projekt, pl. MIT, GPL, stb.)
