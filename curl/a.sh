#!/bin/bash
curl 'https://18comic.vip/' \
  -H 'authority: 18comic.vip' \
  -H 'accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9' \
  -H 'accept-language: en' \
  -H 'sec-ch-ua: "/Not)A;Brand";v="24", "Chromium";v="104"' \
  -H 'cookie: ipcountry=GB; ipm5=a096ac7b165a481b2651177340a425f7; AVS=udl854qdgcbvaplsrehcl1ha2i; shunt=1' \
  -H 'sec-ch-ua-mobile: ?0' \
  -H 'sec-ch-ua-platform: "Windows"' \
  -H 'sec-fetch-dest: document' \
  -H 'sec-fetch-mode: navigate' \
  -H 'sec-fetch-site: none' \
  -H 'sec-fetch-user: ?1' \
  -H 'upgrade-insecure-requests: 1' \
  -H 'user-agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36' \
  --user-agent 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36'  \
  --trace trace.log  \
  -x socks5://127.0.0.1:10808  \
  -o demo1.html  \
  --compressed ;
