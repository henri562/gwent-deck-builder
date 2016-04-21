# gwent-deck-builder
**_A presentation-oriented web application to organize Gwent cards_**

#### I. Project Description

This is a webapp that provides search functionality for players of the popular
card game [Gwent](https://www.youtube.com/watch?v=FIu_jWbZ25o) in Witcher 3.
Users of this app can search through the full collection of Gwent cards and see
their detailed attributes.

#### II. Development Environment Setup

1. Install [NetBeans IDE 8.1](https://netbeans.org/downloads/)
2. Install [Apache Tomcat 7](https://tomcat.apache.org/download-70.cgi)
3. Install [Oracle Database XE 11*g* Release 2](http://www.oracle.com/
technetwork/database/database-technologies/express-edition/downloads/index.html)

#### III. Application Design

The view layer components are built using Mojarra JavaServer Faces. The model
layer business logic is implemented through managed beans, which handles user
requests and retrieves preloaded card data from an Oracle database.

Deployment of the app is done on Apache Tomcat.

The project is still in beta stage, additional features may be added in the near
future.
