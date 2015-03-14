About
-----
cchlib is a collection of APIs to simplify software development in common tasks. This libraries include some proof of concept.

This project contains various common utilities which become handy in daily development of java projects. Under development.

Maven repository
-----
The API is still under migration to GitHub but snapshots are available. There is a public maven repository.

You could specify it directly in your POM or in your settings.xml between the tags <repositories>:

'''xml
  <repositories> 
    <repository>
      <id>cchlib.snapshots</id>
      <name>cchlib Repository (snapshots)</name>
      <url>http://cchlib.googlecode.com/svn/maven-repo/snapshots</url>
      <releases>
        <enabled>false</enabled>
      </releases>
      <snapshots>
        <enabled>true</enabled>
        <!-- updatePolicy>always|daily|interval:XXXminutes|never</updatePolicy -->
      </snapshots>
    </repository>
  </repositories>
'''

News and Website
----------------
Need to be updated

