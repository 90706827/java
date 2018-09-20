import ch.qos.logback.classic.encoder.PatternLayoutEncoder

String appPath = "D://logs/socket_client/"
String commonPattern = "%d{MM-dd HH:mm:ss.SSS}|%-5level|%logger{36}|%msg%n"

String base = "base"
appender(base,RollingFileAppender){
    file = appPath + "base.log"
    encoder(PatternLayoutEncoder){
        pattern = commonPattern
    }
    rollingPolicy(TimeBasedRollingPolicy){
        fileNamePattern = appPath + "%d{yyyyMMdd,aux}/base_%d{HH}.log"
        //保存48小时
        maxHistory = 48
    }
}

logger(base,INFO,[base],true)

String console = "console"
appender(console,ConsoleAppender){
    encoder(PatternLayoutEncoder){
        pattern = commonPattern
    }
}

root(INFO,[base,console])