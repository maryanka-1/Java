1. Create a directory for used file and copy from Src:
    mkdir target
    cp src/resources/it.bmp target

2. Compile the files in directory Target:
    javac src/java/edu/school21/printer/*/*.java -d target

3.Create jar file:
    ! Important. The last line in the manifest.txt must be empty
    c - create new jar-file
    m - file manifest
    f - name new jar file
    -C target - change directory to target
    jar cmf src/manifest.txt target/images-to-chars-printer.jar -C target edu -C target it.bmp

4. Start the program in format with args(--black=0 --white=.):
    java -jar target/images-to-chars-printer.jar --black=0 --white=.
