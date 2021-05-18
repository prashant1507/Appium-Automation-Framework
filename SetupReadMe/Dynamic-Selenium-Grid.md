## _Dynamic Grid_

1. Docker demon should be accessible. Temporary below command can be executed
   ```
    sudo socat tcp-listen:2375,reuseaddr,fork,bind=192.168.0.142 unix-client:/var/run/docker.sock,su=root
   ```
   or
   ```
      1. Create daemon.json file in /etc/docker
      2. Add /etc/systemd/system/docker.service.d/override.conf
      3. systemctl daemon-reload
      4.  systemctl restart docker.service
   ```
   [Reference Link to set docker daemon](https://gist.github.com/styblope/dc55e0ad2a9848f2cc3307d4819d819f)
2. Set IP:PORT in [config.toml](../src/test/resources/docker/Dynamic Selenium Grid/config.toml)
3. Set config.toml in [docker-compose-dynamic-grid.yml](../src/test/resources/docker/Dynamic Selenium Grid/docker-compose-dynamic-grid.yml)
4. Run `docker-compose up`

### Notes:
- Currently, the compose file and config.toml os not supporting video recording
