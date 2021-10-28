## Data models

### Places response

The places response is a json object with the following structure:

```json
{
    "place": "array",
    "total": "number"
}
```

### Place

A place is a json object with the following structure:

```json
{
    "alias": "string",
    "name": "string",
    "longitude": "number",
    "latitude": "number",
    "description": "string",
    "icon": "string",
    "website": "string"
}
```

## Package by Layer

We have the dataBase, network , domain , di , and util layer

* database contains Entities, dao and dataBase 
* network  contains data models ***which we needs for working with retrofit***,
  repository and PlaceService Interface
* domain contains all classes and functions that are the main core of application
* di contains Hilt providers
* util contains the files which help us across the application


## Perform MVVM Architecture

I Obey the MVVM Architecture in order to architect the application code


## Dependency Injection

I use Dagger Hilt in order to use dependency injection across the application codes

## Next Steps 

* convert MainActivityView to 3 Fragment 
* get place information and show more detail such as telephone address and in place cardview
* convert adapter to paging adapter (when we have more than 25 places)



