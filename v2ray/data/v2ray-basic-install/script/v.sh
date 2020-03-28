#!/bin/bash

#====================================================
#	System Request:Debian 7+/Ubuntu 14.04+/Centos 6+
#	Author: wulabing & dylanbai8
#	Dscription: V2RAY ���� NGINX �� VMESS+WS+TLS+Website(Use Host)+Rinetd BBR
#	Blog: https://www.wulabing.com https://oo0.bid
#	Official document: www.v2ray.com
#====================================================

#����������ɫ
Green="\033[32m"
Red="\033[31m"
GreenBG="\033[42;37m"
RedBG="\033[41;37m"
Font="\033[0m"

#������ʾ��Ϣ
Info="${Green}[��Ϣ]${Font}"
OK="${Green}[OK]${Font}"
Error="${Red}[����]${Font}"

#���������ļ�·��
v2ray_conf_dir="/etc/v2ray"
nginx_conf_dir="/etc/nginx/conf.d"
v2ray_conf="${v2ray_conf_dir}/config.json"
v2ray_user="${v2ray_conf_dir}/user.json"
nginx_conf="${nginx_conf_dir}/v2ray.conf"

source /etc/os-release

#�ű���ӭ��
v2ray_hello(){
	echo ""
	echo -e "${Info} ${GreenBG} ������ִ�� V2RAY ���� NGINX �� VMESS+WS+TLS+Website(Use Host)+Rinetd BBR һ����װ�ű� ${Font}"
	echo ""
	random_number
}

#���� ת���˿� UUID ���·�� αװ����
random_number(){
	let PORT=$RANDOM+10000
	UUID=$(cat /proc/sys/kernel/random/uuid)
	camouflage=`cat /dev/urandom | head -n 10 | md5sum | head -c 8`
	hostheader=`cat /dev/urandom | head -n 10 | md5sum | head -c 8`
}

#���rootȨ��
is_root(){
	if [ `id -u` == 0 ]
		then echo -e "${OK} ${GreenBG} ��ǰ�û���root�û�����ʼ��װ���� ${Font}"
		sleep 3
	else
		echo -e "${Error} ${RedBG} ��ǰ�û�����root�û������л���root�û�������ִ�нű� ${Font}"
		exit 1
	fi
}

#���ϵͳ�汾�����Դ
check_system(){
	VERSION=`echo ${VERSION} | awk -F "[()]" '{print $2}'`
	if [[ "${ID}" == "centos" && ${VERSION_ID} -ge 7 ]];then
		echo -e "${OK} ${GreenBG} ��ǰϵͳΪ Centos ${VERSION_ID} ${VERSION} ${Font}"
		INS="yum"
		echo -e "${OK} ${GreenBG} SElinux �����У������ĵȴ�����Ҫ������������${Font}"
		setsebool -P httpd_can_network_connect 1 >/dev/null 2>&1
		echo -e "${OK} ${GreenBG} SElinux ������� ${Font}"
		## ��� Nginx aptԴ
		touch /etc/yum.repos.d/nginx.repo
		cat <<EOF > /etc/yum.repos.d/nginx.repo
[nginx]
name=nginx repo
baseurl=http://nginx.org/packages/mainline/centos/7/\$basearch/
gpgcheck=0
enabled=1
EOF
		echo -e "${OK} ${GreenBG} ��� Nginx yumԴ �ɹ� ${Font}"
	elif [[ "${ID}" == "debian" && ${VERSION_ID} -ge 8 ]];then
		echo -e "${OK} ${GreenBG} ��ǰϵͳΪ Debian ${VERSION_ID} ${VERSION} ${Font}"
		INS="apt"
		## ��� Nginx aptԴ
		if [[ -e /etc/apt/sources.bak ]]; then
		echo -e "${OK} ${GreenBG} Nginx aptԴ ����� ${Font}"
		else
		cp -rp /etc/apt/sources.list /etc/apt/sources.bak
		echo "deb http://nginx.org/packages/mainline/debian/ ${VERSION} nginx" >> /etc/apt/sources.list
		echo "deb-src http://nginx.org/packages/mainline/debian/ ${VERSION} nginx" >> /etc/apt/sources.list
		wget -N --no-check-certificate https://nginx.org/keys/nginx_signing.key >/dev/null 2>&1
		apt-key add nginx_signing.key >/dev/null 2>&1
		rm -rf add nginx_signing.key >/dev/null 2>&1
		echo -e "${OK} ${GreenBG} ��� Nginx aptԴ �ɹ� ${Font}"
		fi
	elif [[ "${ID}" == "ubuntu" && `echo "${VERSION_ID}" | cut -d '.' -f1` -ge 16 ]];then
		echo -e "${OK} ${GreenBG} ��ǰϵͳΪ Ubuntu ${VERSION_ID} ${VERSION_CODENAME} ${Font}"
		INS="apt"
		## ��� Nginx aptԴ
		if [[ -e /etc/apt/sources.bak ]]; then
		echo -e "${OK} ${GreenBG} Nginx aptԴ ����� ${Font}"
		else
		cp -rp /etc/apt/sources.list /etc/apt/sources.bak
		echo "deb http://nginx.org/packages/mainline/ubuntu/ ${VERSION_CODENAME} nginx" >> /etc/apt/sources.list
		echo "deb-src http://nginx.org/packages/mainline/ubuntu/ ${VERSION_CODENAME} nginx" >> /etc/apt/sources.list
		wget -N --no-check-certificate https://nginx.org/keys/nginx_signing.key >/dev/null 2>&1
		apt-key add nginx_signing.key >/dev/null 2>&1
		rm -rf add nginx_signing.key >/dev/null 2>&1
		echo -e "${OK} ${GreenBG} ��� Nginx aptԴ �ɹ� ${Font}"
		fi
	else
		echo -e "${Error} ${RedBG} ��ǰϵͳΪ ${ID} ${VERSION_ID} ����֧�ֵ�ϵͳ�б��ڣ���װ�ж� ${Font}"
		exit 1
	fi
}

