# Android Developer Assignment - Svea Apps

In this repository, you will find a gist for what we believe, over time, could turn
into a massive activity. Your task is to break this view into logical, testable
chunks.

You can use a project structure of your choosing, including 3rd party dependencies and other
design choices. You may also choose the expand it or modify it's layout.

## Specs

The activity in this example lists places fetched from a REST API and presents it to
the user, once a button is clicked.

### Think about these things when you design your solution

* Testability
* Dependencies
* Separation of responsibilities
* Readability
* Maintainability


You will receive feedback on your work regardless of where you are in the
recruitment process.

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



