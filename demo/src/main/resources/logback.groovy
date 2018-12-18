import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.LevelFilter
import ch.qos.logback.core.spi.FilterReply

import java.nio.charset.Charset

String user_home = System.getProperty("user.home")
String logPath = "${user_home}/demo/"
String commonPattern = "%d{HHmmss.SSS}|%-5level|%thread{3}|%logger{36}|%msg%n"

// 基础日志文件配置
String other_all = "other_all";
appender(other_all, RollingFileAppender) {
    file = logPath + "other_all.log"
    encoder(PatternLayoutEncoder) {
        pattern = commonPattern
        charset = Charset.forName("UTF-8")
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = logPath + "%d{yyyyMMdd, aux}/other_all_%d{HH}.log"
        //删除几小时之前数据
        maxHistory = 7 * 24
    }
}

// 基础日志文件配置
String other_err = "other_err";
appender(other_err, RollingFileAppender) {
    file = logPath + "other_err.log"
    filter(LevelFilter) {
        level = ERROR
        onMatch = FilterReply.ACCEPT
        onMismatch = FilterReply.DENY
    }
    encoder(PatternLayoutEncoder) {
        pattern = commonPattern
        charset = Charset.forName("UTF-8")
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = logPath + "%d{yyyyMMdd}-%i.log"
        //删除几天日志
        maxHistory = 35
        timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
            MaxFileSize = "400MB"
        }
    }
}


String console = "console"
appender(console, ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%-4relative [%thread][%-5level][%logger{36}]: %msg%n"
        charset = Charset.forName("UTF-8")
    }
}

//参数分别为类名，日志级别，日志文件，是否让root接收默认为true
logger(other_all, INFO, [other_all], true)
logger(other_err, INFO, [other_err], true)
root(INFO, [console])