# Readme: URL shortener
URL shortener

> app is deployed on [heroku](https://app-short-url.herokuapp.com/help)

**Assignment**: Write an HTTP service which is used to shorten URLs, with following functionalities:
- Registration of a web address (API)
- Redirect of clients call according to the shortened URL-u
- Statistic of API calls (API)

*full assignment:* [Infobip assignment - Java URL shortener eng.pdf](https://github.com/jakubhricik/URL-shortener/blob/master/Infobip%20assignment%20-%20Java%20URL%20shortener%20eng.pdf)


## Dependencies
There are a number of third-party dependencies used in the project. Browse the Maven pom.xml file for details of libraries and versions used.

## Building and running the project
You will need:

*	Java JDK 17
*	Maven 3.8.1 or higher
*	Git
* Docker



Clone project and navigate into root folder
```bash
git clone https://github.com/jakubhricik/URL-shortener.git
cd URL-shortener
```

Run docker and create docker image
```bash
docker build -t url-shortener .
```

Run docker image, publish ports:
```bash
docker run --publish 8080:8080 --name url-shortener url-shortener 
```

### Browser URL
Open your browser at the following URL: `localhost:8080/swagger-ui/` (giving REST interface details)      
For help navigate on `localhost:8080/help` page   
