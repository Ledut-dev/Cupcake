- engelsk i alt kode
- alle features laves i feature branches
- hvor det giver mening laves der unit test på al logik inden der laves pull request.
- Branch merge sker altid med code review (Pull Request)
- Branch navn skal være beskrivende og enten indeholde teamnavn + tekst eller issue nr., f.eks. teama-animated-menu-items eller issue-#16
- Commit beskrivelser skal være sigende.
- UX på dansk

Klasser:
Klassenavne følger Javas standard, dvs. PascalCase (Stort begyndelsesbogstav).

Metoder:
- Er som udgangspunkt ikke static
- skal være korte
- skal have ét ansvar
- alle metoder har forklaring som kommentar, på engelsk
- Metoder og variable følger Javas standard, dvs. camelCase (lille begyndelsesbogstav).


HTML:
navngivning: små bogstaver & bindestreg for sammensatte ord

Database:
- tabeller og kolonner med små bogstaver
- flertal på tabeller
- ental på kolonner


Fejlhåndtering
- input skal valideres
- brugeren skal have fejlbeskeder
- applikationen må ikke crashe




projektstruktur:
app/
├── app.configuration/
├── controllers/
├── dto/
├── entities/
├── exceptions/
├── persistence/
├── services/
└── app.Main.java/


src/main/resources/templates/
src/main/resources/sql/

src/main/resources/public/css/ src/main/resources/public/images/ src/main/resources/public/js/