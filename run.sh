JAR="target/example-dropwizard*.jar"
JAR_FILE=$(ls $JAR)
location=target
CFG="example.yml"
CMD="java -jar $JAR_FILE server $CFG"
$CMD