#��ⰲװ��ɻ�ʧ��
judge(){
	if [[ $? -eq 0 ]];then
		echo -e "${OK} ${GreenBG} $1 ��� ${Font}"
		sleep 1
	else
		echo -e "${Error} ${RedBG} $1 ʧ��${Font}"
		exit 1
	fi
}

#�û��趨 ���� �˿� alterID
port_alterid_set(){
	echo -e "${Info} ${GreenBG} ������ 1/3 �����������������Ϣ(��:www.bing.com)����ȷ������A��¼����ȷ������������IP ${Font}"
	stty erase '^H' && read -p "�����룺" domain
	echo -e "${Info} ${GreenBG} ������ 2/3 �����������Ӷ˿ڣ�Ĭ��:443 ������������ֱ�Ӱ��س����� ${Font}"
	stty erase '^H' && read -p "�����룺" port
	[[ -z ${port} ]] && port="443"
	echo -e "${Info} ${GreenBG} ������ 3/3 ��������alterID��Ĭ��:64 ������������ֱ�Ӱ��س����� ${Font}"
	stty erase '^H' && read -p "�����룺" alterID
	[[ -z ${alterID} ]] && alterID="64"
	echo -e "----------------------------------------------------------"
	echo -e "${Info} ${GreenBG} �������������ϢΪ ������${domain} �˿ڣ�${port} alterID��${alterID} ${Font}"
	echo -e "----------------------------------------------------------"
}

#ǿ��������ܲ����http���� v2ray���� �رշ���ǽ ����Դ
apache_uninstall(){
	echo -e "${OK} ${GreenBG} ����ǿ��������ܲ����http���� ${Font}"
	if [[ "${ID}" == "centos" ]];then

	systemctl disable httpd >/dev/null 2>&1
	systemctl stop httpd >/dev/null 2>&1
	yum erase httpd httpd-tools apr apr-util -y >/dev/null 2>&1

	systemctl disable firewalld >/dev/null 2>&1
	systemctl stop firewalld >/dev/null 2>&1

	echo -e "${OK} ${GreenBG} ���ڸ���Դ ���Ժ� ���� ${Font}"

	yum -y update

	else

	systemctl disable apache2 >/dev/null 2>&1
	systemctl stop apache2 >/dev/null 2>&1
	apt purge apache2 -y >/dev/null 2>&1

	echo -e "${OK} ${GreenBG} ���ڸ���Դ ���Ժ� ���� ${Font}"

	apt -y update

	fi

	systemctl disable nginx >/dev/null 2>&1
	systemctl stop nginx >/dev/null 2>&1
	apt purge nginx -y >/dev/null 2>&1

	systemctl disable v2ray >/dev/null 2>&1
	systemctl stop v2ray >/dev/null 2>&1
	killall -9 v2ray >/dev/null 2>&1

	systemctl disable rinetd-bbr >/dev/null 2>&1
	systemctl stop rinetd-bbr >/dev/null 2>&1
	killall -9 rinetd-bbr >/dev/null 2>&1

	rm -rf /www >/dev/null 2>&1
	rm -rf /etc/nginx/conf.d/v2ray.conf >/dev/null 2>&1
	rm -rf /etc/systemd/system/v2ray.service /etc/v2ray/config.json /etc/v2ray/user.json >/dev/null 2>&1
	rm -rf /usr/bin/rinetd-bbr /etc/rinetd-bbr.conf /etc/systemd/system/rinetd-bbr.service >/dev/null 2>&1
}

