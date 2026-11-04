Skal admin være en slags user, eller et særligt login som kun kan administrere?
- Admin skal kun kunne gøre admin ting.
- Admins skal have hver deres konto, så man kan se, hvem der gjorde hvad.
- Medarbejder ting som løn og skema indgår ikke i systemet, så databasen behøver ikke medarbejdere som ikke er admins.

ERD
- Toppings og bottoms har unikke navne, som er primærnøglen.
- En ordre har "orderlines" med en top og en bund.

Overvejelser ift. US8 (... hvis kunden aldrig har betalt)
- En medarbejder skal acceptere en ordre før betalingen gennemføres.

Designovervejelser
- Ikke for meget lyserød, noget med nogle striber.

- Scrum Poker
- US1: Udelukkende bestilling og betaling, indebærer ikke om bestillingen bliver godkendt.
- US2: Der er nogle skjulte ting som at tjekke brugernavn mod databasen og validere password.
- US3: Bred enighed.
- US4: Hvor svært er det at vise email på hver side?
- US5: Hvordan tjekker vi om brugeren er admin? Skal admin kunne godkende ordrer her?
- US6: Evt. søgefunktion? Hvordan skal oversigten organiseres? Måske flere oversigter?
- US7: Måske noget med fejlhåndtering, men meget enige.
- US8: Skal admin kun kunne slette pending ordrer, eller også gamle tidligere godkendte ordrer?

Navigationsdiagram
- Man skal kunne lave en kurv uden at logge ind, men man skal logge ind for at bestille.
- Forside har information om butikken.
- Forside skal have links til: login/opret bruger, bestil, se kurv.
- Login/opret sigen starter med en login form og et link til opret formen.
- Bestil siden har en form, hvor man vælger top, bund og antal og tilføjer til kurv.
- Efter login som admin routes til en "admin-forside" med links til adminfunktionerne.
- Hvis en admin forsøger at åbne den normale forside eller menu eller kurv, bliver den rerouted til admin-forsiden.
- To admin ordre-oversigter? Hvor skal admin man kunne bekræfte eller godkende ordrer?

Først ville vi bruge email som primær nøgle til users, men brugeren kunne måske ændre email i fremtiden, så vi skiftede 
til en kunstig nøgle.