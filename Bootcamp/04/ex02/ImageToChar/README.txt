1. Create a directory for used file and copy from Src:
    mkdir target
    cp src/resources/it.bmp target
2. Compile with external libraries the files in directory Target:
    javac -cp lib/JColor-5.5.1.jar:lib/jcommander-1.82.jar src/java/edu/school21/printer/*/*.java -d target
3. Extract libraries in folder 'com':
    cd target && jar fx ../lib/JColor-5.5.1.jar && jar fx ../lib/jcommander-1.82.jar && cd ..
4. Create .jar file:
    jar cmf src/manifest.txt target/images-to-chars-printer.jar -C target edu -C target it.bmp -C target com
5. Start program:
    java -jar target/images-to-chars-printer.jar --black=magenta --white=blue