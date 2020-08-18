# tclone

Documentation available by swagger ui at:
    http://localhost:8080/swagger-ui.html
To run from command line clone repository and execute:
    mvn spring-boot:run
from that directory

# Code Challenge

## Description

Build a simple social networking application, similar to Twitter, and
expose it through a web API. The application should support the scenarios
below.

## Scenarios

### Posting

A user should be able to post a 140 character message.

### Wall

A user should be able to see a list of the messages they've posted, in reverse
chronological order.

### Following

A user should be able to follow another user. Following doesn't have to be
reciprocal: Alice can follow Bob without Bob having to follow Alice.

### Timeline

A user should be able to see a list of the messages posted by all the people
they follow, in reverse chronological order.

## Details

- use JAVA
- provide some documentation for the API, so that we know how to use it!
- don't care about registering users: a user is created as soon as they post
  their first message
- don't care about user authentication
- don't care about frontend, only backend
- don't care about storage: storing everything in memory is fine

## Submitting

Place your code on https://github.com.

## Tips

Please write your solution provided an easy option to build it and run it on macOS/windows.
