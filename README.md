# Online exam system usage guide
 
- `Create time`: 2015-04-28
- `Author`: Justin Liao
- `Github`:  https://github.com/JustinLiao1314/online_exam_system
- `Email`: charleslxh@icloud.com

# Project Structure

- `.jhipster`: Database model configurations
- `.settings`: System configurations
- `docs`: System guide and other designed pictures
- `src`: main folder
  - `main`: run folder
    - `java`: server
      - `com/online/exam`:
        - `aop`: aop folder
        - `async`: saync folder
        - `config`: server configurations
        - `domain`: util and entities
        - `repository`: repositories
        - `security`: about security
        - `service`: servicese
        - `web`: controllers
    - `resource`: configurations
      - `config`: liquibase configurations
      - `i18n`: internationalization properties
      - `mails`: mail templates
      - `templates`: error templates
    - `webapp`: client
      - `assets`: fonts, image and styles
      - `i18n`: internationalization configurations
      - `scripts`: angularJS
      - `swagger-ui`: common things
  - `test`: test model
    - `gatling`: gatling folders
    - `java`: server folders
    - `javascript`: client folders 
    - `resource`: configurations
- `target`: classes after compiled

About angular structure, see also Mastering Web Application Development with AngularJS(Chapter 2: Building and Testing - Origanizing files and folders) and https://docs.google.com/document/d/1XXMvReO8-Awi1EZXAXS4PzDzdNvV6pGcuaF4Q9821Es/pub?utm_source=javascriptweekly&utm_medium=email

# Third Party Dependencies

To work on this project, you have to know these, at least knowing what they are for and what they can do. A lot are not listed here, please refer to `package.json` and `bower.json`, those are less important, you can get into them when you're going to use them.

- platform
   - http://nodejs.org/
   - http://www.java.com/
   - http://maven.apache.org/
   - Chrome/Firefox/Safari/IE10+
- language
    - http://coffeescript.org/
    - http://jade-lang.com/
    - http://lesscss.org/
    - http://www.java.com/
- storage
    - http://mongodb.org/
    - http://dev.mysql.com/
    - http://www.w3.org/TR/IndexedDB/
- backend
    - spring
        - http://spring.io/
    - hibernate
        - http://hibernate.org/
    - java8
        - https://jdk8.java.net/
    - testing
        - https://github.com/domenic/chai-as-promised/
        - https://github.com/visionmedia/mocha
- frontend
    - angular
        - http://angularjs.org/
        - https://github.com/angular-ui/ui-router
        - https://github.com/mgonto/restangular
        - http://angular-ui.github.io/bootstrap/
        - https://github.com/cmaurer/angularjs-nvd3-directives
    - bootstrap
        - http://getbootstrap.com/
        - http://bootswatch.com/
        - http://fortawesome.github.io/
    - html5:
        - http://www.w3.org/html/logo/
    - jquery
        - http://jquery.com/
    - testing
        - https://github.com/pivotal/jasmine
        - http://karma-runner.github.io/
        - https://github.com/angular/protractor
- utils
    - https://github.com/nomiddlename/log4js-node
    - https://github.com/oliversalzburg/i18n-node-angular
- testing
    - https://github.com/alex-seville/blanket
- tools
    - http://www.coffeelint.org/
    - http://bower.io/
    - http://gruntjs.com/
- assessing
    - http://gulpjs.com/, gruntjs' alternative
    - http://mgcrea.github.io/angular-strap/, angular-ui-bootstrap's alternative
    - https://github.com/indutny/node-spdy, SPDY server on Node.js

# Develop Environment Setup

- **Windows Environment Setup**
```shell
# Install java (JDK & JRE) environment
# Install maven more than 3.2.0
# Install tomcat more than 7.0
# Install node.js environment
# Install Ruby environment
# Install Mysql environment
```
- **Linux Environment Setup**
```shell
# Install java (JDK & JRE) environment
Download JDK: http://www.oracle.com/technetwork/java/javase/downloads/
Configure JDK: 
  vi /etc/profile
  export JAVA_HOME="JDK path"
  export JAVA_BIN="JDK bin path"
  export PATH=$PATH:$JAVA_HOME/bin
  export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
  export JAVA_HOME JAVA_BIN PATH CLASSPATH
Restart you Computer
 
# Install maven more than 3.2.0
wget http://apache.fayea.com/apache-mirror/maven/maven-3/3.2.0/binaries/apache-maven-3.2.0-bin.tar.gz
tar -xzvf apache-maven-3.2.0-bin.tar.gz
mv apache-maven-3.2.0-bin /usr/local/maven-3.2.0

# Install tomcat more than 7.0
Download Tomcat: http://tomcat.apache.org/download-70.cgi
tar -zxvf apache-tomcat-7.0.32.tar.gz
cp -r /home/zdw/software/apache-tomcat-7.0.32 /opt/tomcat
cd /opt/tomcat/bin
./startup.sh

# Install node.js environment
apt-get install aptitude curl wget git build-essential libssl-dev gdebi graphicsmagick libjpeg-dev libcairo2-dev libpango1.0-dev
curl https://raw.githubusercontent.com/creationix/nvm/v0.6.1/install.sh | sh
. ~/.profile
nvm install 0.10.33
nvm alias default 0.10.33

# Install Ruby environment
sudo apt-get install ruby
em sources --remove http://rubygems.org/
gem sources -a https://ruby.taobao.org/
gem sources -l
*** CURRENT SOURCES ***
https://ruby.taobao.org

# Install Mysql environment
netstat -tag |grep mysql
sudo apt-get install mysql-server-5.5

# Install Grunt and bower
npm install -g grunt-cli
npm install -g bower
```

# Run 
```shell
# mvn spring-boot:run
The server will on 
  "http://localhost:8080"
  "http://127.0.0.1:8080"

# grunt test
 
# grunt serve
The serve will on
  "http://localhost:3000"
  "http://127..0.0.1:3000"

```