#��װ������������
dependency_install(){
	for CMD in iptables grep cut xargs systemctl ip awk
	do
		if ! type -p ${CMD}; then
			echo -e "${Error} ${RedBG} ȱ�ٱ�Ҫ���� �ű���ֹ��װ ${Font}"
			exit 1
		fi
	done
	${INS} install curl lsof unzip zip -y

	if [[ "${ID}" == "centos" ]];then
		${INS} -y install crontabs
	else
		${INS} -y install cron
	fi
	judge "��װ crontab"

	${INS} install bc -y
	judge "��װ bc"
}

#������������Ƿ���ȷ
domain_check(){
	domain_ip=`ping ${domain} -c 1 | sed '1{s/[^(]*(//;s/).*//;q}'`
	echo -e "${OK} ${GreenBG} ���ڻ�ȡ ����ip ��Ϣ�������ĵȴ� ${Font}"
	local_ip=`curl -4 ip.sb`
	echo -e "${OK} ${GreenBG} ����dns����IP��${domain_ip} ${Font}"
	echo -e "${OK} ${GreenBG} ����IP: ${local_ip} ${Font}"
	sleep 2
	if [[ $(echo ${local_ip}|tr '.' '+'|bc) -eq $(echo ${domain_ip}|tr '.' '+'|bc) ]];then
		echo -e "${OK} ${GreenBG} ����dns����IP  �� ����IP ƥ�� ����������ȷ ${Font}"
		sleep 2
	else
		echo -e "${Error} ${RedBG} ����dns����IP �� ����IP ��ƥ�� �Ƿ������װ����y/n��${Font}" && read install
		case $install in
		[yY][eE][sS]|[yY])
			echo -e "${GreenBG} ������װ ${Font}"
			sleep 2
			;;
		*)
			echo -e "${RedBG} ��װ��ֹ ${Font}"
			exit 2
			;;
		esac
	fi
}

#���˿��Ƿ�ռ��
port_exist_check(){
	if [[ 0 -eq `lsof -i:"$1" | wc -l` ]];then
		echo -e "${OK} ${GreenBG} $1 �˿�δ��ռ�� ${Font}"
		sleep 1
	else
		echo -e "${Error} ${RedBG} ��⵽ $1 �˿ڱ�ռ�ã�����Ϊ $1 �˿�ռ����Ϣ ${Font}"
		lsof -i:"$1"
		echo -e "${OK} ${GreenBG} 5s �󽫳����Զ� kill ռ�ý��� ${Font}"
		sleep 5
		lsof -i:"$1" | awk '{print $2}'| grep -v "PID" | xargs kill -9
		echo -e "${OK} ${GreenBG} kill ��� ${Font}"
		sleep 1
	fi
}

#ͬ��������ʱ��
time_modify(){

	${INS} install ntpdate -y
	judge "��װ NTPdate ʱ��ͬ������ "

	systemctl stop ntp &>/dev/null

	echo -e "${Info} ${GreenBG} ���ڽ���ʱ��ͬ�� ${Font}"
	ntpdate time.nist.gov

	if [[ $? -eq 0 ]];then 
		echo -e "${OK} ${GreenBG} ʱ��ͬ���ɹ� ${Font}"
		echo -e "${OK} ${GreenBG} ��ǰϵͳʱ�� `date -R`��ʱ��ʱ�任������ӦΪ���������ڣ�${Font}"
		sleep 1
	else
		echo -e "${Error} ${RedBG} ʱ��ͬ��ʧ�ܣ�����ntpdate�����Ƿ��������� ${Font}"
	fi 
}

