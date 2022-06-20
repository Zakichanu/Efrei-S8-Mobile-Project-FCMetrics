# FCMetrics

Project to validate my mobile development course

## Desing constraints

- &check; Ergnomic interface (use fragments, menus like toolbars and navigation drawers, ...), adapt the interface to landscape, portrait, french and english
- &check; Locate the matches (latitude, longitude and geocoding - transform these into a street name)
- &cross; Take pictures of the match and save them locally
- _ Save statistics locally for 5 previous matches (in a sqlite database) **!!NO NEED!!**
- &check; Save full statistics on a real external database
- _ be able to display information about any previous match (read the information from the local database or the distant one) -> didn't have much time

## Additional functions

We made an [API](https://github.com/Zakichanu/Efrei-S8-Mobile-Project-FCMetrics-Model) specifically for this project, where this one will just communicate with the [mongo atlas](https://www.mongodb.com/fr-fr/pricing) database. It is pretty basic because of the amount of time really short (5 days). For know, at the moment we got just requests to insert and get a user.



> Made by Zak and Yanis
