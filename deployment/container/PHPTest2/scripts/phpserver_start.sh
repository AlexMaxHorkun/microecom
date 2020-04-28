#!/usr/bin/env bash
php -S 0.0.0.0:80 /var/www/test/index.php &>/dev/null & disown