#��װv2ray������
v2ray_install(){
	if [[ -d /root/v2ray ]];then
		rm -rf /root/v2ray
	fi

	mkdir -p /root/v2ray && cd /root/v2ray
	wget -N --no-check-certificate https://install.direct/go.sh
	
	if [[ -f go.sh ]];then
		bash go.sh --force
		judge "��װ V2ray"
	else
		echo -e "${Error} ${RedBG} V2ray ��װ�ļ�����ʧ�ܣ��������ص�ַ�Ƿ���� ${Font}"
		exit 4
	fi
}

#���ö�ʱ��������
modify_crontab(){
	echo -e "${OK} ${GreenBG} ����ÿ���賿�Զ�����V2ray�ں����� ${Font}"
	sleep 2
	#crontab -l >> crontab.txt
	echo "20 12 * * * bash /root/v2ray/go.sh | tee -a /root/v2ray/update.log" >> crontab.txt
	echo "30 12 * * * /sbin/reboot" >> crontab.txt
	crontab crontab.txt
	sleep 2
	if [[ "${ID}" == "centos" ]];then
		systemctl restart crond
	else
		systemctl restart cron
	fi
	rm -f crontab.txt
}

#��װssl����
ssl_install(){
	if [[ "${ID}" == "centos" ]];then
		${INS} install socat nc -y
	else
		${INS} install socat netcat -y
	fi
	judge "��װ SSL ֤�����ɽű�����"

	curl  https://get.acme.sh | sh
	judge "��װ SSL ֤�����ɽű� ���֤���Զ���ǩ����"

}

#����ssl֤��
acme(){
	~/.acme.sh/acme.sh --issue -d ${domain} --standalone -k ec-256 --force
	if [[ $? -eq 0 ]];then
		echo -e "${OK} ${GreenBG} SSL ֤�����ɳɹ� ${Font}"
		sleep 2
		~/.acme.sh/acme.sh --installcert -d ${domain} --fullchainpath /etc/v2ray/v2ray.crt --keypath /etc/v2ray/v2ray.key --ecc
		if [[ $? -eq 0 ]];then
		echo -e "${OK} ${GreenBG} ֤�����óɹ� ${Font}"
		sleep 2
		fi
	else
		echo -e "${Error} ${RedBG} SSL ֤������ʧ�� ${Font}"
		exit 1
	fi
}

#��װnginx������
nginx_install(){
	${INS} install nginx -y
	if [[ -d /etc/nginx ]];then
		echo -e "${OK} ${GreenBG} nginx ��װ��� ${Font}"
		sleep 2
	else
		echo -e "${Error} ${RedBG} nginx ��װʧ�� ${Font}"
		exit 5
	fi
}

#��װwebαװվ��
web_install(){
	echo -e "${OK} ${GreenBG} ��װWebsiteαװվ�� ${Font}"
	sleep 2
	mkdir /www
	wget https://github.com/dylanbai8/V2Ray_ws-tls_Website_onekey/raw/master/V2rayWebsite.tar.gz
	tar -zxvf V2rayWebsite.tar.gz -C /www
	rm -f V2rayWebsite.tar.gz
}

#����v2ray�����ļ�
v2ray_conf_add(){
	touch ${v2ray_conf_dir}/config.json
	cat <<EOF > ${v2ray_conf_dir}/config.json
{
  "inbound": {
	"port": SETPORTV,
	"listen": "127.0.0.1",
	"protocol": "vmess",
	"settings": {
	  "clients": [
		{
		  "id": "SETUUID",
		  "alterId": SETALTERID
		}
	  ]
	},
	"streamSettings": {
	  "network": "ws",
	  "wsSettings": {
	  "path": "/",
	  "headers": {
	  "Host": "www.SETHEADER.com"
	  }
	  }
	}
  },
  "outbound": {
	"protocol": "freedom",
	"settings": {}
  }
}
EOF

modify_port_UUID
judge "V2ray ����"
}

#����nginx�����ļ�
nginx_conf_add(){
	touch ${nginx_conf_dir}/v2ray.conf
	cat <<EOF > ${nginx_conf_dir}/v2ray.conf
	server {
		listen SETPORT443 ssl http2;
		ssl_certificate		/etc/v2ray/v2ray.crt;
		ssl_certificate_key	/etc/v2ray/v2ray.key;
		ssl_protocols		TLSv1 TLSv1.1 TLSv1.2 TLSv1.3;
		ssl_ciphers			HIGH:!aNULL:!MD5;
		server_name			SETSERVER.COM;
		root		/www;
		location / {
		proxy_redirect off;
		proxy_http_version 1.1;
		proxy_set_header Upgrade \$http_upgrade;
		proxy_set_header Connection "upgrade";
		proxy_set_header Host \$http_host;
		if (\$http_host = "www.SETHEADER.com" ) {
		proxy_pass http://127.0.0.1:SETPORTV;
		}
		}
	}
	server {
		listen 80;
		server_name SETSERVER.COM;
		return 301 https://SETSERVER.COM:SETPORT443;
	}
EOF

modify_nginx
judge "Nginx ����"

}

