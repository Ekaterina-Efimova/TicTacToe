
**Project: Tic-Tac-Toe**

- Describe the PostgreSQL database connection in application.properties.
- Provide user support across all layers.
- Create a SignUpRequest model that contains a login and password.
- Create an authorization service that uses UserService:
  - a registration method that takes a SignUpRequest and returns a registration success status;
  - an authorization method that takes the login and password in the header as base64(login:password) and returns the user's UUID.
- Create an authorization controller with the following endpoints:
  - for user registration,
  - for user authorization.
- Create a class AuthFilter extending GenericFilterBean and implement the doFilter method:
  - Validate the login and password.
  - If the validation is successful, proceed with the request.
  - If the validation fails, add the 401 status code to the response and do not proceed with the request.
- Create a Spring Configuration class where:
  - You define a Bean for obtaining SecurityFilterChain.
  - Allow access without authorization to the registration and authorization endpoints.
  - All other endpoints require authorization.
  - Use AuthFilter as a filter.
- Improve the game-ending logic using these states.
- Enhance the endpoint for updating the current game to account for playing against another user or against the computer.
