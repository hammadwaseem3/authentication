# Authentication
Authentication is the process of determining whether someone or something is, in fact, who or what it declares itself to be.

This service is based on the microservice architecture, which responsible to authenticate user on every request incoming to
the system. Ideally this service is use in API gateway level (which authenticate the incoming request and proxy each request
to underneath microservice).

# Jwt Token
This service authenticate user using JWT token which design to light-weight, scalable architecture and stateless authentication.
JSON Web Token (JWT) is an open standard (RFC 7519) that defines a compact and self-contained way for securely transmitting information between parties as a JSON object. This information can be verified and trusted because it is digitally signed. JWTs can be signed using a secret (with the HMAC algorithm) or a public/private key pair using RSA or ECDSA. 

# Rest Api
Following api written for authentication purpose:<br>

1- user/signUp<br>
2- user/signIn<br>
3- user/accessTokenFromRefresh<br>
4- user/list (this api authenticate upcoming request and check that If upcoming request is from Admin that It only allow to proceed)<br>
5- user/authenticateToken (TODO) (a seperate api to authenticate)

*In order to design good microservice architecture we can move the logic of signIn and signUp into "LoginService"*

We can also define roles in JWT token and validate each request that "X user has access to Y resource or not"
Currently in this project we have following two roles

1- ROLE_ADMIN

2- ROLE_CLIENT

and we can use @PreAuthorize Annotation (By Spring Security) to authorize that token is valid or not. After that It process the request (as mention in user/list api)
