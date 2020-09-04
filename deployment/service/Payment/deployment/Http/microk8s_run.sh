#!/bin/bash

/bin/bash -i create_image.sh && /bin/bash -i microk8s_create_image.sh && /bin/bash -i create_service.sh
