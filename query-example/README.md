# Query Example

***

## Description
This example compare a REST Endpoint to a GraphQL endpoint and goes through some 
GraphQL query options. We will look at:

- Query
- Source fields
- Batching (N+1 problem)
- Multiple requests
- Concurrent requests
- Errors and partial results
- Mutations
- 

### Query

Getting all people:

```graphql
{
  people{
    names
    surname
  }
}
```

Getting one person:

```graphql
{
  person(id: 1){
    names
    surname
  }
}
```

### Source fields

Add score field to person:

```graphql
{
  person(id: 1){
    names
    surname
    scores {
      name
      value
    }
  }
}
```

### Collection with source field

```graphql
{
  people{
    names
    surname
    scores {
      name
      value
    }
  }
}
```

### Batching

```graphql
{
  people{
    names
    surname
    scores {
      name
      value
    }
  }
}
```

### Multiple requests

Multiple of the same type:

```graphql
{
  person1:person(id: 1){
    names
    surname
    scores {
      name
      value
    }
    
  }
  person2:person(id: 2){
    names
    surname
  }
}
```

Add exchage rate and weather:

```graphql
{
  person(id: 1){
    names
    surname
    scores {
      name
      value
    }
    exchangeRate(against:GBP){
      rate
    }
  }
  weather(city:"London"){
    currentTemp
    description
  }
}
```

### Concurrent requests

Person and weather at the same time:

```graphql
{
  person(id: 1){
    names
    surname
    scores {
      name
      value
    }
    exchangeRate(against:GBP){
      rate
    }
  }
  weather(city:"London"){
    currentTemp
    description
  }
}
```

Add another destination:

```graphql
{
  person(id: 1){
    names
    surname
    scores {
      name
      value
    }
    uk:exchangeRate(against:GBP){
      rate
    }
    us:exchangeRate(against:USD){
      rate
    }
  }
  london:weather(city:"London"){
    currentTemp
    description
  }
  ny:weather(city:"New York"){
    currentTemp
    description
  }
}
```

### Errors and Partial Response

Simulate exchange rate system failure:

```graphql
{
  person(id: 1){
    names
    surname
    exchangeRate(against:GBP){
      rate
    }
  }
}
```

### Mutations

Create a person with only a name:

```graphql
mutation CreatePerson{
  updateOrCreatePerson(person :
    {
      names: "Phillip"
    }
  ){
    id
    names
    surname
    profilePictures
    website
  }
}
```

Update that person with more details:

```graphql
mutation UpdatePerson{
  updateOrCreatePerson(person :
    {
      id: 201,
      names:"Phillip",
      surname: "Kruger",
      profilePictures: [
        "https://pbs.twimg.com/profile_images/1311017429242437636/CbUoiAeP_400x400.jpg"
      ],
      website: "http://www.phillip-kruger.com"
    }){
    id
    names
    surname
    profilePictures
    website
  }
}
```

### Subscriptions

```graphql
subscription PersonEvent {
  personDatabaseEvent {
    event
    person{
      names
    }
  }
}
```

Now do an add and update again

### Introspection

```graphql
{
  __schema{
    types{
      name
      kind
    }
  }
}
```