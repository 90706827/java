## 安装elasticsearch

https://www.elastic.co/downloads/elasticsearch

```bash
#配置三台机器的hosts文件内容如下：
vim /etc/hosts
192.168.0.121 master-node
192.168.0.122 data-node1
192.168.0.123 data-node2
```

```bash
rpm -ivh elasticsearch-6.5.2.rpm
ll /etc/sysconfig/elasticsearch

ll /etc/elasticsearch
-rw-rw----. 1 root elasticsearch   207 12月  9 23:14 elasticsearch.keystore
-rw-rw----. 1 root elasticsearch  2869 11月 30 08:02 elasticsearch.yml
-rw-rw----. 1 root elasticsearch  3266 11月 30 08:02 jvm.options
-rw-rw----. 1 root elasticsearch 12423 11月 30 08:02 log4j2.properties
-rw-rw----. 1 root elasticsearch   473 11月 30 08:02 role_mapping.yml
-rw-rw----. 1 root elasticsearch   197 11月 30 08:02 roles.yml
-rw-rw----. 1 root elasticsearch     0 11月 30 08:02 users
-rw-rw----. 1 root elasticsearch     0 11月 30 08:02 users_roles
```

elasticsearch.yml 文件用于配置集群节点等相关信息的

elasticsearch 文件则是配置服务本身相关的配置，例如某个配置文件的路径以及java的一些路径配置什么的。

```bash
vim /etc/elasticsearch/elasticsearch.yml 
##主节点配置如下内容
cluster.name: master-node  # 集群中的名称
node.name: master  # 该节点名称
network.host: 0.0.0.0  # 监听全部ip，在实际环境中应设置为一个安全的ip
http.port: 9200  # es服务的端口号
discovery.zen.ping.unicast.hosts: ["192.168.0.121", "192.168.77.122", "192.168.77.123"] # 配置自动发现

##支节点配置如下内容，
cluster.name: master-node  # 集群中的名称
node.name: data-node1
node.master: false
node.data: true
network.host: 0.0.0.0  # 监听全部ip，在实际环境中应设置为一个安全的ip
http.port: 9200  # es服务的端口号
discovery.zen.ping.unicast.hosts: ["192.168.0.121", "192.168.0.122", "192.168.0.123"]  # 配置自动发现
```

启动

```bash
systemctl start elasticsearch.service
ps aux |grep elasticsearch
```

开放端口

firewall-cmd --zone=public --add-port=9200/tcp --permanent
firewall-cmd --reload
firewall-cmd --zone=public --list-ports



访问集群的状态信息

```bash
http://192.168.0.121:9200/_cluster/health?pretty
http://192.168.0.122:9200/_cluster/health?pretty
http://192.168.0.123:9200/_cluster/health?pretty
```



## 安装kibana

https://www.elastic.co/downloads/kibana

只有主节点需要安装kibana 

```bash
rpm -ivh kibana-6.5.2-x86_64.rpm
vim /etc/kibana/kibana.yml 
server.port: 5601  # 配置kibana的端口
server.host: 192.168.77.128  # 配置监听ip
elasticsearch.url: "http://192.168.0.121:9200"  # 配置es服务器的ip，如果是集群则配置该集群中主节点的ip
logging.dest: /var/log/kibana.log  # 配置kibana的日志文件路径，不然默认是messages里记录日志

touch /var/log/kibana.log; chmod 777 /var/log/kibana.log


systemctl start kibana
ps aux |grep kibana
netstat -lntp |grep 5601
```

开放端口

firewall-cmd --zone=public --add-port=5601/tcp --permanent
firewall-cmd --reload
firewall-cmd --zone=public --list-ports



http://192.168.0.121:5601/



## 安装logstash

目前logstash不支持JDK1.9

https://www.elastic.co/downloads/logstash

```bash
#第二台服务器
rpm -ivh rpm -ivh logstash-6.5.2.rpm

vim /etc/logstash/conf.d/syslog.conf 

input {  # 定义日志源
  syslog {
    type => "system-syslog"  # 定义类型
    port => 10514    # 定义监听端口
  }
}
output {  # 定义日志输出
  stdout {
    codec => rubydebug  # 将日志输出到当前的终端上显示
  }
}


cd /usr/share/logstash/bin
 ./logstash --path.settings /etc/logstash/ -f /etc/logstash/conf.d/syslog.conf --config.test_and_exit
 Sending Logstash's logs to /var/log/logstash which is now configured via log4j2.properties
Configuration OK  # 为ok则代表配置文件没有问题 虚拟机可以调整处理器数量 1个会报错
##配置kibana服务器的ip以及配置的监听端口：
vim /etc/rsyslog.conf
*.* @@192.168.0.123:10514

systemctl restart rsyslog

cd /usr/share/logstash/bin
./logstash --path.settings /etc/logstash/ -f /etc/logstash/conf.d/syslog.conf
Sending Logstash's logs to /var/log/logstash which is now configured via log4j2.properties
# 这时终端会停留在这里，因为我们在配置文件中定义的是将信息输出到当前终端
netstat -lntp |grep 10514

cd /usr/share/logstash/bin
./logstash --path.settings /etc/logstash/ -f /etc/logstash/conf.d/syslog.conf

配置logstash
以上只是测试的配置，这一步我们需要重新改一下配置文件，让收集的日志信息输出到es服务器中，而不是当前终端：

vim /etc/logstash/conf.d/syslog.conf 

input {
  syslog {
    type => "system-syslog"
    port => 10514
  }
}
output {
  elasticsearch {
    hosts => ["192.168.77.128:9200"]  # 定义es服务器的ip
    index => "system-syslog-%{+YYYY.MM}" # 定义索引
  }
}

cd /usr/share/logstash/bin
./logstash --path.settings /etc/logstash/ -f /etc/logstash/conf.d/syslog.conf --config.test_and_exit
Sending Logstash's logs to /var/log/logstash which is now configured via log4j2.properties
Configuration OK
显示没有问题

systemctl start logstash
ps aux |grep logstash

查看有没有信息
http://192.168.0.121:9200/_cat/indices?v

http://192.168.0.121:9200/system-syslog-2018.12?pretty

firewall-cmd --zone=public --add-port=10514/tcp --permanent
firewall-cmd --reload
firewall-cmd --zone=public --list-ports
```

## 安装filebeat

https://www.elastic.co/downloads/beats/filebeat