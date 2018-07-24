import ch.qos.logback.classic.encoder.PatternLayoutEncoder

String userName = System.getProperty("user.home")
String logPath = "${userName}/logs/shiro/"
String commonPattern = "%d{HH:mm:ss.SSS}|[%thread]|%-5level|%logger{36}-%msg%n"

String all = "all"
appender(all, RollingFileAppender) {
    file = logPath + "all.log"
    encoder(PatternLayoutEncoder) {
        pattern = commonPattern
    }
    RollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = logPath + "%d{yyyyMMdd,aux}/all_%d{dd}"
        maxHistory = 30
    }
}


String console = "console"
appender(console,ConsoleAppender){
    encoder(PatternLayoutEncoder){
        pattern = commonPattern
    }
}

logger("error",ERROR,[all],false)

root(INFO,[all,console])