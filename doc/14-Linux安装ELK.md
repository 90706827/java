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
node.master: true  # 意思是该节点为主节点 不用设置自主选择
node.data: false  # 表示这不是数据节点
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
elasticsearch.url: "http://192.168.77.128:9200"  # 配置es服务器的ip，如果是集群则配置该集群中主节点的ip
logging.dest: /var/log/kibana.log  # 配置kibana的日志文件路径，不然默认是messages里记录日志

chmod 777 /var/log/kibana.log


systemctl start kibana
ps aux |grep kibana
netstat -lntp |grep 5601
```

开放端口

firewall-cmd --zone=public --add-port=5601/tcp --permanent
firewall-cmd --reload
firewall-cmd --zone=public --list-ports



http://192.168.77.128:5601/



## 安装logstash

目前logstash不支持JDK1.9

https://www.elastic.co/downloads/logstash



## 安装filebeat

https://www.elastic.co/downloads/beats/filebeat