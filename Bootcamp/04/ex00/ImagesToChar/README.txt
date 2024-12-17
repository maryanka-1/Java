1. Create a directory for used file and copy from Src:
    mkdir target
    mkdir target/source
    cp src/it.bmp target/source
2. Compile the files in directory Target:
    javac src/java/edu/school21/printer/*/*.java -d ./target
3. Start the program in format with args(--black=0 --white=. --file=target/source/it.bmp):
    java -cp target edu/school21/printer/app/Program --black=0 --white=. --file=target/source/it.bmp
