JAR="target/example-dropwizard*.jar"
JAR_FILE=$(ls $JAR)
CFG="target/config/local.yml"
MIGRATION="java -jar $JAR_FILE db migrate $CFG"
CMD="java -jar $JAR_FILE server $CFG"
$MIGRATION
$CMD
