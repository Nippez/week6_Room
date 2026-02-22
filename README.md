# week6_Room

Entity hoitaa tietokannan taulun.

DAO kutsuu SQL.

Database Yhdistään Entity:n ja DAO:n roomiksi.

Repository hakee Roomista tietoa.

ViewModel sisältää datan UI:lle.

UI näyttää datat ja tarvittaessa napin painalluksella tai muulla lähettää dataa.

Projektin Rakenne:

Data:
  Local: Room toiminnot.
  Model: Entity ja API.
  Remote: Retrofit ja WeatherAPI.
  
Repository:
  TaskRepository:
  
ViewModel:
  TaskViewmodel:
  WeatherViewmodel
  
UI:
  CalendarScreen:
  DetailDialog:
  HomeScreen:
  WeatherScreen:
  
MainActivity: UI Screen yhdistäminen.

Routes: Navigointi.



Datavirta alkaa käyttäjän toimesta, jolloin UI kutsuu ViewModelia. Tämän jälkeen viewmodel pyytää dataa repositoryltä, joka sitten hakee datan Roomista. Lopulta data tulee viewmodeliin, joka sitten päivittää UI:n uudelleen.
