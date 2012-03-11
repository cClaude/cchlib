cchlib-apps-editresourcebundle

---------------------------------------------------------------------
4.1.7
  Java 1.7 required
  Fix some javadoc issues
---------------------------------------------------------------------
4.1.6
  Add support of some Java 1.7 features.
  Generated target code now is Java7
  Use some new class from core

  Ask before saving
  Save when window is closed

  Don't save if no modifications
  Fix some encoding problems using FormattedProperties
  New Main class (migrate under package com.googlecode.cchlib)
  Add I18n
---------------------------------------------------------------------
4.1.2
  Moving 2 maven (remove ant build number)
---------------------------------------------------------------------
04.00 Maven version
---------------------------------------------------------------------
03a.80
  Migrate to maven
---------------------------------------------------------------------
03a.20
  Move release note to package.
---------------------------------------------------------------------

---------------------------------------------------------------------
Known problems:
---------------------------------------------------------------------
To add:
  Java web start support
---------------------------------------------------------------------
To improve:
---------------------------------------------------------------------
Future extensions:
---------------------------------------------------------------------
---------------------------------------------------------------------
---------------------------------------------------------------------
          <sign>
            <keystore>${project.build.directory}/keyStore</keystore> <!-- path or URI (if empty, the default keystore ".keystore"-file in the user-homedir is used) -->
            <keypass>password</keypass>  <!-- we need to override passwords easily from the command line. ${keypass} -->
            <storepass>password</storepass> <!-- ${storepass} -->
            <alias>webstart</alias> <!-- alias of the key to  use -->

            <!-- the following key-settings are only used if the keystore and key has to be generated at build-time -->
            <storetype>jks</storetype>
            <validity>365</validity>
            <dnameCn>Firstname Lastname</dnameCn>
            <dnameOu>OrganisationalUnit</dnameOu>
            <dnameO>Organisation</dnameO>
            <dnameL>Location</dnameL>
            <dnameSt>State</dnameSt>
            <dnameC>CountryCode</dnameC>

            <!-- KEYSTORE MANAGEMENT -->
            <keystoreConfig>
              <delete>true</delete> <!-- delete the keystore at build time -->
              <gen>true</gen>       <!-- generate keystore and key at build time -->
            </keystoreConfig>

            <verify>false</verify> <!-- verify the signature after signing -->
          </sign>


      <plugin>
        <groupId>org.codehaus.mojo.webstart</groupId>
        <artifactId>webstart-maven-plugin</artifactId>
        <version>1.0-beta-1</version>
        <executions>
          <execution>
            <phase>process-resources</phase>
            <goals>
              <goal>jnlp</goal>
            </goals>
          </execution>
        </executions>

        <configuration>
          <excludeTransitive>true</excludeTransitive>
          <jnlp>
            <outputFile>EditRessourceBundle-last-release.jnlp</outputFile>
            <mainClass>cx.ath.choisnet.tools.i18n.CompareRessourceBundleFrame</mainClass>
          </jnlp>
          <pack200>true</pack200>
<!--
           <sign>
            <keystore>keystore</keystore>
            <alias>test</alias>
            <keypass>m2m2m2</keypass>
            <storepass>m2m2m2</storepass>
          </sign>
-->
        <!--
  <alias>${alias}</alias>
<keypass>${keypass}</keypass>
<keystore>${keystore}</keystore>
<storepass>${storepass}</storepass>

-->
<!--
          <outputDirectoryName>webstart</outputDirectoryName>
-->
          <!--
          Set to true to exclude all transitive dependencies. Default is false.
          -->
<!--
          <excludeTransitive>false</excludeTransitive>
-->
<!--
          <jnlpFiles>
            <jnlpFile>
              <templateFilename>jnlpTemplate.vm</templateFilename>
              <outputFilename>launch1.jnlp</outputFilename> <!- - when there's only one jnlpFile, can be optioanl and defaults to launch.jnlp - ->
 ???????????  <inputTemplate>jnlp/template.vm</inputTemplate- ->
???????????<outputFile>test.jnlp</outputFile>
???????????<mainClass>org.codehaus.mojo.webstart.test.Test</mainClass>
              <jarResources>
                <jarResource>
                  <groupId>org.codehaus.mojo.webstart.it003</groupId>
                  <artifactId>hello-world</artifactId>
                  <version>1.0</version>
                  <mainClass>cx.ath.choisnet.tools.i18n.CompareRessourceBundleFrame</mainClass>
                </jarResource>
              </jarResources>
            </jnlpFile>
          </jnlpFiles>
-->

          <unsign>true</unsign> <!-- unsign already signed packages and sign them with own key -->

          <verifyjar>false</verifyjar>
        </configuration>
      </plugin>
