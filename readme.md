## Azure App Center:

    1. npm install -g appcenter-cli
    2. appcenter login
    3. mvn -DskipTests -P prepare-for-azure-upload package
    4. appcenter test run appium --app "tue1996-gmail.com/test-app-ios" --devices c7b38879 --app-path target/upload/test-classes/apps/ios/HelloWorldiOS.ipa --test-series "master" --locale "en_US" --build-dir target/upload --test-parameter jdk=zulu-11 --test-parameter "test_env=PROPERTIES_FILES=ios.azure.properties"

## AWS Device Farm

    1. Sign in to the Device Farm console at https://console.aws.amazon.com/devicefarm
    2. On the Device Farm navigation panel, choose Mobile Device Testing, then choose Projects.
    3. If you are a new user, choose New project, enter a name for the project, then choose Submit.
    4. If you already have a project, you can choose it to upload your tests to it. 
    5. Open your project, and then choose Create a new run.

## Local

    1. sudo npm install -g appium@next
    2. install xcode, turn on simulator
    3. run Local_iOS_ExampleTest configuration