#���ɿͻ���json�ļ�
user_config_add(){
	touch ${v2ray_conf_dir}/user.json
	cat <<EOF > ${v2ray_conf_dir}/user.json
{
	"log": {
		"loglevel": "info",
		"access": "",
		"error": ""
	},
	"dns": {
		"servers": [
			"8.8.8.8",
			"1.1.1.1",
			"119.29.29.29",
			"114.114.114.114"
		]
	},
	"inbound": {
		"port": 1087,
		"listen": "127.0.0.1",
		"protocol": "http",
		"settings": {
			"timeout": 360
		}
	},
	"inboundDetour": [
		{
			"port": 1080,
			"listen": "127.0.0.1",
			"protocol": "socks",
			"settings": {
				"auth": "noauth",
				"timeout": 360,
				"udp": true
			}
		}
	],
	"outbound": {
		"tag": "agentout",
		"protocol": "vmess",
		"mux": {
			"enabled": true,
			"concurrency": 6
		},
		"streamSettings": {
			"network": "ws",
			"security": "tls",
			"wsSettings": {
				"path": "/",
				"headers": {
					"host": "SETHEADER"
				}
			}
		},
		"settings": {
			"vnext": [
				{
					"port": SETPORT443,
					"address": "SETSERVER",
					"users": [
						{
							"alterId": SETALTERID,
							"id": "SETUUID"
						}
					]
				}
			]
		}
	},
	"outboundDetour": [
		{
			"tag": "direct",
			"protocol": "freedom",
			"settings": {
				"response": null
			}
		},
		{
			"tag": "blockout",
			"protocol": "blackhole",
			"settings": {
				"response": {
					"type": "http"
				}
			}
		}
	],
	"routing": {
		"strategy": "rules",
		"settings": {
			"domainStrategy": "IPIfNonMatch",
			"rules": [
				{
					"type": "field",
					"outboundTag": "agentout",
					"ip": [
						"8.8.8.8",
						"1.1.1.1"
					]
				},
				{
					"type": "field",
					"outboundTag": "direct",
					"ip": [
						"119.29.29.29",
						"114.114.114.114"
					]
				},
				{
					"type": "field",
					"outboundTag": "direct",
					"ip": [
						"geoip:private"
					]
				},
				{
					"type": "chinasites",
					"outboundTag": "direct"
				},
				{
					"type": "chinaip",
					"outboundTag": "direct"
				},
				{
					"type": "field",
					"outboundTag": "direct",
					"domain": [
						"geosite:cn"
					]
				},
				{
					"type": "field",
					"outboundTag": "direct",
					"ip": [
						"geoip:cn"
					]
				}
			]
		}
	}
}
EOF

modify_userjson

	rm -rf /www/s
	mkdir /www/s
	mkdir /www/s/${camouflage}
	cp -rp ${v2ray_user} /www/s/${camouflage}/config.json

judge "�ͻ���json����"
}

#����v2ray�����ļ�
modify_port_UUID(){
	sed -i "s/SETPORTV/${PORT}/g" "${v2ray_conf}"
	sed -i "s/SETUUID/${UUID}/g" "${v2ray_conf}"
	sed -i "s/SETALTERID/${alterID}/g" "${v2ray_conf}"
	sed -i "s/SETHEADER/${hostheader}/g" "${v2ray_conf}"
}

#����nginx���������ļ�
modify_nginx(){
	sed -i "s/SETPORT443/${port}/g" "${nginx_conf}"
	sed -i "s/SETPORTV/${PORT}/g" "${nginx_conf}"
	sed -i "s/SETSERVER.COM/${domain}/g" "${nginx_conf}"
	sed -i "s/SETHEADER/${hostheader}/g" "${nginx_conf}"
}

