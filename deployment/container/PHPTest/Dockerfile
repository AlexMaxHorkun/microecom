FROM ubuntu:18.04
COPY scripts /etc/microecom/
COPY app /var/www/test/
RUN /etc/microecom/ubuntu_init.sh
RUN /etc/microecom/php_install.sh
EXPOSE 80 22
CMD /etc/microecom/ubuntu_start.sh && /etc/microecom/phpserver_start.sh && tail -f /dev/null
