JAR="target/example-dropwizard*.jar"
JAR_FILE=$(ls $JAR)
CFG="target/config/local.yml"
CMD="java -jar $JAR_FILE server $CFG"
exec $CMD