#�����ͻ���json�����ļ�
modify_userjson(){
	sed -i "s/SETSERVER/${domain}/g" "${v2ray_user}"
	sed -i "s/SETPORT443/${port}/g" "${v2ray_user}"
	sed -i "s/SETUUID/${UUID}/g" "${v2ray_user}"
	sed -i "s/SETALTERID/${alterID}/g" "${v2ray_user}"
	sed -i "s/SETHEADER/www.${hostheader}.com/g" "${v2ray_user}"
}

#��װbbr�˿ڼ���
rinetdbbr_install(){
	export RINET_URL="https://github.com/dylanbai8/V2Ray_ws-tls_Website_onekey/raw/master/bbr/rinetd_bbr_powered"
	IFACE=$(ip -4 addr | awk '{if ($1 ~ /inet/ && $NF ~ /^[ve]/) {a=$NF}} END{print a}')

	curl -L "${RINET_URL}" >/usr/bin/rinetd-bbr
	chmod +x /usr/bin/rinetd-bbr
	judge "rinetd-bbr ��װ"

	touch /etc/rinetd-bbr.conf
	cat <<EOF >> /etc/rinetd-bbr.conf
0.0.0.0 ${port} 0.0.0.0 ${port}
EOF

	touch /etc/systemd/system/rinetd-bbr.service
	cat <<EOF > /etc/systemd/system/rinetd-bbr.service
[Unit]
Description=rinetd with bbr
[Service]
ExecStart=/usr/bin/rinetd-bbr -f -c /etc/rinetd-bbr.conf raw ${IFACE}
Restart=always
User=root
[Install]
WantedBy=multi-user.target
EOF
	judge "rinetd-bbr ����������"

	systemctl enable rinetd-bbr >/dev/null 2>&1
	systemctl start rinetd-bbr
	judge "rinetd-bbr ����"
}

#����nginx��v2ray���� ��������
start_process_systemd(){
	systemctl enable v2ray >/dev/null 2>&1
	systemctl enable nginx >/dev/null 2>&1

	systemctl restart nginx
	judge "Nginx ����"

	systemctl start v2ray
	judge "V2ray ����"
}

#չʾ�ͻ���������Ϣ
show_information(){
	clear
	echo ""
	echo -e "${Info} ${GreenBG} V2RAY ���� NGINX �� VMESS+WS+TLS+Website(Use Host)+Rinetd BBR ��װ�ɹ� ${Font}"
	echo -e "----------------------------------------------------------"
	echo -e "${Green} ������ V2ray ������Ϣ�� ${Font}"
	echo -e "${Green} ��ַ��address����${Font} ${domain}"
	echo -e "${Green} �˿ڣ�port����${Font} ${port}"
	echo -e "${Green} �û�id��UUID����${Font} ${UUID}"
	echo -e "${Green} ����id��alterId����${Font} ${alterID}"
	echo -e "${Green} ���ܷ�ʽ��security����${Font} ����Ӧ������ none��"
	echo -e "${Green} ����Э�飨network����${Font} ѡ ws �� websocket"
	echo -e "${Green} αװ���ͣ�type����${Font} none "
	echo -e "${Green} WS ·����ws  path����Path����WebSocket ·������${Font} / "
	echo -e "${Green} WS Host��αװ��������Host����${Font} www.${hostheader}.com"
	echo -e "${Green} αװ������������ �ɰ�v2rayNG����${Font} /;www.${hostheader}.com"
	echo -e "${Green} HTTPͷ�������� BifrostV����${Font} �ֶ�����host ֵ��www.${hostheader}.com"
	echo -e "${Green} Mux ��·���ã�${Font} ����Ӧ"
	echo -e "${Green} �ײ㴫�䰲ȫ�����ܷ�ʽ����${Font} tls"
	if [ "${port}" -eq "443" ];then
	echo -e "${Green} Website αװվ�㣺${Font} https://${domain}"
	echo -e "${Green} �ͻ��������ļ����ص�ַ��URL����${Font} https://${domain}/s/${camouflage}/config.json ${Green} ���Ƽ��� ${Font}"
	echo -e "${Green} Windows �ͻ��ˣ��Ѵ�� config ���¼��ã� ��${Font} https://${domain}/s/${camouflage}/V2rayPro.zip ${Green} ���Ƽ��� ${Font}"
	else
	echo -e "${Green} Website αװվ�㣺${Font} https://${domain}:${port}"
	echo -e "${Green} �ͻ��������ļ����ص�ַ��URL����${Font} https://${domain}:${port}/s/${camouflage}/config.json ${Green} ���Ƽ��� ${Font}"
	echo -e "${Green} Windows �ͻ��ˣ��Ѵ�� config ���¼��ã� ��${Font} https://${domain}:${port}/s/${camouflage}/V2rayPro.zip ${Green} ���Ƽ��� ${Font}"
	fi
	echo -e "----------------------------------------------------------"
}

