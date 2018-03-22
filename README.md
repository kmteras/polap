# Building
Before building the application `src/main/resources/application.yml` if needed.
```yml
spring:
  jpa.hibernate.ddl-auto: update
  datasource:
    url: jdbc:mysql://localhost:3306/<DATABASE NAME>?useSSL=false
    username: <USERNAME>
    password: <PASSWORD>
  profiles:
      active: google

security:
  oauth2:
    client:
      clientId: <GOOGLE CLIENT ID>
      clientSecret: <GOOGLE CLIENT SECRET>
      accessTokenUri: https://www.googleapis.com/oauth2/v4/token
      userAuthorizationUri: https://accounts.google.com/o/oauth2/v2/auth
      clientAuthenticationScheme: form
      scope:
        - openid
        - email
        - profile
    resource:
      userInfoUri: https://www.googleapis.com/oauth2/v3/userinfo
      preferTokenInfo: true

server:
  compression:
    enabled: true
    mime-types: text/html,text/css,application/javascript,application/json
```
