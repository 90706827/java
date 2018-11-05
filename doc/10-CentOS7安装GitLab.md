

```shell
yum -y install build-essential zlib1g-devel libyaml-devel libssl-devel libgdbm-devel libreadline-devel libncurses5-devel libffi-devel curl openssh-server redis-server checkinstall libxml2-devel libxslt-devel libcurl4-openssl-devel libicu-devel logrotate python-docutils pkg-config cmake nodejs


yum -y install gcc gcc-c++ make autoconf libyaml-devel gdbm-devel ncurses-devel openssl-devel zlib-devel readline-devel curl-devel expat-devel gettext-devel  tk-devel libxml2-devel libffi-devel libxslt-devel libicu-devel sendmail patch libyaml* pcre-devel wget

yum -y install policycoreutils openssh-server openssh-clients postfix policycoreutils-python

#centos 6系统的下载地址:https://mirrors.tuna.tsinghua.edu.cn/gitlab-ce/yum/el6
#centos 7系统的下载地址:https://mirrors.tuna.tsinghua.edu.cn/gitlab-ce/yum/el7
wget https://mirrors.tuna.tsinghua.edu.cn/gitlab-ce/yum/el7/gitlab-ce-8.0.0-ce.0.el7.x86_64.rpm

rpm -i gitlab-ce-8.0.0-ce.0.el7.x86_64.rpm

vim  /etc/gitlab/gitlab.rb
#external_url 'http://192.168.0.122:8080'

gitlab-ctl reconfigure

#Running handlers:
#Running handlers complete
#Chef Client finished, 427/611 resources updated in 02 minutes 47 seconds
#gitlab Reconfigured!


gitlab-ctl restart

# 提示“ok: run:”表示启动成功

#ok: run: alertmanager: (pid 8002) 0s
#ok: run: gitaly: (pid 8010) 1s
#ok: run: gitlab-monitor: (pid 8025) 0s
#ok: run: gitlab-workhorse: (pid 8034) 1s
#ok: run: logrotate: (pid 8043) 0s
#ok: run: nginx: (pid 8049) 1s
#ok: run: node-exporter: (pid 8055) 0s
#ok: run: postgres-exporter: (pid 8059) 0s
#ok: run: postgresql: (pid 8141) 0s
#ok: run: prometheus: (pid 8149) 0s
#ok: run: redis: (pid 8158) 1s
#ok: run: redis-exporter: (pid 8165) 0s
#ok: run: sidekiq: (pid 8184) 0s
#ok: run: unicorn: (pid 8204) 0s


#开放端口
firewall-cmd --zone=public --add-port=8080/tcp --permanent
#重启防火墙
firewall-cmd --reload
#查看开放端口
firewall-cmd --zone=public --list-ports

#访问：http://192.168.0.122:8080

#初始账户: root 密码: 5iveL!fe

#报错处理：
#一.登录502报错
##一般是权限问题，解决方法：chmod -R 755 /var/log/gitlab
#二.gitlab-ctl reconfigure
##报错n itdb: could not obtain information about current user: Permission denied
##Error executing action `run` on resource ##'execute[/opt/gitlab/embedded/bin/initdb -D /var/opt/gitlab/postgresql/data -E UTF8]'
##根据报错信息大概锁定用户的权限问题,安装gitlab-ce会自动添加用户四个用户:
##gitlab-www:x:497:498::/var/opt/gitlab/nginx:/bin/false
##git:x:496:497::/var/opt/gitlab:/bin/sh
##gitlab-redis:x:495:496::/var/opt/gitlab/redis:/bin/nologin
##gitlab-psql:x:494:495::/var/opt/gitlab/postgresql:/bin/sh
##google和百度都搜索不到解决方法,既然出错提示到权限问题，那么按照这个方向去查就不会有问题，后来查了文件/etc/passwd的权限是600,给予644权限后,成功解决报错问题

#配饰SSH KEY
#1.打开本地git bash,使用如下命令生成ssh公钥和私钥对
ssh-keygen -t rsa -C 'xxx@xxx.com'
#2.然后打开~/.ssh/id_rsa.pub文件(~表示用户目录，比如我的windows就是C:\Users\Administrator)
#3.打开gitlab,找到Profile Settings-->SSH Keys--->Add SSH Key,并把上一步中复制的内容粘贴到Key所对应的文本框，在Title对应的文本框中给这个sshkey设置一个名字，点击Add key按钮
#4.使用git下载项目


#修改GitLab邮件服务配置，使用腾讯企业邮箱的SMTP服务器
gitlab_rails['smtp_enable'] = true
gitlab_rails['smtp_address'] = "smtp.exmail.qq.com"
gitlab_rails['smtp_port'] = 25
gitlab_rails['smtp_user_name'] = "xxx"
gitlab_rails['smtp_password'] = "xxx"
gitlab_rails['smtp_domain'] = "smtp.qq.com"
gitlab_rails['smtp_authentication'] = 'plain'
gitlab_rails['smtp_enable_starttls_auto'] = true


#Nexus

wget https://sonatype-download.global.ssl.fastly.net/repository/repositoryManager/3/nexus-3.14.0-04-unix.tar.gz

mkdir /usr/local/nexus

tar -zxvf nexus-3.14.0-04-unix.tar.gz -C /usr/local/nexus/

cd /usr/local/nexus/nexus-3.14.0-04/bin

./nexus run &

#查看到如下内容表示安装启动成功
-------------------------------------------------
Started Sonatype Nexus OSS 3.14.0-04
-------------------------------------------------

#开放端口
firewall-cmd --zone=public --add-port=8081/tcp --permanent
firewall-cmd --reload
firewall-cmd --zone=public --list-ports
#访问NEXUS
http://192.168.0.122:8081

#设置开机自启
#参考https://help.sonatype.com/repomanager3/system-requirements#SystemRequirements-Linux
vim /usr/lib/systemd/system/nexus.service

[Unit]
Description=nexus service

[Service]
Type=forking
LimitNOFILE=65536
ExecStart=/usr/local/nexus/nexus-3.14.0-04/bin/nexus start
ExecReload=/usr/local/nexus/nexus-3.14.0-04/bin/nexus restart
ExecStop=/usr/local/nexus/nexus-3.14.0-04/bin/nexus stop
Restart=on-failure

[Install]
WantedBy=multi-user.target

systemctl enable nexus.service

systemctl daemon-reload


cd /usr/local/nexus/nexus-3.14.0-04/
#修改nexus用户root
vim bin/nexus.rc
run_as_user="root"

#查找jdk路径
which java
#/usr/bin/java
ls -lrt /usr/bin/java
# /usr/bin/java -> /etc/alternatives/java
ls -lrt /etc/alternatives/java
#/etc/alternatives/java -> /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.191.b12-0.el7_5.x86_64/jre/bin/java

#修改jdk
vim bin/nexus
INSTALL4J_JAVA_HOME_OVERRIDE=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.191.b12-0.el7_5.x86_64

#修改默认端口
vim etc/nexus-default.properties
application-port=8081
#修改数据、日志存储位置
vim bin/nexus.vmoptions

#下载放到同一目录下 可百度网盘查找
wget http://repo.maven.apache.org/maven2/.index/nexus-maven-repository-index.gz
wget http://repo.maven.apache.org/maven2/.index/nexus-maven-repository-index.properties
wget http://central.maven.org/maven2/org/apache/maven/indexer/indexer-cli/6.0.0/indexer-cli-6.0.0.jar

java -jar indexer-cli-6.0.0.jar -u nexus-maven-repository-index.gz -d indexer
#等待程序运行完成之后可以发现indexer文件夹下出现了很多文件，将这些文件放置到{nexus-home}/sonatype-work/nexus3/indexer/central-ctx目录下
cp -r /opt/indexer/ /usr/local/nexus/sonatype-work/nexus3/instances/
mv indexer/ central-ctx
#重启nexus
systemctl restart  nexus.service



```