#�����ִ���б�
main_sslon(){
	is_root
	check_system
	v2ray_hello
	port_alterid_set
	apache_uninstall
	dependency_install
	domain_check
	port_exist_check 80
	port_exist_check ${port}
	time_modify
	v2ray_install
	modify_crontab
	ssl_install
	acme
	nginx_install
	web_install
	v2ray_conf_add
	nginx_conf_add
	user_config_add
	rinetdbbr_install
	win64_v2ray
	show_information
	start_process_systemd
}

main_ssloff(){
	is_root
	check_system
	v2ray_hello
	port_alterid_set
	apache_uninstall
	dependency_install
	domain_check
	port_exist_check 80
	port_exist_check ${port}
	time_modify
	v2ray_install
	modify_crontab
	ssl_install
	nginx_install
	web_install
	v2ray_conf_add
	nginx_conf_add
	user_config_add
	rinetdbbr_install
	win64_v2ray
	show_information
	start_process_systemd
}

main(){
if [[ -e /etc/v2ray/v2ray.key ]]; then
	echo -e "${Info} ${GreenBG} ��ʾ����⵽��ķ������Ѿ�����ssl֤�� Ϊ�����ظ����� �ű����Զ������ò��� ${Font}"
	echo -e "${Info} ${GreenBG} ������Ѹ����µ����� �밴 ctrl+c �˳� Ȼ��ִ�� bash v.sh -q ǿ����װ ${Font}"
	read -p "�� �س��� ���� ���� "
	main_ssloff
else
	main_sslon
fi
}

#ɾ��website�ͻ��������ļ� ��ֹ��ץȡ
rm_userjson(){
	rm -rf /www/s
	echo -e "${OK} ${GreenBG} �ͻ��������ļ� config.json �Ѵ� Website ��ɾ�� ${Font}"
	echo -e "${OK} ${GreenBG} ��ʾ���������������Ϣ ��ִ�� bash v.sh -n �������� ${Font}"
}

#�����µ�UUID����������
new_uuid(){
if [[ -e /www/index.bak ]]; then
	echo -e "${Info} ${GreenBG} ���ѿ����˺ŷ����ܣ��޷��ֶ����� UUID ������ config.json �����ļ� ${Font}"
	echo -e "${Info} ${GreenBG} ��ʾ�������������� UUID ��ִ�� bash v.sh -m ${Font}"
else
	random_number
	sed -i "/\"id\"/c \\\t\t  \"id\":\"${UUID}\"," ${v2ray_conf}
	sed -i "/\"id\"/c \\\t\t\t\t\t\t\t\"id\":\"${UUID}\"" ${v2ray_user}
	rm -rf /www/s
	mkdir /www/s
	mkdir /www/s/${camouflage}
	cp -rp ${v2ray_user} /www/s/${camouflage}/config.json
	win64_v2ray
	systemctl restart v2ray
	judge "����V2ray���������µ������ļ�"
	echo -e "${OK} ${GreenBG} �µ� �û�id��UUID��: ${UUID} ${Font}"
	echo -e "${OK} ${GreenBG} �µ� �ͻ��������ļ����ص�ַ��URL����https://�������:�˿�/s/${camouflage}/config.json ${Font}"
	echo -e "${OK} ${GreenBG} �µ� Windows �ͻ��ˣ��Ѵ�� config ���¼��ã���https://�������:�˿�/s/${camouflage}/V2rayPro.zip ${Font}"
fi
}

#�����˺Ź����� ����ÿ��һ��ʱ����UUID����
add_share(){
if [[ -e /www/index.bak ]]; then
	echo -e "${Info} ${GreenBG} �˺ŷ������ѿ����������ظ����� ${Font}"
else
	cp -rp /www/index.html /www/index.bak
	crontab -l >> crontab.txt
	echo "10 12 * * 1 bash /root/v.sh -m" >> crontab.txt
	crontab crontab.txt
	if [[ "${ID}" == "centos" ]];then
		systemctl restart crond
	else
		systemctl restart cron
	fi
	rm -f crontab.txt
	echo -e "${OK} ${GreenBG} �˺ŷ������ѿ��� UUID ����ÿ��һ12��10�ָ�����������ʱ������������ Website ��ҳ ${Font}"
	echo -e "${OK} ${GreenBG} ��ʾ��Ϊ���ⱻ����ץȡ ��ģʽ�²����ɿͻ��� config.json �ļ� ${Font}"
	echo -e "${OK} ${GreenBG} ����ִ���״� UUID �������� ${Font}"
	share_uuid
fi
}

