# Vulnerability Analysis

## A01:2021 - Broken Access Control
### Description
This vulnerability enables an attacker to access data without the needed rights. For instance when a user knows another users' ID, they could try using that to query their data.

### Risk
In this application the risk is fairly low, since intended use is for one person on one machine. However, if the backend is connected to multiple frontends, than users of any frontend could acces the games of any other user on another frontend if they know or correctly guess the ID of those games.

If we where to implement authorisation and authentication, it would be necessary to protect all games by associating them to a specific user, so that each user only has acces to their own games.

### Counter-measures
In the current state there are no counter measures taken for this vulnerability. It would be possible to prevent this problem by:
- Deny all requests by default, except when the resource is public
- Setup strict CORS policy so an attacker is not able to sent requests to the API from another address than the intended server
- Log failed access attempts to create an overview of possible tried attacks, so we know what to protect the most


## A03:2021 – Injection
### Description
This vulnerability enables an attacker to inject their own code or instructions in the application because their input is not validated before execution. Any point in an application where user input is asked could be a potential point to attack using injection. Most common example is SQL Injection, an attack where a malicious query is used as input that is directly queried on the database, thus returning sensitive data.

### Risk
In this application, the risk of injection attacks is relatively low. This application doesn't really contain sensitive data and is simply intended for local use on one machine. Really the only risk is that an attacker who has access to the machine could play or stop any game. The only user inputs that are requested are the game ID and (in the case of a guess attempt) a string of 5 to 7 characters long. 

If this application would use authentication or authorisation to 'login' a user to keep track of and provide access to a specific game, this vulnerablitiy would be a greater risk because executing an injection attack with succes could cause the attacker to access other players' games. If the users' credentials would have been stored in the database without proper hashing, injection could be used to view the password of any user in plain text.

### Counter-measures
- The Game ID inputs are handled by Hibernate ORM, not a single custom SQL or HQL query is used
- The guess attempt will return an exception if the guess doesn't equal the intended guess length 
- When the user inputs anything other than alphabetical letters in the guess request, the API returns BAD REQUEST


## A06:2021 - Vulnerable and Outdated Components
### Description
This vulnerability leaves the security and integrity of an application compromised because of a vulnerability in a component the application depends on. A lot of components are based on other components which in turn carry the same risk. Outdated components often contain security flaws which have been patched in later versions.

### Risk
This is a fairly big risk in this project, as it uses a large number of components that in turn depend on a huge number of other components. A single vulnerability in one of those components can compromise the safety of this whole application.

If this application would use authentication or authorisation, that would probably be implemented using one or more components. A vulnerability in such a component carries a lot more risk because you're directly dealing with user credentials.

### Counter-measures
- This repository has been configured with GitHub Dependabot, which checks for updates and security flaws in dependencies once every week
- Pull requests are automatically created if a new version of a dependency is found, so dependencies can be updated with ease
- All dependencies have been checked for usage, unused dependencies have been deleted