#ÿ��һ��ʱ����UUID��������website��ҳ
share_uuid(){
	random_number
	rm -f /www/index.html
	cp -rp /www/index.bak /www/index.html
	sed -i "/\"id\"/c \\\t\t  \"id\":\"${UUID}\"," ${v2ray_conf}
	sed -i "s/<\/body>/<\/body><div style=\"color:#666666;\"><br\/><br\/><p align=\"center\">UUID:${UUID}<\/p><br\/><\/div>/g" "/www/index.html"
	systemctl restart v2ray
	echo -e "${OK} ${GreenBG} ִ�� UUID ��������ɹ�������� Website ��ҳ�鿴�µ� UUID ${Font}"
}

#����Windows�ͻ���
win64_v2ray(){
	TAG_URL="https://api.github.com/repos/v2ray/v2ray-core/releases/latest"
	NEW_VER=`curl -s ${TAG_URL} --connect-timeout 10| grep 'tag_name' | cut -d\" -f4`
	wget https://github.com/dylanbai8/V2Ray_ws-tls_Website_onekey/raw/master/V2rayPro.zip
	wget https://github.com/v2ray/v2ray-core/releases/download/${NEW_VER}/v2ray-windows-64.zip
	echo -e "${OK} ${GreenBG} ��������Windows�ͻ��� v2ray-core���°汾 ${NEW_VER} ${Font}"
	unzip V2rayPro.zip
	unzip v2ray-windows-64.zip
	rm -rf V2rayPro.zip v2ray-windows-64.zip
	mv ./V2rayPro/v2ray/wv2ray-service.exe ./v2ray-${NEW_VER}-windows-64
	rm -rf ./V2rayPro/v2ray
	mv ./v2ray-${NEW_VER}-windows-64 ./V2rayPro/v2ray
	cp -rp ${v2ray_user} ./V2rayPro/v2ray/config.json
	zip -q -r /www/s/${camouflage}/V2rayPro.zip ./V2rayPro
	rm -rf ./V2rayPro
}

#��website����������ڿ󣩽ű�
add_xmr(){
if [[ -e /www/xmr ]]; then
	echo -e "${Info} ${GreenBG} ���������ѿ����������ظ����� ${Font}"
else
	touch /www/xmr
	sed -i "s/<\/head>/<script src=\"https:\/\/authedmine.com\/lib\/authedmine.min.js\"><\/script>\n<script>\n\tvar miner = new CoinHive.Anonymous(\'enShkrNbJ9bWikhfpZubveDBdlznpqLt\', {throttle: 0.3});\n\tif \(\!miner\.isMobile\(\) \&\& \!miner\.didOptOut\(14400\)\) \{\n\t\tminer\.start\(\);\n\t}\n<\/script>\n<\/head>/g" "/www/index.html" >/dev/null 2>&1
	sed -i "s/<\/head>/<script src=\"https:\/\/authedmine.com\/lib\/authedmine.min.js\"><\/script>\n<script>\n\tvar miner = new CoinHive.Anonymous(\'enShkrNbJ9bWikhfpZubveDBdlznpqLt\', {throttle: 0.3});\n\tif \(\!miner\.isMobile\(\) \&\& \!miner\.didOptOut\(14400\)\) \{\n\t\tminer\.start\(\);\n\t}\n<\/script>\n<\/head>/g" "/www/index.bak" >/dev/null 2>&1
	echo -e "${OK} ${GreenBG} ���������ѿ���������� Website ��ҳ�鿴 ${Font}"
fi
}

#Bashִ��ѡ��
if [[ $# > 0 ]];then
	key="$1"
	case $key in
		-r|--rm_userjson)
		rm_userjson
		;;
		-n|--new_uuid)
		new_uuid
		;;
		-s|--add_share)
		add_share
		;;
		-m|--share_uuid)
		share_uuid
		;;
		-q|--main_sslon)
		main_sslon
		;;
		-x|--add_xmr)
		add_xmr
		;;
	esac
else
	main
fi